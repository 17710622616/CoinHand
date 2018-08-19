package com.bssf.john_li.coinhand.CHModel;

import java.util.List;

/**
 * Created by John_Li on 8/5/2018.
 */

public class OrderDetialOutModel {

    /**
     * code : 200
     * msg :
     * data : {"order":{"id":367,"userId":16,"orderNo":"S201808031654296350001","orderStatus":3,"orderType":3,"machineNo":"4467","carId":30,"startSlotTime":1533293520000,"totalAmount":5,"discountAmount":0,"payAmount":5,"couponId":1,"remark":"","monthNum":0,"isDelete":0,"createTime":1533286469000,"updateTime":1533286483000,"updateTime0":1535710273000,"carType":1,"createTime0":1535710273000,"startSlotTime0":1535710273000,"pillarColor":"yellow","areaCode":"FST","parkingSpace":"","synTradeNo":"","asynTradeNo":"S201808031654296350001","img1":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/car20180309163702.jpg","img2":"","img3":"","img4":"","img5":"","toushouAmount":0,"toushouUserId":200},"currentToubiAmount":4,"toushouRecordList":[{"id":1,"orderNo":"U201710221222174430001","userId":11,"username":"test11","amount":4,"status":2,"createTime":1524583058000,"finishTime":1524583064000},{"id":2,"orderNo":"U201710221222174430001","userId":22,"username":"测试22","amount":4,"status":1,"createTime":1524583082000,"finishTime":1524583082000}],"soltMachine":{"id":841,"machineNo":"4467","carType":1,"pillarColor":"yellow","longitude":113.533549,"latitude":22.1915088,"areaCode":"FST","address":"風順堂區","parkingSpaces":"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26","isDelete":0,"createTime":1535710273000,"updateTime":1535710273000,"updateTime0":1535710273000,"createTime0":1535710273000},"car":{"id":30,"userId":16,"imgUrl":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/car20180309163702.jpg","ifPerson":2,"carNo":"m65-86","modelForCar":"SUV","carBrand":"q5","carStyle":"q5","ifPay":0,"isDelete":0,"createTime":1535710273000,"updateTime":1535710273000,"expiryTime":1535710273000}}
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
         * order : {"id":367,"userId":16,"orderNo":"S201808031654296350001","orderStatus":3,"orderType":3,"machineNo":"4467","carId":30,"startSlotTime":1533293520000,"totalAmount":5,"discountAmount":0,"payAmount":5,"couponId":1,"remark":"","monthNum":0,"isDelete":0,"createTime":1533286469000,"updateTime":1533286483000,"updateTime0":1535710273000,"carType":1,"createTime0":1535710273000,"startSlotTime0":1535710273000,"pillarColor":"yellow","areaCode":"FST","parkingSpace":"","synTradeNo":"","asynTradeNo":"S201808031654296350001","img1":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/car20180309163702.jpg","img2":"","img3":"","img4":"","img5":"","toushouAmount":0,"toushouUserId":200}
         * currentToubiAmount : 4
         * toushouRecordList : [{"id":1,"orderNo":"U201710221222174430001","userId":11,"username":"test11","amount":4,"status":2,"createTime":1524583058000,"finishTime":1524583064000},{"id":2,"orderNo":"U201710221222174430001","userId":22,"username":"测试22","amount":4,"status":1,"createTime":1524583082000,"finishTime":1524583082000}]
         * soltMachine : {"id":841,"machineNo":"4467","carType":1,"pillarColor":"yellow","longitude":113.533549,"latitude":22.1915088,"areaCode":"FST","address":"風順堂區","parkingSpaces":"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26","isDelete":0,"createTime":1535710273000,"updateTime":1535710273000,"updateTime0":1535710273000,"createTime0":1535710273000}
         * car : {"id":30,"userId":16,"imgUrl":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/car20180309163702.jpg","ifPerson":2,"carNo":"m65-86","modelForCar":"SUV","carBrand":"q5","carStyle":"q5","ifPay":0,"isDelete":0,"createTime":1535710273000,"updateTime":1535710273000,"expiryTime":1535710273000}
         */

        private OrderBean order;
        private int currentToubiAmount;
        private SoltMachineBean soltMachine;
        private CarBean car;
        private List<ToushouRecordListBean> toushouRecordList;

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public int getCurrentToubiAmount() {
            return currentToubiAmount;
        }

        public void setCurrentToubiAmount(int currentToubiAmount) {
            this.currentToubiAmount = currentToubiAmount;
        }

        public SoltMachineBean getSoltMachine() {
            return soltMachine;
        }

        public void setSoltMachine(SoltMachineBean soltMachine) {
            this.soltMachine = soltMachine;
        }

        public CarBean getCar() {
            return car;
        }

