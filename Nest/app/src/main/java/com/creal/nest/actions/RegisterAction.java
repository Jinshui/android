package com.creal.nest.actions;

import android.content.Context;

import com.creal.nest.Constants;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterAction extends AbstractAction<String> {

    private String mMobile;
    private String mVerificationCode;
    private String mPassword;
    private String mName;
    private String mIDCard;
    private String mAddress;
    private boolean mSendViaPost;

    public RegisterAction(Context context, String mobile, String verificationCode, String password, String name, String idCard, String address, boolean sendViaPost) {
        super(context);
        this.mMobile = mobile;
        this.mVerificationCode = verificationCode;
        this.mPassword = password;
        this.mName = name;
        this.mIDCard = idCard;
        this.mAddress = address;
        this.mSendViaPost = sendViaPost;
        this.mServiceId = "URL_REGISTER";
        mURL = Constants.URL_REGISTER;
    }

    protected JSONObject getRequestBody(String timeStr) throws JSONException{
        JSONObject parameters = new JSONObject();
        parameters.put(Constants.KEY_MOBILE, mMobile);
        parameters.put(Constants.KEY_VERIFICATION_CODE, mVerificationCode);
        parameters.put(Constants.KEY_PASSWORD, Utils.md5(mPassword));
        if(mName != null)
            parameters.put("name", mName);
        if(mIDCard != null)
            parameters.put("id_card", mIDCard);
        if(mAddress != null)
            parameters.put("address", mAddress);
        parameters.put("is_mail", mSendViaPost);
        return parameters;
    }

    @Override
    protected String createRespObject(JSONObject response) throws JSONException {
        if(response.has(Constants.KEY_CARD_ID))
            PreferenceUtil.saveString(mAppContext, Constants.APP_USER_CARD_ID, response.getString(Constants.KEY_CARD_ID));
        if(response.has(Constants.KEY_CARD_NUM))
            PreferenceUtil.saveString(mAppContext, Constants.APP_USER_CARD_NUM, response.getString(Constants.KEY_CARD_NUM));
        if(response.has(Constants.KEY_KEY))
            PreferenceUtil.saveString(mAppContext, Constants.APP_BINDING_KEY, response.getString(Constants.KEY_KEY));
        return response.getString(Constants.KEY_KEY);
    }
}