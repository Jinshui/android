package com.creal.nest.actions;

import android.content.Context;

import com.creal.nest.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class GetMyIngotsAction extends AbstractAction<String> {

    private String mCardId;

    public GetMyIngotsAction(Context context, String cardId) {
        super(context);
        this.mCardId = cardId;
        this.mServiceId = "GET_MY_INGOTS";
        mURL = Constants.URL_GET_MY_INGOTS;
    }

    protected JSONObject getRequestBody(String timeStr) throws JSONException{
        JSONObject parameters = new JSONObject();
        parameters.put(Constants.KEY_CARD_ID, mCardId);
        return parameters;
    }

    @Override
    protected String createRespObject(JSONObject response) throws JSONException {
        return response.getString("amount");
    }
}