package com.creal.nest;

public class Constants {
    public static final String SERVER_HOST = "manager.go.wuxian114.com";
    public static final String URL_GET_VERIFICATION_CODE      = "http://" + SERVER_HOST + "/lmk_interface/vcode/index.php";
    public static final String URL_LOGIN                      = "http://" + SERVER_HOST + "/lmk_interface/login/index.php";
    public static final String URL_BIND_PHONE_CARD            = "http://" + SERVER_HOST + "/lmk_interface/bding/index.php";
    public static final String URL_GET_QR_CODE                = "http://" + SERVER_HOST + "/lmk_interface/qr/index.php";
    public static final String URL_GET_CHARGE_INFO            = "http://" + SERVER_HOST + "/lmk_interface/sellertemporderinfo/index.php";
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
    public static final String URL_GET_SHOP_HISTORY           = "http://" + SERVER_HOST + "/lmk_interface/transactionlist/index.php";
    public static final String URL_GET_RECHARGE_HISTORY       = "http://" + SERVER_HOST + "/lmk_interface/rechargelist/index.php";
    public static final String URL_GET_CARD_INFO              = "http://" + SERVER_HOST + "/lmk_interface/cardinfo/index.php";
    public static final String URL_GET_EXCHANGE_LIST          = "http://" + SERVER_HOST + "/lmk_interface/exchangelist/index.php";
    public static final String URL_SEARCH                     = "http://" + SERVER_HOST + "/lmk_interface/search/index.php";
    public static final String URL_PAY                        = "http://" + SERVER_HOST + "/lmk_interface/payment/index.php";
    public static final String URL_GET_BILL                   = "http://" + SERVER_HOST + "/lmk_interface/paymentarrear/index.php";
    public static final String URL_GET_PAY_HISTORY            = "http://" + SERVER_HOST + "/lmk_interface/paymentlist/index.php";
    public static final String URL_GET_HELP_LIST              = "http://" + SERVER_HOST + "/lmk_interface/helplist/index.php";
    public static final String URL_GET_USER_INFO              = "http://" + SERVER_HOST + "/lmk_interface/personalinfo/index.php";


    public final static String KEY_CARD_ID = "card_id";
    public final static String KEY_CARD_NUM = "card_num";
    public final static String KEY_MOBILE = "mobile";
    public final static String KEY_VERIFICATION_CODE = "vcode";
    public final static String KEY_INTEGRAL = "integral";
    public final static String KEY_MONEY = "money";
    public final static String KEY_KEY = "key";
    public final static String KEY_PASSWORD = "password";
    public final static String KEY_QR_CODE = "qr_code";

    public static final String APP_DEFAULT_KEY        = "123456789";
    public static final String APP_BINDING_KEY        = "app_user_binding_key";
    public static final String APP_USER_AUTHORIZED    = "app_user_authorized";
    public static final String APP_USER_PHONE         = "app_user_phone";
    public static final String APP_USER_PWD           = "app_user_pwd";
    public static final String APP_USER_CARD_ID       = "app_user_card_id";
    public static final String APP_USER_CARD_NUM      = "app_user_card_num";
    public static final String APP_USER_POINTS        = "app_user_points";
    public static final String APP_USER_AMOUNT        = "app_user_amount";
    public static final String APP_USER_GESTURE_PWD   = "app_user_gesture_pwd";
    public static final String APP_SEARCH_KEYWORDS    = "app_user_search_keywords";

    public static final String APP_WX_APPID = "wxd930ea5d5a258f4f";
    public static final String APP_KEYWORDS_SPLITTER  = "\\|\\|";



    public static final String CACHE_DIR = "/nest";
    //Pagination
    public static final int PAGE_SIZE = 10;
    //Cached Keywords
    public static final int MAX_CACHED_KEYWORD_SIZE = 10;

    public final static String SERVICE_ID_NEWS = "S1116";
}
