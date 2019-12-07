package com.ysxsoft.deliverylocker.bean;

import android.util.Log;

import com.google.gson.Gson;
import com.ysxsoft.deliverylocker.api.ApiUtils;
import com.ysxsoft.deliverylocker.bus.DeviceRefreshBus;
import com.ysxsoft.deliverylocker.network.AbsPostJsonStringCb;
import com.ysxsoft.deliverylocker.ui.activity.MainActivity;
import com.ysxsoft.deliverylocker.utils.MD5Util;
import com.ysxsoft.deliverylocker.utils.SystemUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceInfo {

    private static DeviceInfo info;

    public static DeviceInfo getIntence(){
        if (info == null){
            synchronized (DeviceInfo.class){
                if (info == null){
                    info = new DeviceInfo();
                }
            }
        }
        return info;
    }
    private DeviceInfo() {
    }

    private DeviceBean deviceBean;

    public DeviceBean getDeviceBean() {
        return deviceBean;
    }

    public void setDeviceBean(DeviceBean deviceBean) {
        this.deviceBean = deviceBean;
    }

    public String register_key(){
        if (deviceBean == null || deviceBean.getResult() == null || deviceBean.getResult().getCompany() == null){
            return "";
        }
        return deviceBean.getResult().getCompany().getRegister_key();
    }
    public String getTag(){
        if (deviceBean == null || deviceBean.getResult() == null || deviceBean.getResult().getCompany() == null){
            return "";
        }
        return deviceBean.getResult().getCompany().getTag();
    }
    public String getProperty(){
        if (deviceBean == null || deviceBean.getResult() == null || deviceBean.getResult().getCompany() == null){
            return "";
        }
        return deviceBean.getResult().getCompany().getProperty();
    }
    public String getLogo(){
        if (deviceBean == null || deviceBean.getResult() == null || deviceBean.getResult().getCompany() == null){
            return "";
        }
        return deviceBean.getResult().getCompany().getLogo();
    }
    public String getService_tel(){
        if (deviceBean == null || deviceBean.getResult() == null || deviceBean.getResult().getCompany() == null){
            return "";
        }
        return deviceBean.getResult().getCompany().getService_tel();
    }
    public int getCompany_id(){
        if (deviceBean == null || deviceBean.getResult() == null || deviceBean.getResult().getCompany() == null){
            return 0;
        }
        return deviceBean.getResult().getCompany().getCompany_id();
    }

    /**
     * 刷新设备信息
     */
    public void refreshDeviceInfo(){
        String device_id = MD5Util.md5Decode32(SystemUtil.getImei() + "iot");
        Log.e("device_id", device_id);
        ApiUtils.getFacility(device_id, SystemUtil.getSimId(), new AbsPostJsonStringCb() {
            @Override
            public void onSuccess(String str, String data) {
                try {
                    JSONObject obj = new JSONObject(str);
                    int status = obj.optInt("status");
                    if (status == 0) {//设备未绑定 需要注册
                    } else if (status == 1) {//设备不合法

                    }else if (status == 2){//设备合法 进入取件界面
                        DeviceBean device = new Gson().fromJson(str, DeviceBean.class);
                        DeviceInfo.getIntence().setDeviceBean(device);
                        EventBus.getDefault().post(new DeviceRefreshBus());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFinish() {
            }
        });
    }
}
