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
        mURL = URL_BIND_PHONE_CARD;
    }

    protected JSONObject getRequestBody(String timeStr) throws JSONException {
        JSONObject parameters = new JSONObject();
        parameters.put(KEY_CARD_NUM, mCardNum);
        parameters.put(KEY_MOBILE, mMobile);
        parameters.put(KEY_PASSWORD, Utils.md5(Utils.md5(mPassword) + mVerificationCode + timeStr));
        return parameters;
    }

    @Override
    protected String createRespObject(JSONObject response) throws JSONException {
        if(response.has(KEY_CARD_ID))
            PreferenceUtil.saveString(mAppContext, Constants.APP_USER_CARD_ID, response.getString(KEY_CARD_ID));
        if(response.has(KEY_CARD_NUM))
            PreferenceUtil.saveString(mAppContext, Constants.APP_USER_CARD_NUM, response.getString(KEY_CARD_NUM));
        if(response.has(KEY_MOBILE))
            PreferenceUtil.saveString(mAppContext, Constants.APP_USER_PHONE, response.getString(KEY_MOBILE));
        if(response.has(KEY_INTEGRAL))
            PreferenceUtil.saveInt(mAppContext, Constants.APP_USER_POINTS, response.getInt(KEY_INTEGRAL));
        if(response.has(KEY_MONEY))
            PreferenceUtil.saveInt(mAppContext, Constants.APP_USER_AMOUNT, response.getInt(KEY_MONEY));
        if(response.has(KEY_KEY))
            PreferenceUtil.saveString(mAppContext, Constants.APP_BINDING_KEY, response.getString(KEY_KEY));
        return response.getString(KEY_KEY);
    }
}