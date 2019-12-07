package com.ysxsoft.deliverylocker.bus;

public class OverTimeBus {

    private String text = "超时收费通知";

    private String orderid;

    public OverTimeBus(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderid() {
        return orderid == null ? "" : orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
}
