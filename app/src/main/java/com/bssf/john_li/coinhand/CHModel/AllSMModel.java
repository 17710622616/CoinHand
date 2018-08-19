package com.bssf.john_li.coinhand.CHModel;

import java.util.List;

/**
 * Created by John_Li on 9/8/2018.
 */

public class AllSMModel {

    /**
     * code : 200
     * msg :
     * data : {"totalCount":873,"data":[{"id":679,"machineNo":"3020","carType":2,"pillarColor":"green","longitude":113.5484896,"latitude":22.1979441,"areaCode":"HDMT","address":"澳門特別行政區","parkingSpaces":["1","2","3","4","5"],"distance":32.1833},{"id":371,"machineNo":"3048","carType":2,"pillarColor":"yellow","longitude":113.5474455,"latitude":22.1993776,"areaCode":"HDMT","address":"澳門特別行政區","parkingSpaces":["1","2","3","4","5","6"],"distance":165.1732},{"id":781,"machineNo":"4444","carType":2,"pillarColor":"yellow","longitude":113.5326629,"latitude":22.885835,"areaCode":"HDMT","address":"澳門特別行政區","parkingSpaces":["5","6","7","8"],"distance":76513.6528}]}
     */

    private int code;
    private String msg;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * totalCount : 873
         * data : [{"id":679,"machineNo":"3020","carType":2,"pillarColor":"green","longitude":113.5484896,"latitude":22.1979441,"areaCode":"HDMT","address":"澳門特別行政區","parkingSpaces":["1","2","3","4","5"],"distance":32.1833},{"id":371,"machineNo":"3048","carType":2,"pillarColor":"yellow","longitude":113.5474455,"latitude":22.1993776,"areaCode":"HDMT","address":"澳門特別行政區","parkingSpaces":["1","2","3","4","5","6"],"distance":165.1732},{"id":781,"machineNo":"4444","carType":2,"pillarColor":"yellow","longitude":113.5326629,"latitude":22.885835,"areaCode":"HDMT","address":"澳門特別行政區","parkingSpaces":["5","6","7","8"],"distance":76513.6528}]
         */

        private int totalCount;
        private List<DataBean> data;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 679
             * machineNo : 3020
             * carType : 2
             * pillarColor : green
             * longitude : 113.5484896
             * latitude : 22.1979441
             * areaCode : HDMT
             * address : 澳門特別行政區
             * parkingSpaces : ["1","2","3","4","5"]
             * distance : 32.1833
             */

            private int id;
            private String machineNo;
            private int carType;
            private String pillarColor;
            private double longitude;
            private double latitude;
            private String areaCode;
            private String address;
            private double distance;
            private List<String> parkingSpaces;

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

            public double getDistance() {
                return distance;
            }

            public void setDistance(double distance) {
                this.distance = distance;
            }

            public List<String> getParkingSpaces() {
                return parkingSpaces;
            }

            public void setParkingSpaces(List<String> parkingSpaces) {
                this.parkingSpaces = parkingSpaces;
            }
        }
    }
}
