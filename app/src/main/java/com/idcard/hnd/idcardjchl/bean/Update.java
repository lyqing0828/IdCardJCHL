package com.idcard.hnd.idcardjchl.bean;


import android.util.Log;

import com.idcard.hnd.idcardjchl.AppException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;

/**
 * 应用程序更新实体类
 *
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class Update implements Serializable {
    public final static String UTF8 = "UTF-8";
    private int versionCode;
    private String versionName;
    private String downloadUrl;
    private String updateLog;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getUpdateLog() {
        return updateLog;
    }

    public void setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
    }

    public static Update parse(String result) throws IOException, AppException {
        Log.d("判断软件更新返回数据=", result);
        Update update = null;
        try {
            JSONObject dataJson = new JSONObject(result);
            String code = dataJson.getString("code");
            String message = dataJson.getString("message");
            JSONObject dataJson2 = dataJson.getJSONObject("data");
            update = new Update();
            if (update != null){
                update.setVersionCode(Integer.parseInt(dataJson2.getString("proup")));
                update.setDownloadUrl(dataJson2.getString("prourl"));
            }
            Log.d("版本更新数据", "versonCode==" + dataJson2.getString("proup") + "--" + "DownloadUrl=" + dataJson2.getString("prourl") + "--" + code + "--" + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return update;

    }
    //     "code": "0",
//             "message": "成功"，
//             "data":{
//        "proup":"1.0.0.2",
//                "prourl":"http://122.2.66.66:8080/proapk/20171116.apk"
//    }
//}
}
