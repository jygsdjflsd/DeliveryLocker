package com.ysxsoft.deliverylocker.bus;

/**
 * 获取二维码接口总线通知
 */
public class QrCodeBus {

    private String register_key;

    public QrCodeBus(String register_key) {
        this.register_key = register_key;
    }

    public String getRegister_key() {
        return register_key == null ? "" : register_key;
    }

    public void setRegister_key(String register_key) {
        this.register_key = register_key;
    }
}
