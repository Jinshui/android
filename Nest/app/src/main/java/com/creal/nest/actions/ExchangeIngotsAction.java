package com.creal.nest.actions;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class ExchangeIngotsAction extends AbstractAction<Boolean> {

    private String mCardId;
    private String mRechargeCardId;
    private String mNum;

    public ExchangeIngotsAction(Context context, String cardId, String rechargeCardId, String num) {
        super(context);
        this.mCardId = cardId;
        mRechargeCardId = rechargeCardId;
        mNum = num;
        this.mServiceId = "EXCHANGE_INGOTS";
        mURL = URL_EXCHANGE_INGOTS;
    }

    protected  JSONObject getRequestBody(String timeStr) throws JSONException {
        JSONObject parameters = new JSONObject();
        parameters.put(KEY_CARD_ID, mCardId);
        parameters.put("recharge_card_id", mRechargeCardId);
        parameters.put("exchange_num", mNum);
        return parameters;
    }

    @Override
    protected Boolean createRespObject(JSONObject response) throws JSONException {
        return true;
    }
}