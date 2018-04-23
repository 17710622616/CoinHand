package com.bssf.john_li.coinhand.CHModel;

import java.util.List;

/**
 * 常用的外層解析累
 * Created by John on 17/9/2017.
 */

public class GetWorkAreaOutModel {

    /**
     * code : 200
     * msg :
     * data : ["QT","DT","WDT"]
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
