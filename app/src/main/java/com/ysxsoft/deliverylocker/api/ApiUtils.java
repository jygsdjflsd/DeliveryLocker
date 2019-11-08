package com.ysxsoft.deliverylocker.api;


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
    public static void getFacility(String device_id, AbsPostJsonStringCb absPostJsonStringCb) {
        OkGoUtils.RequestOption option = new OkGoUtils.RequestOption();
        option.params = new HashMap<>();
        option.params.put("device_id", device_id);
        option.isNormalDeal = false;
        option.url = Url.APP_FACILITY_CANUSE;
        option.iPostJsonStringCb = absPostJsonStringCb;
        OkGoUtils.postJsonStringCallback(option);
    }


}
