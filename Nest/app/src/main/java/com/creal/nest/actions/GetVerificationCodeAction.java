package com.creal.nest.actions;

import android.content.Context;

import com.creal.nest.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class GetVerificationCodeAction extends AbstractAction<String> {

    private String mMobile;

    public GetVerificationCodeAction(Context context, String mobile) {
        super(context);
        this.mMobile = mobile;
        this.mServiceId = "GET_VERIFICATION_CODE";
        mURL = Constants.URL_GET_VERIFICATION_CODE;
    }

    protected JSONObject getRequestBody(String timeStr) throws JSONException{
        JSONObject parameters = new JSONObject();
        parameters.put("mobile", mMobile);
        return parameters;
    }

    @Override
    protected String createRespObject(JSONObject response) throws JSONException {
        return response.getString("code");
    }
}