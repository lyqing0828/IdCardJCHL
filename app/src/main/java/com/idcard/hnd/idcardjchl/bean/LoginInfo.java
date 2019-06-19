package com.idcard.hnd.idcardjchl.bean;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2019/6/13.
 */

public class LoginInfo {
    private String code;
    private String message;
    private String bmdm;//部门代码
    private String jybh;//警员编号
    private String jwtmm;//警务通密码
    private String xm;//姓名

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(String code) {
        this.code = code;

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBmdm() {
        return bmdm;
    }

    public String getJybh() {
        return jybh;
    }

    public String getJwtmm() {
        return jwtmm;
    }

    public void setBmdm(String bmdm) {
        this.bmdm = bmdm;
    }

    public void setJybh(String jybh) {
        this.jybh = jybh;
    }

    public void setJwtmm(String jwtmm) {
        this.jwtmm = jwtmm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getXm() {
        return xm;

    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "bmdm='" + bmdm + '\'' +
                ", jybh='" + jybh + '\'' +
                ", jwtmm='" + jwtmm + '\'' +
                ", xm='" + xm + '\'' +
                '}';
    }

//    public static LoginInfo parseLoginResult(String result) {
//        LoginInfo login = new LoginInfo();
//        try {
////            if (TextUtils.isEmpty(result)) {
////                return;
////            }
//            JSONObject dataJson = new JSONObject(result);
//            String code = dataJson.getString("code");
//            String message = dataJson.getString("message");
//            JSONObject dataJson2 = dataJson.getJSONObject("data");
//            String bmdm = dataJson2.getString("bmdm");
//            String jwtmm = dataJson2.getString("jwtmm");
//            String jybh = dataJson2.getString("jybh");
//            String xm = dataJson2.getString("xm");
//            Log.d("数据", code + "--" + bmdm + "--" + jwtmm + "--" + jybh + "--" + xm + "--" + message);
//            login.setCode(code);
//            login.setMessage(message);
//            login.setBmdm(bmdm);
//            login.setJwtmm(jwtmm);
//            login.setJybh(jybh);
//            login.setXm(xm);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return login;
//    }
}
