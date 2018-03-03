package com.nenguou.dayuandaily.Model;

/**
 * Created by binguner on 2018/2/24.
 */

public class Captcha {
    /**
     * code : 1
     * msg : success
     * data : {"captchaUrl":"/captcha/5/88e0ddc6-8edd-4467-be8d-78c999e50dd9.jpg","cookies":"879c06766d4648d59b95a40f26ae91b9"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * captchaUrl : /captcha/5/88e0ddc6-8edd-4467-be8d-78c999e50dd9.jpg
         * cookies : 879c06766d4648d59b95a40f26ae91b9
         */

        private String captchaUrl;
        private String cookies;

        public String getCaptchaUrl() {
            return captchaUrl;
        }

        public void setCaptchaUrl(String captchaUrl) {
            this.captchaUrl = captchaUrl;
        }

        public String getCookies() {
            return cookies;
        }

        public void setCookies(String cookies) {
            this.cookies = cookies;
        }
    }
}
