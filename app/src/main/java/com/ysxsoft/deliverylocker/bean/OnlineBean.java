package com.ysxsoft.deliverylocker.bean;

public class OnlineBean {

    /**
     * status : 0
     * msg :
     * result : {"last_heartbeat":1572963901445}
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
         * last_heartbeat : 1572963901445
         */

        private long last_heartbeat;

        public long getLast_heartbeat() {
            return last_heartbeat;
        }

        public void setLast_heartbeat(long last_heartbeat) {
            this.last_heartbeat = last_heartbeat;
        }
    }
}
