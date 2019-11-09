package com.ysxsoft.deliverylocker.bean;

import java.io.Serializable;
import java.util.List;

/**
 * status为2的 设备信息
 */
public class DeviceBean implements Serializable {

    /**
     * status : 2
     * msg :
     * result : {"company":{"register_key":"register:885a61c30a9cba5a152cabce2777f39","property":"开发android小区","tag":"大门口","service_tel":"18600283835","company_id":172},"ads":[{"url":"http://public.u-xuan.com/%E6%B5%81%E7%A8%8B%E5%9B%BE.png?a=10","type":"image","position":"main-left-10"}]}
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

    public static class ResultBean implements Serializable{
        /**
         * company : {"register_key":"register:885a61c30a9cba5a152cabce2777f39","property":"开发android小区","tag":"大门口","service_tel":"18600283835","company_id":172}
         * ads : [{"url":"http://public.u-xuan.com/%E6%B5%81%E7%A8%8B%E5%9B%BE.png?a=10","type":"image","position":"main-left-10"}]
         */

        private CompanyBean company;
        private List<AdsBean> ads;

        public CompanyBean getCompany() {
            return company;
        }

        public void setCompany(CompanyBean company) {
            this.company = company;
        }

        public List<AdsBean> getAds() {
            return ads;
        }

        public void setAds(List<AdsBean> ads) {
            this.ads = ads;
        }

        public static class CompanyBean implements Serializable{
            /**
             * register_key : register:885a61c30a9cba5a152cabce2777f39
             * property : 开发android小区
             * tag : 大门口
             * service_tel : 18600283835
             * company_id : 172
             */

            private String register_key;
            private String property;
            private String tag;
            private String service_tel;
            private int company_id;

            public String getRegister_key() {
                return register_key;
            }

            public void setRegister_key(String register_key) {
                this.register_key = register_key;
            }

            public String getProperty() {
                return property;
            }

            public void setProperty(String property) {
                this.property = property;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getService_tel() {
                return service_tel;
            }

            public void setService_tel(String service_tel) {
                this.service_tel = service_tel;
            }

            public int getCompany_id() {
                return company_id;
            }

            public void setCompany_id(int company_id) {
                this.company_id = company_id;
            }
        }

        public static class AdsBean implements Serializable{
            /**
             * url : http://public.u-xuan.com/%E6%B5%81%E7%A8%8B%E5%9B%BE.png?a=10
             * type : image
             * position : main-left-10
             */

            private String url;
            private String type;
            private String position;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }
        }
    }
}
