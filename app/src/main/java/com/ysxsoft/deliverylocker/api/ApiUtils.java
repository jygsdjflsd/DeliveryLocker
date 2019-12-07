package com.ysxsoft.deliverylocker.api;


import android.util.Log;

import com.ysxsoft.deliverylocker.network.AbsPostJsonStringCb;
import com.ysxsoft.deliverylocker.network.OkGoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApiUtils {

    /**
     * 设备注册&获取设备信息
     *
     * @param device_id           imei
     * @param absPostJsonStringCb 回调
     */
    public static void getFacility(String device_id, String sim_iccid, AbsPostJsonStringCb absPostJsonStringCb) {
        OkGoUtils.RequestOption option = new OkGoUtils.RequestOption();
        option.params = new HashMap<>();
        option.params.put("device_id", device_id);
        option.params.put("sim_iccid", sim_iccid);
        option.isNormalDeal = false;
        option.url = Url.APP_FACILITY_CANUSE;
        option.iPostJsonStringCb = absPostJsonStringCb;
        OkGoUtils.postJsonStringCallback(option);
    }
    /**
     * 获取二维码
     *
     * @param register_key        key
     * @param absPostJsonStringCb 回调
     */
    public static void getQrCode(String register_key, AbsPostJsonStringCb absPostJsonStringCb) {
        OkGoUtils.RequestOption option = new OkGoUtils.RequestOption();
        option.params = new HashMap<>();
        option.params.put("from", "cabinet");
        option.params.put("register_key", register_key);
        option.isNormalDeal = false;
        option.url = Url.APP_QRCODE;
        option.iPostJsonStringCb = absPostJsonStringCb;
        OkGoUtils.postJsonStringCallback(option);
    }
    /**
     * 取件码开门
     *
     * @param register_key        key
     * @param absPostJsonStringCb 回调
     */
    public static void takeCode(String code, String register_key, AbsPostJsonStringCb absPostJsonStringCb) {
        OkGoUtils.RequestOption option = new OkGoUtils.RequestOption();
        option.params = new HashMap<>();
        option.params.put("from", "code-user");
        option.params.put("type", "get_by_code");
        option.params.put("code", code);
        option.params.put("register_key", register_key);
        option.isNormalDeal = false;
        option.url = Url.APP_TAKECODE;
        option.iPostJsonStringCb = absPostJsonStringCb;
        OkGoUtils.postJsonStringCallback(option);
    }
    /**
     * 长链接假死检测
     *
     * @param absPostJsonStringCb 回调
     */
    public static void netOnline(String register_key, AbsPostJsonStringCb absPostJsonStringCb) {
        OkGoUtils.RequestOption option = new OkGoUtils.RequestOption();
        option.params = new HashMap<>();
        option.params.put("register_key", register_key);
        option.isNormalDeal = false;
        option.url = Url.APP_NET_ONLINE;
        option.iPostJsonStringCb = absPostJsonStringCb;
        OkGoUtils.postJsonStringCallback(option);
    }
    /**
     * 错误上报
     *
     * @param absPostJsonStringCb 回调
     */
    public static void errorLog(String device_id, String version, String err_msg, AbsPostJsonStringCb absPostJsonStringCb) {
        OkGoUtils.RequestOption option = new OkGoUtils.RequestOption();
        option.params = new HashMap<>();
        option.params.put("device_id", device_id);
        option.params.put("version", version);
        option.params.put("err_msg", err_msg);
        option.isNormalDeal = false;
        option.url = Url.APP_ERROR_LOG;
        option.iPostJsonStringCb = absPostJsonStringCb;
        OkGoUtils.postJsonStringCallback(option);
    }
    /**
     * 更新app
     *
     * @param absPostJsonStringCb 回调
     */
    public static void updataApp(String register_key, String version, String version_code, AbsPostJsonStringCb absPostJsonStringCb) {
        Log.e("uptataApp", "register_key:"+ register_key+ " version:"+ version);
        OkGoUtils.RequestOption option = new OkGoUtils.RequestOption();
        option.params = new HashMap<>();
        option.params.put("register_key", register_key);
        option.params.put("version", version);
        option.params.put("version_code", version_code);
        option.isNormalDeal = false;
        option.url = Url.APP_UPDATA_APP;
        option.iPostJsonStringCb = absPostJsonStringCb;
        OkGoUtils.postJsonStringCallback(option);
    }


    /**
     * 快递相关- 超时收费获取二维码
     * @param captcha_id 订单id
     * @param absPostJsonStringCb 回调
     */
    public static void overTime(String register_key, String captcha_id,  AbsPostJsonStringCb absPostJsonStringCb) {
        OkGoUtils.RequestOption option = new OkGoUtils.RequestOption();
        option.params = new HashMap<>();
        option.params.put("captcha_id", captcha_id);
        option.params.put("register_key", register_key);
        option.isNormalDeal = false;
        option.url = Url.APP_OVERTIME;
        option.iPostJsonStringCb = absPostJsonStringCb;
        OkGoUtils.postJsonStringCallback(option);
    }
    /**
     * 快递相关- 超时收费获取二维码
     * @param captcha_id 订单id
     * @param absPostJsonStringCb 回调
     */
    public static void overTimeQuery(String captcha_id,  AbsPostJsonStringCb absPostJsonStringCb) {
        OkGoUtils.RequestOption option = new OkGoUtils.RequestOption();
        option.params = new HashMap<>();
        option.params.put("captcha_id", captcha_id);
        option.isNormalDeal = false;
        option.url = Url.APP_OVERTIMEQUERY;
        option.iPostJsonStringCb = absPostJsonStringCb;
        OkGoUtils.postJsonStringCallback(option);
    }
}
