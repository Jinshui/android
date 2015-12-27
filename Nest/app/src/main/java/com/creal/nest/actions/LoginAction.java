package com.creal.nest.actions;

import android.content.Context;
import android.util.Log;

import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class LoginAction extends AbstractAction {
    private final static String tag = "N-LoginAction";
    private String mCardNum;
    private String mPassword;

    public LoginAction(Context context, String cardNum, String pwd) {
        super(context);
        this.mCardNum = cardNum;
        this.mPassword = pwd;
        this.mServiceId = "LOGIN";
        mURL = URL_LOGIN;
    }

    protected JSONObject getRequestBody(String timeStr) throws JSONException{
        JSONObject parameters = new JSONObject();
        parameters.put("card_num", mCardNum);
        String key = PreferenceUtil.getString(mAppContext, KEY_KEY, null);
        parameters.put("password", Utils.md5(Utils.md5(mPassword) + key + timeStr));
        return parameters;
    }

    @Override
    protected Object createRespObject(JSONObject response) throws JSONException {
        PreferenceUtil.saveString(mAppContext, "card_id", response.getString("card_id"));
        PreferenceUtil.saveString(mAppContext, "card_num", response.getString("card_num"));
        return null;
    }
}
