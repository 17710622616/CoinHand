package com.bssf.john_li.coinhand.CHModel;

/**
 * Created by John_Li on 2/5/2018.
 */

public class UserInfoModel {

    /**
     * code : 200
     * msg : success
     * data : {"username":"xiaoma66","qstoken":"131FBB3ABD3BE5CD04332191357AB8B1C3215F2298DA863011FFE508BC6158643138E36ABCD13CF283DC8C277213375CCAF7D93F8DAD84954706A48412189C61F5E4820D253753D33B4A49AFAC4FEC43"}
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
         * username : xiaoma66
         * qstoken : 131FBB3ABD3BE5CD04332191357AB8B1C3215F2298DA863011FFE508BC6158643138E36ABCD13CF283DC8C277213375CCAF7D93F8DAD84954706A48412189C61F5E4820D253753D33B4A49AFAC4FEC43
         */

        private String username;
        private String qstoken;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getQstoken() {
            return qstoken;
        }

        public void setQstoken(String qstoken) {
            this.qstoken = qstoken;
        }
    }
}
