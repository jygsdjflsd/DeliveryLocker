package com.ysxsoft.deliverylocker.api;

public class Url {


    public static final String BASE_URL = "https://iot.modoubox.com/";
    /**
     * 工具 ================================================================================
     */
    static final String APP_FACILITY_CANUSE = BASE_URL + "api/control_app/status";//设备注册&获取设备信息

    static final String APP_QRCODE = BASE_URL + "web_wechat/deliver/qrcode";//获取二维码
    static final String APP_TAKECODE = BASE_URL + "cabinet/open_door";//取件码开门

}