        public void setCar(CarBean car) {
            this.car = car;
        }

        public List<ToushouRecordListBean> getToushouRecordList() {
            return toushouRecordList;
        }

        public void setToushouRecordList(List<ToushouRecordListBean> toushouRecordList) {
            this.toushouRecordList = toushouRecordList;
        }

        public static class OrderBean {
            /**
             * id : 367
             * userId : 16
             * orderNo : S201808031654296350001
             * orderStatus : 3
             * orderType : 3
             * machineNo : 4467
             * carId : 30
             * startSlotTime : 1533293520000
             * totalAmount : 5
             * discountAmount : 0
             * payAmount : 5
             * couponId : 1
             * remark :
             * monthNum : 0
             * isDelete : 0
             * createTime : 1533286469000
             * updateTime : 1533286483000
             * updateTime0 : 1535710273000
             * carType : 1
             * createTime0 : 1535710273000
             * startSlotTime0 : 1535710273000
             * pillarColor : yellow
             * areaCode : FST
             * parkingSpace :
             * synTradeNo :
             * asynTradeNo : S201808031654296350001
             * img1 : http://test-pic-666.oss-cn-hongkong.aliyuncs.com/car20180309163702.jpg
             * img2 :
             * img3 :
             * img4 :
             * img5 :
             * toushouAmount : 0
             * toushouUserId : 200
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
            private int couponId;
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
            private int toushouAmount;
            private int toushouUserId;

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

            public int getCouponId() {
                return couponId;
            }

            public void setCouponId(int couponId) {
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

            public int getToushouAmount() {
                return toushouAmount;
            }

            public void setToushouAmount(int toushouAmount) {
                this.toushouAmount = toushouAmount;
            }

            public int getToushouUserId() {
                return toushouUserId;
            }

            public void setToushouUserId(int toushouUserId) {
                this.toushouUserId = toushouUserId;
            }
        }

        public static class SoltMachineBean {
            /**
             * id : 841
             * machineNo : 4467
             * carType : 1
             * pillarColor : yellow
             * longitude : 113.533549
             * latitude : 22.1915088
             * areaCode : FST
             * address : 風順堂區
             * parkingSpaces : 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26
             * isDelete : 0
             * createTime : 1535710273000
             * updateTime : 1535710273000
             * updateTime0 : 1535710273000
             * createTime0 : 1535710273000
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

        public static class CarBean {
            /**
             * id : 30
             * userId : 16
             * imgUrl : http://test-pic-666.oss-cn-hongkong.aliyuncs.com/car20180309163702.jpg
             * ifPerson : 2
             * carNo : m65-86
             * modelForCar : SUV
             * carBrand : q5
             * carStyle : q5
             * ifPay : 0
             * isDelete : 0
             * createTime : 1535710273000
             * updateTime : 1535710273000
             * expiryTime : 1535710273000
             */

            private int id;
            private int userId;
            private String imgUrl;
            private int ifPerson;
            private String carNo;
            private String modelForCar;
            private String carBrand;
            private String carStyle;
            private int ifPay;
            private int isDelete;
            private long createTime;
            private long updateTime;
            private long expiryTime;

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

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public int getIfPerson() {
                return ifPerson;
            }

            public void setIfPerson(int ifPerson) {
                this.ifPerson = ifPerson;
            }

            public String getCarNo() {
                return carNo;
            }

            public void setCarNo(String carNo) {
                this.carNo = carNo;
            }

            public String getModelForCar() {
                return modelForCar;
            }

            public void setModelForCar(String modelForCar) {
                this.modelForCar = modelForCar;
            }

            public String getCarBrand() {
                return carBrand;
            }

            public void setCarBrand(String carBrand) {
                this.carBrand = carBrand;
            }

            public String getCarStyle() {
                return carStyle;
            }

            public void setCarStyle(String carStyle) {
                this.carStyle = carStyle;
            }

            public int getIfPay() {
                return ifPay;
            }

            public void setIfPay(int ifPay) {
                this.ifPay = ifPay;
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

            public long getExpiryTime() {
                return expiryTime;
            }

            public void setExpiryTime(long expiryTime) {
                this.expiryTime = expiryTime;
            }
        }

        public static class ToushouRecordListBean {
            /**
             * id : 1
             * orderNo : U201710221222174430001
             * userId : 11
             * username : test11
             * amount : 4
             * status : 2
             * createTime : 1524583058000
             * finishTime : 1524583064000
             */

            private int id;
            private String orderNo;
            private int userId;
            private String username;
            private int amount;
            private int status;
            private long createTime;
            private long finishTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public long getFinishTime() {
                return finishTime;
            }

            public void setFinishTime(long finishTime) {
                this.finishTime = finishTime;
            }
        }
    }
}
