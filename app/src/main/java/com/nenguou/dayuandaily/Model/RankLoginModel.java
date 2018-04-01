package com.nenguou.dayuandaily.Model;

public class RankLoginModel {

    /**
     * code : 1
     * msg : 登陆成功
     * data : ASP.NET_SessionId=kiss2ktwvpw3loirqnu5mfgr; path=/; HttpOnly512610BFAA3692D1=5D1CE9A1B71462F0; path=/1B8CF2BA879BBCEFD588FFEF45F4514F=3BE5071BB5291BEDE4495310915DDBDC; path=/FE71C29C107FD41840E03E3C77C1E504=FEB7CA42954C80D4; path=/B56CAB057E98CEA01AA21270A577C9D3=8157603D753149D6; path=/2587657B10AEBB0B=7107699426DC7179E64FF2BC4AB6A322; path=/D839906CC55542B9=84C1CBE661C00CDE; path=/F5CC61A7AF3AE4FF=E895594D8744FCB1; path=/9BACB9BEC5A5CEC9=73E475E459DB246A; path=/
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
