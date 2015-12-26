package com.creal.nest.actions;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class GetMyIngotsAction extends AbstractAction<String> {

    private String mCardId;

    public GetMyIngotsAction(Context context, String cardId) {
        super(context);
        this.mCardId = cardId;
        this.mServiceId = "GET_MY_INGOTS";
        mURL = URL_GET_MY_INGOTS;
    }

    protected void addRequestParameters(JSONObject parameters, String timeStr) throws JSONException {
        parameters.put(KEY_CARD_ID, mCardId);
    }

    @Override
    protected String createRespObject(JSONObject response) throws JSONException {
        return response.getString("amount");
    }
}