package com.creal.nest.actions;

import android.content.Context;

import com.creal.nest.Constants;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class BindPhoneCardAction extends AbstractAction<String> {

    private String mCardNum;
    private String mMobile;
    private String mPassword;
    private String mVerificationCode;

    public BindPhoneCardAction(Context context,  String mobile, String cardNum,  String password, String verificationCode) {
        super(context);
        this.mMobile = mobile;
        this.mCardNum = cardNum;
        this.mPassword = password;
        this.mVerificationCode = verificationCode;
        this.mServiceId = "BIND_PHONE_CARD";
        mURL = Constants.URL_BIND_PHONE_CARD;
    }

    protected JSONObject getRequestBody(String timeStr) throws JSONException {
        JSONObject parameters = new JSONObject();
        parameters.put(Constants.KEY_CARD_NUM, mCardNum);
        parameters.put(Constants.KEY_MOBILE, mMobile);
        parameters.put(Constants.KEY_PASSWORD, Utils.md5(Utils.md5(mPassword) + mVerificationCode + timeStr));
        return parameters;
    }

    @Override
    protected String createRespObject(JSONObject response) throws JSONException {
        if(response.has(Constants.KEY_CARD_ID))
            PreferenceUtil.saveString(mAppContext, Constants.APP_USER_CARD_ID, response.getString(Constants.KEY_CARD_ID));
        if(response.has(Constants.KEY_CARD_NUM))
            PreferenceUtil.saveString(mAppContext, Constants.APP_USER_CARD_NUM, response.getString(Constants.KEY_CARD_NUM));
        if(response.has(Constants.KEY_MOBILE))
            PreferenceUtil.saveString(mAppContext, Constants.APP_USER_PHONE, response.getString(Constants.KEY_MOBILE));
        if(response.has(Constants.KEY_INTEGRAL))
            PreferenceUtil.saveInt(mAppContext, Constants.APP_USER_POINTS, response.getInt(Constants.KEY_INTEGRAL));
        if(response.has(Constants.KEY_MONEY))
            PreferenceUtil.saveInt(mAppContext, Constants.APP_USER_AMOUNT, response.getInt(Constants.KEY_MONEY));
        if(response.has(Constants.KEY_KEY))
            PreferenceUtil.saveString(mAppContext, Constants.APP_BINDING_KEY, response.getString(Constants.KEY_KEY));
        return response.getString(Constants.KEY_KEY);
    }
}