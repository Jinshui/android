package com.yanis.yc_ui_fragment_tabhost.model;

import android.annotation.SuppressLint;

import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint("ParcelCreator")
public class Shop extends BaseModel{

    public static Shop fromJSON(JSONObject json) throws JSONException {
        if(json == null)
            throw new IllegalArgumentException("JSONObject is null");
        Shop shop = new Shop();
        return shop;
    }
}
