package com.creal.nest.actions;

import android.content.Context;
import android.content.Intent;

import com.creal.nest.model.ReceiveCouponResult;

import org.json.JSONException;
import org.json.JSONObject;

public class ReceiveCouponAction extends AbstractAction<ReceiveCouponResult> {

    private String mCardId;
    private String mCouponId;

    public ReceiveCouponAction(Context context, String cardId, String couponId) {
        super(context);
        this.mCardId = cardId;
        this.mCouponId = couponId;
        this.mServiceId = "RECEIVE_COUPONS";
        mURL = URL_RECEIVE_COUPONS;
    }

    protected void addRequestParameters(JSONObject parameters, String cardId) throws JSONException {
        parameters.put(KEY_CARD_ID, mCardId);
        parameters.put("coupons_id", mCouponId);
    }

    @Override
    protected ReceiveCouponResult createRespObject(JSONObject response) throws JSONException {
        ReceiveCouponResult result = new ReceiveCouponResult();
        if(response.has("value") && response.getString("value").length() > 0)
            result.setValue(response.getInt("value"));
        if(response.has("receive_id"))
            result.setReceiveId(response.getInt("receive_id"));
        return result;
    }
}