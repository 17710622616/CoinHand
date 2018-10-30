package com.bssf.john_li.coinhand.CHModel;

import java.util.List;

/**
 * Created by John_Li on 24/1/2018.
 */

public class OrderListOutModel {
    /**
     * code : 200
     * msg : null
     * data : [{"order":{"id":152,"userId":16,"orderNo":"S201805211006369040001","orderStatus":3,"orderType":3,"machineNo":"86755","carId":30,"startSlotTime":1526875585000,"totalAmount":61,"discountAmount":0,"payAmount":61,"couponId":"","remark":"666","monthNum":0,"isDelete":0,"createTime":1526868396000,"updateTime":1527318461000,"updateTime0":1527318461000,"carType":2,"createTime0":1526868396000,"startSlotTime0":1526868396000,"pillarColor":"red","areaCode":"HDMT","parkingSpace":"","synTradeNo":"","asynTradeNo":"S201805211006369040001","img1":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/l/homepage/appcenter/zh-tw/579.bmp","img2":"","img3":"","img4":"","img5":"","toushouAmount":0,"toushouUserId":1526875585000},"soltMachine":{"id":729,"machineNo":"86755","carType":2,"pillarColor":"red","longitude":113.5357,"latitude":22.2121,"areaCode":"HDMT","address":"青州河邊馬路","parkingSpaces":"","isDelete":0,"createTime":1526868396000,"updateTime":1526868396000,"updateTime0":1526868396000,"createTime0":1526868396000}}]
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
         * order : {"id":152,"userId":16,"orderNo":"S201805211006369040001","orderStatus":3,"orderType":3,"machineNo":"86755","carId":30,"startSlotTime":1526875585000,"totalAmount":61,"discountAmount":0,"payAmount":61,"couponId":"","remark":"666","monthNum":0,"isDelete":0,"createTime":1526868396000,"updateTime":1527318461000,"updateTime0":1527318461000,"carType":2,"createTime0":1526868396000,"startSlotTime0":1526868396000,"pillarColor":"red","areaCode":"HDMT","parkingSpace":"","synTradeNo":"","asynTradeNo":"S201805211006369040001","img1":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/l/homepage/appcenter/zh-tw/579.bmp","img2":"","img3":"","img4":"","img5":"","toushouAmount":0,"toushouUserId":1526875585000}
         * soltMachine : {"id":729,"machineNo":"86755","carType":2,"pillarColor":"red","longitude":113.5357,"latitude":22.2121,"areaCode":"HDMT","address":"青州河邊馬路","parkingSpaces":"","isDelete":0,"createTime":1526868396000,"updateTime":1526868396000,"updateTime0":1526868396000,"createTime0":1526868396000}
         */

        private OrderBean order;
        private SoltMachineBean soltMachine;

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public SoltMachineBean getSoltMachine() {
            return soltMachine;
        }

        public void setSoltMachine(SoltMachineBean soltMachine) {
            this.soltMachine = soltMachine;
        }

        public static class OrderBean {
            /**
             * id : 152
             * userId : 16
             * orderNo : S201805211006369040001
             * orderStatus : 3
             * orderType : 3
             * machineNo : 86755
             * carId : 30
             *"carNO": "M1234",
             * startSlotTime : 1526875585000
             * totalAmount : 61
             * discountAmount : 0
             * payAmount : 61
             * couponId :
             * remark : 666
             * monthNum : 0
             * isDelete : 0
             * createTime : 1526868396000
             * updateTime : 1527318461000
             * updateTime0 : 1527318461000
             * carType : 2
             * createTime0 : 1526868396000
             * startSlotTime0 : 1526868396000
             * pillarColor : red
             * areaCode : HDMT
             * parkingSpace :
             * synTradeNo :
             * asynTradeNo : S201805211006369040001
             * img1 : http://test-pic-666.oss-cn-hongkong.aliyuncs.com/l/homepage/appcenter/zh-tw/579.bmp
             * img2 :
             * img3 :
             * img4 :
             * img5 :
             * toushouAmount : 0
             * toushouUserId : 1526875585000
             */

