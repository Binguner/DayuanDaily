package com.nenguou.dayuandaily.Model;

import java.util.List;

/**
 * Created by binguner on 2018/2/15.
 */

public class Major2 {
    /**
     * code : 1
     * msg : success
     * data : [{"id":3,"collegeId":3,"major":"信息安全"},{"id":4,"collegeId":3,"major":"测控技术与仪器"},{"id":5,"collegeId":3,"major":"物联网工程"},{"id":6,"collegeId":3,"major":"电子信息工程"},{"id":7,"collegeId":3,"major":"电子科学与技术"},{"id":8,"collegeId":3,"major":"计算机科学与技术"},{"id":9,"collegeId":3,"major":"通信工程"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3
         * collegeId : 3
         * major : 信息安全
         */

        private int id;
        private int collegeId;
        private String major;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCollegeId() {
            return collegeId;
        }

        public void setCollegeId(int collegeId) {
            this.collegeId = collegeId;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }
    }
}
