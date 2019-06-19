package com.idcard.hnd.idcardjchl.common;

import android.util.Log;

import com.idcard.hnd.idcardjchl.AppContext;
import com.idcard.hnd.idcardjchl.AppException;
import com.idcard.hnd.idcardjchl.HttpApiUtils;
import com.idcard.hnd.idcardjchl.bean.URLs;
import com.idcard.hnd.idcardjchl.bean.Update;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.lidroid.xutils.util.OtherUtils.getUserAgent;

/**
 * API客户端接口：用于访问网络数据
 * Created by Administrator on 2019/6/17.
 */

public class ApiClient {
    public static final String UTF_8 = "UTF-8";

    private final static int TIMEOUT_CONNECTION = 20000;
    private final static int TIMEOUT_SOCKET = 20000;
    private final static int RETRY_TIME = 3;

    private static String appCookie;

    private static String getCookie(AppContext appContext) {
        if(appCookie == null || appCookie == "") {
            appCookie = appContext.getProperty("cookie");
        }
        return appCookie;
    }

    public static void cleanCookie() {
        appCookie = "";
    }

    /**
     * 检查版本更新
     * @param url
     * @return
     */
    public static Update checkVersion(AppContext appContext,String str) throws AppException {
        try{
            Log.d("版本链接=",URLs.UPDATE_VERSION + "");
            return Update.parse(HttpApiUtils.httpApi(URLs.UPDATE_VERSION,str));
        }catch(Exception e){
            if(e instanceof AppException)
                throw (AppException)e;
            throw AppException.network(e);
        }
    }

    private static HttpClient getHttpClient() {
        HttpClient httpClient = new HttpClient();
        // 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        // 设置 默认的超时重试处理策略
        httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        // 设置 连接超时时间
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT_CONNECTION);
        // 设置 读数据超时时间
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT_SOCKET);
        // 设置 字符集
        httpClient.getParams().setContentCharset(UTF_8);
        return httpClient;
    }

    private static GetMethod getHttpGet(String url, String cookie, String userAgent) {
        GetMethod httpGet = new GetMethod(url);
        // 设置 请求超时时间
        httpGet.getParams().setSoTimeout(TIMEOUT_SOCKET);
        httpGet.setRequestHeader("Host", URLs.HOST);
        httpGet.setRequestHeader("Connection","Keep-Alive");
        httpGet.setRequestHeader("Cookie", cookie);
        httpGet.setRequestHeader("User-Agent", userAgent);
        return httpGet;
    }

    /**
     * get请求URL
     * @param url
     * @throws AppException
     */
    private static InputStream http_get(AppContext appContext, String url) throws AppException {
        //System.out.println("get_url==> "+url);
        String cookie = getCookie(appContext);
        String userAgent = getUserAgent(appContext);

        HttpClient httpClient = null;
        GetMethod httpGet = null;

        String responseBody = "";
        int time = 0;
        do{
            try
            {
                httpClient = getHttpClient();
                httpGet = getHttpGet(url, cookie, userAgent);
                int statusCode = httpClient.executeMethod(httpGet);
                if (statusCode != HttpStatus.SC_OK) {
                    throw AppException.http(statusCode);
                }
                responseBody = httpGet.getResponseBodyAsString();
                //System.out.println("XMLDATA=====>"+responseBody);
                break;
            } catch (HttpException e) {
                time++;
                if(time < RETRY_TIME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {}
                    continue;
                }
                // 发生致命的异常，可能是协议不对或者返回的内容有问题
                e.printStackTrace();
                throw AppException.http(e);
            } catch (IOException e) {
                time++;
                if(time < RETRY_TIME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {}
                    continue;
                }
                // 发生网络异常
                e.printStackTrace();
                throw AppException.network(e);
            } finally {
                // 释放连接
                httpGet.releaseConnection();
                httpClient = null;
            }
        }while(time < RETRY_TIME);

        responseBody = responseBody.replaceAll("\\p{Cntrl}", "");
        if(responseBody.contains("result") && responseBody.contains("errorCode") && appContext.containsProperty("user.uid")){
            try {
//                Result res = Result.parse(new ByteArrayInputStream(responseBody.getBytes()));
//                if(res.getErrorCode() == 0){
//                    appContext.Logout();
//                    appContext.getUnLoginHandler().sendEmptyMessage(1);
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ByteArrayInputStream(responseBody.getBytes());
    }

}
