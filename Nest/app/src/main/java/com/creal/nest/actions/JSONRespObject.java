package com.creal.nest.actions;

import com.creal.nest.util.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jinshui on 16-1-9.
 */
public class JSONRespObject implements ActionRespObject<JSONRespObject> {

    private JSONObject jsonObject;
    public JSONRespObject(){}

    public JSONRespObject(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    public String getString(String key){
        return JSONUtil.getString(jsonObject, key, null);
    }

    @Override
    public JSONRespObject fillWithJSON(JSONObject jsonObject) throws JSONException {
        return new JSONRespObject(jsonObject);
    }
}
