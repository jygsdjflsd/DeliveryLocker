package com.ysxsoft.deliverylocker.bus;

public class PingBus {

    private boolean pingSuccess;

    private String text = "ping网络返回";

    public PingBus(boolean pingSuccess) {
        this.pingSuccess = pingSuccess;
    }

    public boolean isPingSuccess() {
        return pingSuccess;
    }

    public void setPingSuccess(boolean pingSuccess) {
        this.pingSuccess = pingSuccess;
    }
}
