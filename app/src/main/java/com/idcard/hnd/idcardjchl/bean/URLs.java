package com.idcard.hnd.idcardjchl.bean;

/**
 * 接口URL实体类
 * Created by Administrator on 2019/6/17.
 */

public class URLs {
    //    String url = "http://192.168.0.111:8080/trffapi/police/login";
    public final static String HOST = "192.168.0.111:8080/trffapi";
    public final static String HTTP = "http://";
    public final static String HTTPS = "https://";

    private final static String URL_SPLITTER = "/";
    private final static String URL_UNDERLINE = "_";

    //登陆接口
    public final static String LOGIN_VALIDATE_HTTP = HTTP + HOST + URL_SPLITTER + "police/login";
    //    public final static String LOGIN_VALIDATE_HTTPS = HTTPS + HOST + URL_SPLITTER + "action/api/login_validate";
    //设备注册接口
    public final static String DEVICE_REGISTER_VALIDATE_HTTP = HTTP + HOST + URL_SPLITTER + "device/register";

    //检查站点查询
    public final static String STATION_CHECK_VALIDATE_HTTP = HTTP + HOST + URL_SPLITTER + "station/queryLocal";
    //身份核查
    public final static String PERSON_CHECK_VALIDATE_HTTP = HTTP + HOST + URL_SPLITTER + "personnel/check";
    //程序版本查询
    public final static String UPDATE_VERSION = HTTP + HOST + URL_SPLITTER + "pda/proQuery";
}
