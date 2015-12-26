package com.creal.nest.actions;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class GetQRCodeAction extends AbstractAction<String> {

    private String mCardId;

    public GetQRCodeAction(Context context, String cardId) {
        super(context);
        this.mCardId = cardId;
        this.mServiceId = "GET_BAR_CODE";
        mURL = URL_GET_QR_CODE;
    }

    protected void addRequestParameters(JSONObject parameters, String cardId) throws JSONException {
        parameters.put(KEY_CARD_ID, mCardId);
    }

    @Override
    protected String createRespObject(JSONObject response) throws JSONException {
        return response.getString(KEY_QR_CODE);
    }
}