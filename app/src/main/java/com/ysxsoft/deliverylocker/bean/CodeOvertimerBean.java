package com.ysxsoft.deliverylocker.bean;

public class CodeOvertimerBean {

    /**
     * status : 0
     * msg : 订单超时，请先支付超时费用。
     * result : {"captcha_id":1,"qrcode":"XXXXXXX"}
     */

    private int status;
    private String msg;
    private ResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * captcha_id : 1
         * qrcode : XXXXXXX
         */

        private int captcha_id;
        private String qrcode;

        public int getCaptcha_id() {
            return captcha_id;
        }

        public void setCaptcha_id(int captcha_id) {
            this.captcha_id = captcha_id;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }
    }
}
