package com.creal.nest.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Activity extends BaseModel {

    private boolean isExpired = false;

    public Activity(){
    }


    public static Activity fromJSON(JSONObject json) throws JSONException {
        if(json == null)
            throw new IllegalArgumentException("JSONObject is null");
        Activity activity = new Activity();
        return activity;
    }
}