            private int id;
            private int userId;
            private String orderNo;
            private int orderStatus;
            private int orderType;
            private String machineNo;
            private int carId;
            private String carNO;
            private long startSlotTime;
            private double totalAmount;
            private double discountAmount;
            private double payAmount;
            private String couponId;
            private String remark;
            private int monthNum;
            private int isDelete;
            private long createTime;
            private long updateTime;
            private long updateTime0;
            private int carType;
            private long createTime0;
            private long startSlotTime0;
            private String pillarColor;
            private String areaCode;
            private String parkingSpace;
            private String synTradeNo;
            private String asynTradeNo;
            private String img1;
            private String img2;
            private String img3;
            private String img4;
            private String img5;
            private double toushouAmount;
            private long toushouUserId;
            private String currency;
            private String exchange;
            private double exchangeAmountPay;

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public String getExchange() {
                return exchange;
            }

            public void setExchange(String exchange) {
                this.exchange = exchange;
            }

            public double getExchangeAmountPay() {
                return exchangeAmountPay;
            }

            public void setExchangeAmountPay(double exchangeAmountPay) {
                this.exchangeAmountPay = exchangeAmountPay;
            }

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

            public String getCarNO() {
                return carNO;
            }

            public void setCarNO(String carNO) {
                this.carNO = carNO;
            }

            public long getStartSlotTime() {
                return startSlotTime;
            }

            public void setStartSlotTime(long startSlotTime) {
                this.startSlotTime = startSlotTime;
            }

            public double getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(double totalAmount) {
                this.totalAmount = totalAmount;
            }

            public double getDiscountAmount() {
                return discountAmount;
            }

            public void setDiscountAmount(double discountAmount) {
                this.discountAmount = discountAmount;
            }

            public double getPayAmount() {
                return payAmount;
            }

            public void setPayAmount(double payAmount) {
                this.payAmount = payAmount;
            }

            public String getCouponId() {
                return couponId;
            }

            public void setCouponId(String couponId) {
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

            public long getUpdateTime0() {
                return updateTime0;
            }

            public void setUpdateTime0(long updateTime0) {
                this.updateTime0 = updateTime0;
            }

            public int getCarType() {
                return carType;
            }

            public void setCarType(int carType) {
                this.carType = carType;
            }

            public long getCreateTime0() {
                return createTime0;
            }

            public void setCreateTime0(long createTime0) {
                this.createTime0 = createTime0;
            }

            public long getStartSlotTime0() {
                return startSlotTime0;
            }

            public void setStartSlotTime0(long startSlotTime0) {
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

            public String getSynTradeNo() {
                return synTradeNo;
            }

            public void setSynTradeNo(String synTradeNo) {
                this.synTradeNo = synTradeNo;
            }

            public String getAsynTradeNo() {
                return asynTradeNo;
            }

            public void setAsynTradeNo(String asynTradeNo) {
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

            public double getToushouAmount() {
                return toushouAmount;
            }

            public void setToushouAmount(double toushouAmount) {
                this.toushouAmount = toushouAmount;
            }

            public long getToushouUserId() {
                return toushouUserId;
            }

            public void setToushouUserId(long toushouUserId) {
                this.toushouUserId = toushouUserId;
            }
        }

        public static class SoltMachineBean {
            /**
             * id : 729
             * machineNo : 86755
             * carType : 2
             * pillarColor : red
             * longitude : 113.5357
             * latitude : 22.2121
             * areaCode : HDMT
             * address : 青州河邊馬路
             * parkingSpaces :
             * isDelete : 0
             * createTime : 1526868396000
             * updateTime : 1526868396000
             * updateTime0 : 1526868396000
             * createTime0 : 1526868396000
             */

            private int id;
            private String machineNo;
            private int carType;
            private String pillarColor;
            private double longitude;
            private double latitude;
            private String areaCode;
            private String address;
            private String parkingSpaces;
            private int isDelete;
            private long createTime;
            private long updateTime;
            private long updateTime0;
            private long createTime0;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMachineNo() {
                return machineNo;
            }

            public void setMachineNo(String machineNo) {
                this.machineNo = machineNo;
            }

            public int getCarType() {
                return carType;
            }

            public void setCarType(int carType) {
                this.carType = carType;
            }

            public String getPillarColor() {
                return pillarColor;
            }

            public void setPillarColor(String pillarColor) {
                this.pillarColor = pillarColor;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public String getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getParkingSpaces() {
                return parkingSpaces;
            }

            public void setParkingSpaces(String parkingSpaces) {
                this.parkingSpaces = parkingSpaces;
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

            public long getUpdateTime0() {
                return updateTime0;
            }

            public void setUpdateTime0(long updateTime0) {
                this.updateTime0 = updateTime0;
            }

            public long getCreateTime0() {
                return createTime0;
            }

            public void setCreateTime0(long createTime0) {
                this.createTime0 = createTime0;
            }
        }
    }
}
