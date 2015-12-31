package com.creal.nest.actions;

import android.content.Context;

import com.creal.nest.Constants;
import com.creal.nest.util.PreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class RegisterAction extends JSONObjectAction {

    public RegisterAction(Context context, String url, Map parameters) {
        super(context, url, parameters);
    }

    @Override
    protected JSONObject createRespObject(JSONObject response) throws JSONException {
        if(response.has(Constants.KEY_CARD_ID))
            PreferenceUtil.saveString(mAppContext, Constants.APP_USER_CARD_ID, response.getString(Constants.KEY_CARD_ID));
        if(response.has(Constants.KEY_CARD_NUM))
            PreferenceUtil.saveString(mAppContext, Constants.APP_USER_CARD_NUM, response.getString(Constants.KEY_CARD_NUM));
        if(response.has(Constants.KEY_KEY))
            PreferenceUtil.saveString(mAppContext, Constants.APP_BINDING_KEY, response.getString(Constants.KEY_KEY));
        PreferenceUtil.saveString(mAppContext, Constants.APP_USER_PHONE, getReqParams().get(Constants.KEY_MOBILE));
        return response;
    }

    protected String getEncryptKey(){
        return Constants.APP_DEFAULT_KEY;
    }
}