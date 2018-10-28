package com.bssf.john_li.coinhand.CHUtils;

/**
 * Created by John_Li on 17/4/2018.
 */

public class CHConfigtor {
    // 測試的IP地址
    public final static String BASE_URL = "http://112.74.52.98/parkingman-web/";
    // 用戶登錄的API
    public final static String USER_LOGIN = "user/qishoulogin";
    // 獲取工作區域
    public final static String WORK_AREA = "toushou/getAreaList";
    // 獲取工作區域
    public final static String CHECK_UNFINISH = "toushou/checkUnfinished";
    // 獲取該區域內的訂單
    public final static String GET_ORDER_LIST = "toushou/getOrderList";
    // 獲取訂單詳情
    public final static String GET_ORDER_DETAIL = "toushou/getToushouOrderDetail";
    // 投手單次接單
    public final static String TOUSHOU_RECIEVER_ORDER_ONCE = "toushou/acceptOrder";
    // 完成單次訂單投幣
    public final static String FINISH_INSERT_COINS_ORDER_ONCE = "toushou/finish";
}
