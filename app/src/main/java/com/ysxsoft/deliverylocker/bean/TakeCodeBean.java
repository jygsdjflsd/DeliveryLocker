package com.ysxsoft.deliverylocker.bean;

public class TakeCodeBean {


    /**
     * status : 0
     * msg :
     * result : {"door_number":"12"}
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
         * door_number : 12
         */

        private String door_number;

        public String getDoor_number() {
            return door_number;
        }

        public void setDoor_number(String door_number) {
            this.door_number = door_number;
        }
    }
}
