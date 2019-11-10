package com.ysxsoft.deliverylocker.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * status为2的 设备信息
 */
public class DeviceBean implements Serializable {

    /**
     * status : 2
     * msg :
     * result : {"company":{"logo":"https://iot.modoubox.com/cabinet_app/images/logo-white.png","register_key":"register:f89536201f34a9b0ea38b4a1633f73a","property":"美景天成","tag":"大门口","company_id":3,"service_tel":"18600283835"},"ads":[{"url":"http://public.u-xuan.com/%E6%B5%81%E7%A8%8B%E5%9B%BE.png?a=10","type":"image","position":"main-left-10"}]}
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
        return msg == null ? "" : msg;
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
         * company : {"logo":"https://iot.modoubox.com/cabinet_app/images/logo-white.png","register_key":"register:f89536201f34a9b0ea38b4a1633f73a","property":"美景天成","tag":"大门口","company_id":3,"service_tel":"18600283835"}
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
            if (ads == null) {
                return new ArrayList<>();
            }
            return ads;
        }

        public void setAds(List<AdsBean> ads) {
            this.ads = ads;
        }

        public static class CompanyBean implements Serializable{
            /**
             * logo : https://iot.modoubox.com/cabinet_app/images/logo-white.png
             * register_key : register:f89536201f34a9b0ea38b4a1633f73a
             * property : 美景天成
             * tag : 大门口
             * company_id : 3
             * service_tel : 18600283835
             */

            private String logo;
            private String register_key;
            private String property;
            private String tag;
            private int company_id;
            private String service_tel;

            public String getLogo() {
                return logo == null ? "" : logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getRegister_key() {
                return register_key == null ? "" : register_key;
            }

            public void setRegister_key(String register_key) {
                this.register_key = register_key;
            }

            public String getProperty() {
                return property == null ? "" : property;
            }

            public void setProperty(String property) {
                this.property = property;
            }

            public String getTag() {
                return tag == null ? "" : tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public int getCompany_id() {
                return company_id;
            }

            public void setCompany_id(int company_id) {
                this.company_id = company_id;
            }

            public String getService_tel() {
                return service_tel == null ? "" : service_tel;
            }

            public void setService_tel(String service_tel) {
                this.service_tel = service_tel;
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
                return url == null ? "" : url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getType() {
                return type == null ? "" : type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getPosition() {
                return position == null ? "" : position;
            }

            public void setPosition(String position) {
                this.position = position;
            }
        }
    }
}
