package com.creal.nest.actions;

import android.content.Context;

import com.creal.nest.model.Coupon;

import org.json.JSONException;
import org.json.JSONObject;


public class GetCouponsAction extends PaginationAction<Coupon> {
    private static final String tag = "XYK-GetCouponsAction";
    public enum Type{
        MY_COUPONS,
        SNAP_COUPONS,
        LUCKY_COUPON
    }
    private String mCardId;
    private Type mType;
    public GetCouponsAction(Context context, int pageIndex, int pageSize, Type actionType){
        super(context, pageIndex, pageSize);
        mType = actionType;
        switch (mType){
            case MY_COUPONS:
                mURL = URL_GET_MY_COUPONS;
                break;
            case SNAP_COUPONS:
                mURL = URL_GET_COUPONS;
                break;
            case LUCKY_COUPON:
                mURL = URL_GET_LUCKY_COUPONS;
                break;
        }
        mServiceId = mURL;
    }

    public GetCouponsAction(Context context, int pageIndex, int pageSize, String cardId, Type actionType){
        this(context, pageIndex, pageSize, actionType);
        mCardId = cardId;
    }

    @Override
    protected JSONObject getRequestBody(String timeStr) throws JSONException{
        JSONObject parameters = super.getRequestBody(timeStr);
        if(mCardId != null)
            parameters.put(KEY_CARD_ID, mCardId);
        return parameters;
    }

    public GetCouponsAction cloneCurrentPageAction(){
        return new GetCouponsAction(mAppContext, getPageIndex(), getPageSize(), mCardId, mType );
    }

    public Coupon convertJsonToResult(JSONObject item) throws JSONException{
        return Coupon.fromJSON(item);
    }

    protected String getContentsKey(){
        return "coupons";
    }
}
