package com.nenguou.dayuandaily.Model;

import java.util.List;

/**
 * Created by binguner on 2018/2/15.
 */

public class ClassName {

    /**
     * code : 1
     * msg : success
     * data : ["软件1609","软件1611","软件1614","软件1632","软件1631","软件1630","软件1629","软件1628","软件1627","软件1626","软件1625","软件1604","软件1624","软件1623","软件1622","软件1621","软件1620","软件1619","软件1618","软件1617","软件1616","软件1615","软件1613","软件1612","软件1610","软件1608","软件1607","软件1606","软件1605","软件1603","软件1602","软件1601"]
     */

    private int code;
    private String msg;
    private List<String> data;

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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
