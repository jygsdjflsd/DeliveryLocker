package com.ysxsoft.deliverylocker.api;

public class Url {


//    public static final String BASE_URL = "https://iot.modoubox.com/";
    public static final String BASE_URL = "https://iot.dev.modoubox.com/";
//    public static final String BASE_URL_PHP = "http://xintian.modoubox.com/";//正式服
    public static final String BASE_URL_PHP = "http://express.admin.modoubox.com/";//测试服
    /**
     * 工具 ================================================================================
     */
    static final String APP_FACILITY_CANUSE = BASE_URL + "api/control_app/status";//设备注册&获取设备信息

    static final String APP_QRCODE = BASE_URL + "web_wechat/deliver/qrcode";//获取二维码
    static final String APP_TAKECODE = BASE_URL + "cabinet/open_door";//取件码开门
    static final String APP_NET_ONLINE = BASE_URL + "cabinet/tcp_online_check";//假死检测
    static final String APP_ERROR_LOG= BASE_URL + "api/error_report";//错误日志上报


    static final String APP_UPDATA_APP= BASE_URL_PHP + "api_cabinet/base/checkVersion";//更新

    /** 取件超时*/
    static final String APP_OVERTIME = BASE_URL_PHP + "api_cabinet/Deliverorder/getOvertimeQrcode";
    static final String APP_OVERTIMEQUERY = BASE_URL_PHP + "api_cabinet/Deliverorder/checkSupplementalPaid";

}
