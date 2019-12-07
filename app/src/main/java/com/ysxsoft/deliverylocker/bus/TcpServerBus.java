package com.ysxsoft.deliverylocker.bus;

public class TcpServerBus {

    private long timeStamp;

    public TcpServerBus(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
