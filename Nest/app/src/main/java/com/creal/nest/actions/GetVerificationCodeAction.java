package com.creal.nest.actions;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class GetVerificationCodeAction extends AbstractAction<String> {

    private String mMobile;

    public GetVerificationCodeAction(Context context, String mobile) {
        super(context);
        this.mMobile = mobile;
        this.mServiceId = "GET_VERIFICATION_CODE";
        mURL = URL_GET_VERIFICATION_CODE;
    }

    protected void addRequestParameters(JSONObject parameters, String timeStr) throws JSONException {
        parameters.put("mobile", mMobile);
    }

    @Override
    protected String createRespObject(JSONObject response) throws JSONException {
        return response.getString("code");
    }
}