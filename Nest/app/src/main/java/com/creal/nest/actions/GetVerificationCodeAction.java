package com.creal.nest.actions;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class GetVerificationCodeAction extends AbstractAction {

    private String mCardNum;
    private String mMobile;

    public GetVerificationCodeAction(Context context, String cardNum, String mobile) {
        super(context);
        this.mCardNum = cardNum;
        this.mMobile = mobile;
        this.mServiceId = "GET_VERIFICATION_CODE";
        mURL = "http://manager.go.wuxian114.com/lmk_interface/login.php";
    }

    protected void addRequestParameters(JSONObject parameters, String timeStr) throws JSONException {
        parameters.put("card_num", mCardNum);
        parameters.put("mobile", mMobile);
    }

    @Override
    protected Object createRespObject(JSONObject response) throws JSONException {
        return null;
    }
}