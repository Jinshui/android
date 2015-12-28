package com.creal.nest.actions;

public interface JSONConstants {
    public static final String SERVER_HOST = "manager.go.wuxian114.com";
    public static final String URL_GET_VERIFICATION_CODE      = "http://" + SERVER_HOST + "/lmk_interface/vcode/index.php";
    public static final String URL_LOGIN                      = "http://" + SERVER_HOST + "/lmk_interface/login/index.php";
    public static final String URL_BIND_PHONE_CARD            = "http://" + SERVER_HOST + "/lmk_interface/bding/index.php";
    public static final String URL_GET_QR_CODE                = "http://" + SERVER_HOST + "/lmk_interface/qr/index.php";
    public static final String URL_CHANGE_PWD                 = "http://" + SERVER_HOST + "/lmk_interface/cpwd/index.php";
    public static final String URL_REGISTER                   = "http://" + SERVER_HOST + "/lmk_interface/register/index.php";
    public static final String URL_GET_ADS                    = "http://" + SERVER_HOST + "/lmk_interface/ads/index.php";
    public static final String URL_GET_LATEST_ACTIVITIES      = "http://" + SERVER_HOST + "/lmk_interface/activity/index.php";
    public static final String URL_GET_COUPONS                = "http://" + SERVER_HOST + "/lmk_interface/coupons/index.php";
    public static final String URL_GET_LUCKY_COUPONS          = "http://" + SERVER_HOST + "/lmk_interface/try/index.php";
    public static final String URL_GET_MY_COUPONS             = "http://" + SERVER_HOST + "/lmk_interface/mycoupons/index.php";
    public static final String URL_RECEIVE_COUPONS            = "http://" + SERVER_HOST + "/lmk_interface/receivecoupons/index.php";
    public static final String URL_GET_COUPON_DETAIL          = "http://" + SERVER_HOST + "/lmk_interface/coupondetail/index.php";
    public static final String URL_GET_MY_COUPON_DETAIL       = "http://" + SERVER_HOST + "/lmk_interface/mycouponsdetail/index.php";
    public static final String URL_GET_MY_INGOTS              = "http://" + SERVER_HOST + "/lmk_interface/yuanbao/index.php";
    public static final String URL_GET_RECHARGE_CARD          = "http://" + SERVER_HOST + "/lmk_interface/rechargeablecard/index.php";
    public static final String URL_EXCHANGE_INGOTS            = "http://" + SERVER_HOST + "/lmk_interface/exchange/index.php";
    public static final String URL_BIND_PROPERTY              = "http://" + SERVER_HOST + "/lmk_interface/bdhousing/index.php";
    public static final String URL_REPORT_REPAIR              = "http://" + SERVER_HOST + "/lmk_interface/repair/index.php";
    public static final String URL_REPORT_REPAIR_LIST         = "http://" + SERVER_HOST + "/lmk_interface/repairlist/index.php";
    public static final String URL_REPORT_REPAIR_DETAIL       = "http://" + SERVER_HOST + "/lmk_interface/repairdetail/index.php";
    public static final String URL_GET_SHOP_CATEGORY          = "http://" + SERVER_HOST + "/lmk_interface/comclass/index.php";
    public static final String URL_GET_SHOPS                  = "http://" + SERVER_HOST + "/lmk_interface/comlist/index.php";
    public static final String URL_GET_SHOP_DETAIL            = "http://" + SERVER_HOST + "/lmk_interface/comdetail/index.php";
    public static final String URL_GET_SHOP_HISTORY            = "http://" + SERVER_HOST + "/lmk_interface/transactionlist/index.php";
    public static final String URL_GET_RECHARGE_HISTORY            = "http://" + SERVER_HOST + "/lmk_interface/rechargelist/index.php";

    public final static String KEY_CARD_ID = "card_id";
    public final static String KEY_CARD_NUM = "card_num";
    public final static String KEY_MOBILE = "mobile";
    public final static String KEY_VERIFICATION_CODE = "vcode";
    public final static String KEY_INTEGRAL = "integral";
    public final static String KEY_MONEY = "money";
    public final static String KEY_KEY = "key";
    public final static String KEY_PASSWORD = "password";
    public final static String KEY_QR_CODE = "qr_code";

    public final static String SERVICE_ID_CATEGORY = "S1112";
    public final static String SERVICE_ID_NEWS = "S1116";
}
