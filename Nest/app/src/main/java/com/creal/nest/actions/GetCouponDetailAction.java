package com.creal.nest.actions;

import android.content.Context;

import com.creal.nest.Constants;
import com.creal.nest.model.Coupon;

import org.json.JSONException;
import org.json.JSONObject;


public class GetCouponDetailAction extends AbstractAction<Coupon> {
    private static final String tag = "XYK-GetCouponsAction";

    private String mCardId;
    private String mCouponId;

    //获取今日抢券优惠券详情
    public GetCouponDetailAction(Context context, String couponId){
        super(context);
        mCouponId = couponId;
        mServiceId = "GET_COUPON_DETAIL";
        mURL = Constants.URL_GET_COUPON_DETAIL;
    }

    //获取我的优惠券详情
    public GetCouponDetailAction(Context context, String cardId, String couponId){
        super(context);
        mCardId = cardId;
        mCouponId = couponId;
        mServiceId = "GET_MY_COUPON_DETAIL";
        mURL = Constants.URL_GET_MY_COUPON_DETAIL;
    }

    @Override
    protected JSONObject getRequestBody(String timeStr) throws JSONException{
        JSONObject parameters = new JSONObject();
        if(mCardId != null)
            parameters.put(Constants.KEY_CARD_ID, mCardId);
        if(mCouponId != null)
            parameters.put("coupons_id", mCouponId);
        return parameters;
    }

    protected Coupon createRespObject(JSONObject response) throws JSONException {
        return Coupon.fromJSON(response);
    }
}
