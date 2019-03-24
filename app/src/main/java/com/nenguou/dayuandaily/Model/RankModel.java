package com.nenguou.dayuandaily.Model;

public class RankModel {

    /**
     * code : 1
     * msg : success
     * data : [{"xh":"2016006593","xm":"郑左宇","bjh":"软件1632","bm":"软件1632","zyh":"160101","zym":"软件工程","xsh":"16","xsm":"软件学院","njdm":"2016","yqzxf":"186.50","yxzzsjxf":"","zxf":"74","yxzxf":"74","cbjgxf":"6","sbjgxf":"0","pjxfjd":"3.11","gpabjpm":"9","gpazypm":"267","pjcj":"80.52","pjcjbjpm":"8","pjcjzypm":"242","jqxfcj":"79.18","jqbjpm":"8","jqzypm":"216","tsjqxfcj":"79.18","tjsj":"2018-04-01 01:00:10","bjrs":"33","zyrs":"1006","dlrs":"","gpadlpm":"3446"}]
     */

    private int code;
    private String msg;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
