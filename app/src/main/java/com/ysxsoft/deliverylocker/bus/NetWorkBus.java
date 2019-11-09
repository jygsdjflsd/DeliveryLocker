package com.ysxsoft.deliverylocker.bus;

/**
 * 网络变化总线
 */
public class NetWorkBus {

    private String netName; //信号名称
    private boolean netIsLink;//信号链接情况

    public NetWorkBus(String netName, boolean netIsLink) {
        this.netName = netName;
        this.netIsLink = netIsLink;
    }

    public boolean isNetIsLink() {
        return netIsLink;
    }

    public void setNetIsLink(boolean netIsLink) {
        this.netIsLink = netIsLink;
    }

    public String getNetName() {
        return netName == null ? "" : netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }
}
