package com.creal.nest.actions;

import android.content.Context;

import com.creal.nest.Constants;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class PayArrearAction extends JSONObjectAction {
    public PayArrearAction(Context context, String url, Map parameters) {
        super(context, url, parameters);
    }

    protected JSONObject getRequestBody(String timeStr) throws JSONException{
        JSONObject parameters = super.getRequestBody(timeStr);
        if(parameters.has("password")) {
            String password = parameters.getString("password");
            String key = PreferenceUtil.getString(mAppContext, Constants.APP_BINDING_KEY, Constants.APP_DEFAULT_KEY);
            parameters.put("password", Utils.md5(Utils.md5(password) + key + timeStr));
        }
        return parameters;
    }
}