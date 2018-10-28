package com.bssf.john_li.coinhand.CHModel;

/**
 * Created by John_Li on 21/9/2018.
 */

public class CheckUnfinishModel {
    /**
     * code : 70008
     * msg : 投手存在未完成订单
     * data : {"orderNo":"S201807311246246400001"}
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
         * orderNo : S201807311246246400001
         */

        private String orderNo;

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }
    }
}
