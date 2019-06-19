package com.idcard.hnd.idcardjchl.bean;

/**
 * Created by Administrator on 2019/6/17.
 */

public class CheckIdCardInfo {
    private String code;
    private String message;
    private String bz;
    private String sfzh;
    private String xm;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getBz() {
        return bz;
    }

    public String getSfzh() {
        return sfzh;
    }

    public String getXm() {
        return xm;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    @Override
    public String toString() {
        return "CheckIdCardInfo{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", bz='" + bz + '\'' +
                ", sfzh='" + sfzh + '\'' +
                ", xm='" + xm + '\'' +
                '}';
    }
}
