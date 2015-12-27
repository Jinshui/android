package com.creal.nest.actions;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class JSONObjectAction extends AbstractAction<JSONObject> {
    private Map mReqParams;
    public JSONObjectAction(Context context, String url, Map parameters) {
        super(context);
        this.mReqParams = parameters;
        this.mServiceId = url;
        mURL = url;
    }

    protected JSONObject getRequestBody(String timeStr) throws JSONException{
        return new JSONObject(mReqParams);
    }

    @Override
    protected JSONObject createRespObject(JSONObject response) throws JSONException {
        return response;
    }
}