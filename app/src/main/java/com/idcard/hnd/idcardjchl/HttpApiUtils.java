package com.idcard.hnd.idcardjchl;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/**
 * @ClassName: HttpUtils
 * @Title: HttpUtils.java
 * @Description: http请求utils
 * @author：张亚运
 * @date 2017-2-22 下午04:10:25
 */
public class HttpApiUtils {


    /**
     * @throws
     * @Title: http
     * @Description: http请求
     * @author： 张亚运
     * @param： @param url
     * @param： @param params
     * @param： @param encoded
     * @param： @return
     * @return： String
     */
    public static String httpApi(String url, String jsonStr) {
        String resultMsg = "";
        URL u = null;
        HttpURLConnection con = null;

        // 构建请求参数
        // System.out.println("send_url:" + url);
        // System.out.println("send_data:" + sb.toString());

        // 尝试发送请求
        try {
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter osw = new OutputStreamWriter(con
                    .getOutputStream(), "utf-8");
            osw.write(jsonStr);
            osw.flush();
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 读取返回内容
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(con
                    .getInputStream(), "utf-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }
            System.out.println("获取的数据" + buffer.toString());
            resultMsg = buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            if (con != null) {
                con.disconnect();
            }
        }
        return resultMsg;
    }


    public static String HttpRequest(String baseURL, String jsonBody)
            throws Exception {
        URL url = new URL(baseURL);
        Log.e("huangfujian", "请求URL:" + url);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        // httpConn.setConnectTimeout(60000);
        // httpConn.setReadTimeout(60000);
        // //设置连接属性
        httpConn.setDoOutput(true);// 使用 URL 连接进行输出
        httpConn.setDoInput(true);// 使用 URL 连接进行输入
        httpConn.setUseCaches(false);// 忽略缓存
        httpConn.setRequestMethod("POST");// 设置URL请求方法

        // 设置请求属性
        // 获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致
        byte[] requestStringBytes = jsonBody.getBytes();
        httpConn.setRequestProperty("Content-length", ""
                + requestStringBytes.length);
        httpConn.setRequestProperty("Content-Type", "application/json");
        httpConn.setRequestProperty("Charset", "UTF-8");
        //
        String name = URLEncoder.encode("name", "utf-8");
        httpConn.setRequestProperty("NAME", name);
        // 建立输出流，并写入数据
        OutputStream outputStream = httpConn.getOutputStream();
        outputStream.write(requestStringBytes);
        outputStream.close();
        // 获得响应状态
        int responseCode = httpConn.getResponseCode();
        Log.e("huangfujian", "服务器响应:" + responseCode);
        String strResult = "";
        if (responseCode != 200) {
            Log.e("huangfujian", "responseCode = " + responseCode);
            throw new Exception(String.valueOf(responseCode));
        }
        if (responseCode == 200) {// 连接成功
            // 当正确响应时处理数据
            StringBuffer sb = new StringBuffer();
            String readLine;
            BufferedReader responseReader;
            // 处理响应流，必须与服务器响应流输出的编码一致
            responseReader = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream(), "utf-8"));
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine).append("\n");
            }
            strResult = sb.toString();
            System.out.println("获取的数据" + strResult);
            responseReader.close();
        }

        return strResult;
    }


    public static void main(String[] args) {
        try {
            /*
             * Map parms=new HashMap(); ClientUtils client = new ClientUtils();
			 * String cpnoderno = "C20120602121644344627"; parms.put("method",
			 * "querystate"); parms.put("cporderno", cpnoderno);
			 * parms.put("sign",
			 * MD5.MD5Encode("method=querystate&cporderno="+cpnoderno
			 * +"cuncun8.com").toUpperCase()); String response = new
			 * String(client
			 * .postMethod("http://ec.cuncun8.com/rechargeinterface.sc",
			 * parms),"UTF-8"); System.out.println(response);
			 */

			/*
             * StringBuffer sb = new StringBuffer();
			 * sb.append("00-49-60-00-15-00-00-60-30-00-00-00-00-01-00-20");
			 * sb.append("18-00-00-02-c0-00-12-10-00-00-16-51-53-04-25-30");
			 * sb.append("30-31-30-30-30-30-33-37-33-30-30-30-30-30-30-30");
			 * sb.append("31-30-30-30-30-32-30-32-00-26-12-12-24-15-10-12");
			 * sb.append("11-20-16-20-11-00-00-00-02-30-31-");
			 */
            {
                // String content =
                // Utils.split("00000000000030351200080000030351"
                // .toCharArray());
                // String content = "00000000000030351200080000030351";
                // System.out.println(content);
                // String s1 = ClientUtils.socketClinet("192.185.5.22", 9000,
                // content);
                // System.out.println(s1);
                // System.out.println(s1.length());
                // String aa = s1.substring(0, 32);
                // String bb = s1.substring(32, 64);
                // String cc = s1.substring(64, 96);
                // String dd = s1.substring(96, 128);
                // System.out.println("1扇区3块写入的数据：" + aa);
                // System.out.println("2扇区3块写入的数据：" + bb);
                // System.out.println("4 7扇区3块写入的数据：" + cc);
                // System.out.println("2扇区3块写入的数据：" + dd);
                // System.out.println("-------------------------");
                // System.out.println("1扇区3块读密码：" + aa.substring(0, 12) +
                // "-写密码："
                // + aa.substring(20, 32));
                // System.out.println("2扇区3块读密码：" + bb.substring(0, 12) +
                // "-写密码："
                // + bb.substring(20, 32));
                // System.out.println("4 7扇区3块读密码：" + cc.substring(0, 12)
                // + "-写密码：" + cc.substring(20, 32));
                // System.out.println("8扇区3块读密码：" + dd.substring(0, 12) +
                // "-写密码："
                // + dd.substring(20, 32));
            }

			/*
			 * for (int i = 0;i<10000;i++) {
			 * System.out.println(i+"-"+ClientUtils.getReturnByUrl(
			 * "http://192.168.5.50:8000/api/transaApi!trades?tradeType=&from=2013-09-13&to=2013-09-15&merId=&merName=&termName=&hiddenTest=true&organId=00000001&page=1&pagenum=999999"
			 * )); }
			 */

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
