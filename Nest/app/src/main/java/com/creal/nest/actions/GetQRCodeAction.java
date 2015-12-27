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

    protected JSONObject getRequestBody(String timeStr) throws JSONException{
        JSONObject parameters = new JSONObject();
        parameters.put(KEY_CARD_ID, mCardId);
        return parameters;
    }

    @Override
    protected String createRespObject(JSONObject response) throws JSONException {
        return response.getString(KEY_QR_CODE);
    }
}