package com.bssf.john_li.coinhand.CHModel;

import java.util.List;

/**
 * Created by John_Li on 24/1/2018.
 */

public class OrderListOutModel {

    /**
     * code : 200
     * msg : null
     * data : [{"id":19,"userId":16,"orderNo":"U201710221223000520001","orderStatus":3,"orderType":4,"machineNo":"60681","carId":12,"startSlotTime":1506747553000,"totalAmount":40,"discountAmount":0,"payAmount":40,"couponId":null,"remark":"xxxxxxxxxxxxxxx","monthNum":0,"isDelete":0,"createTime":1508646181000,"updateTime":1524582993000,"updateTime0":null,"carType":1,"createTime0":null,"startSlotTime0":null,"pillarColor":"yellow","areaCode":"QT","parkingSpace":"","synTradeNo":null,"asynTradeNo":null,"img1":"objectName1","img2":"objectName2","img3":"objectName3","img4":"objectName4","img5":"objectName5","toushouAmount":0,"toushouUserId":null}]
     */

    private int code;
    private Object msg;
    private List<OrderModel> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public List<OrderModel> getData() {
        return data;
    }

    public void setData(List<OrderModel> data) {
        this.data = data;
    }

    public static class OrderModel {
        /**
         * id : 19
         * userId : 16
         * orderNo : U201710221223000520001
         * orderStatus : 3
         * orderType : 4
         * machineNo : 60681
         * carId : 12
         * startSlotTime : 1506747553000
         * totalAmount : 40
         * discountAmount : 0
         * payAmount : 40
         * couponId : null
         * remark : xxxxxxxxxxxxxxx
         * monthNum : 0
         * isDelete : 0
         * createTime : 1508646181000
         * updateTime : 1524582993000
         * updateTime0 : null
         * carType : 1
         * createTime0 : null
         * startSlotTime0 : null
         * pillarColor : yellow
         * areaCode : QT
         * parkingSpace :
         * synTradeNo : null
         * asynTradeNo : null
         * img1 : objectName1
         * img2 : objectName2
         * img3 : objectName3
         * img4 : objectName4
         * img5 : objectName5
         * toushouAmount : 0
         * toushouUserId : null
         */

        private int id;
        private int userId;
        private String orderNo;
        private int orderStatus;
        private int orderType;
        private String machineNo;
        private int carId;
        private long startSlotTime;
        private int totalAmount;
        private int discountAmount;
        private int payAmount;
        private Object couponId;
        private String remark;
        private int monthNum;
        private int isDelete;
        private long createTime;
        private long updateTime;
        private Object updateTime0;
        private int carType;
        private Object createTime0;
        private Object startSlotTime0;
        private String pillarColor;
        private String areaCode;
        private String parkingSpace;
        private Object synTradeNo;
        private Object asynTradeNo;
        private String img1;
        private String img2;
        private String img3;
        private String img4;
        private String img5;
        private int toushouAmount;
        private Object toushouUserId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public String getMachineNo() {
            return machineNo;
        }

        public void setMachineNo(String machineNo) {
            this.machineNo = machineNo;
        }

        public int getCarId() {
            return carId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }

        public long getStartSlotTime() {
            return startSlotTime;
        }

        public void setStartSlotTime(long startSlotTime) {
            this.startSlotTime = startSlotTime;
        }

        public int getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(int totalAmount) {
            this.totalAmount = totalAmount;
        }

        public int getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(int discountAmount) {
            this.discountAmount = discountAmount;
        }

        public int getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(int payAmount) {
            this.payAmount = payAmount;
        }

        public Object getCouponId() {
            return couponId;
        }

        public void setCouponId(Object couponId) {
            this.couponId = couponId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getMonthNum() {
            return monthNum;
        }

        public void setMonthNum(int monthNum) {
            this.monthNum = monthNum;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public Object getUpdateTime0() {
            return updateTime0;
        }

        public void setUpdateTime0(Object updateTime0) {
            this.updateTime0 = updateTime0;
        }

        public int getCarType() {
            return carType;
        }

        public void setCarType(int carType) {
            this.carType = carType;
        }

        public Object getCreateTime0() {
            return createTime0;
        }

        public void setCreateTime0(Object createTime0) {
            this.createTime0 = createTime0;
        }

        public Object getStartSlotTime0() {
            return startSlotTime0;
        }

        public void setStartSlotTime0(Object startSlotTime0) {
            this.startSlotTime0 = startSlotTime0;
        }

        public String getPillarColor() {
            return pillarColor;
        }

        public void setPillarColor(String pillarColor) {
            this.pillarColor = pillarColor;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getParkingSpace() {
            return parkingSpace;
        }

        public void setParkingSpace(String parkingSpace) {
            this.parkingSpace = parkingSpace;
        }

        public Object getSynTradeNo() {
            return synTradeNo;
        }

        public void setSynTradeNo(Object synTradeNo) {
            this.synTradeNo = synTradeNo;
        }

        public Object getAsynTradeNo() {
            return asynTradeNo;
        }

        public void setAsynTradeNo(Object asynTradeNo) {
            this.asynTradeNo = asynTradeNo;
        }

        public String getImg1() {
            return img1;
        }

        public void setImg1(String img1) {
            this.img1 = img1;
        }

        public String getImg2() {
            return img2;
        }

        public void setImg2(String img2) {
            this.img2 = img2;
        }

        public String getImg3() {
            return img3;
        }

        public void setImg3(String img3) {
            this.img3 = img3;
        }

        public String getImg4() {
            return img4;
        }

        public void setImg4(String img4) {
            this.img4 = img4;
        }

        public String getImg5() {
            return img5;
        }

        public void setImg5(String img5) {
            this.img5 = img5;
        }

        public int getToushouAmount() {
            return toushouAmount;
        }

        public void setToushouAmount(int toushouAmount) {
            this.toushouAmount = toushouAmount;
        }

        public Object getToushouUserId() {
            return toushouUserId;
        }

        public void setToushouUserId(Object toushouUserId) {
            this.toushouUserId = toushouUserId;
        }
    }
}
