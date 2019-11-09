package com.ysxsoft.deliverylocker.bean;

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
}
