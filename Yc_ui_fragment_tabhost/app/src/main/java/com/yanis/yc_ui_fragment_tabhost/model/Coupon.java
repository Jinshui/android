package com.yanis.yc_ui_fragment_tabhost.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jinshui on 15-12-6.
 */
public class Coupon extends BaseModel {

    private boolean isExpired = false;

    private boolean started = true;

    public Coupon(){
    }

    public Coupon(boolean isExpired, boolean started){
        this.isExpired = isExpired;
        this.started = started;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setIsExpired(boolean isExpired) {
        this.isExpired = isExpired;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public static Coupon fromJSON(JSONObject json) throws JSONException {
        if(json == null)
            throw new IllegalArgumentException("JSONObject is null");
        Coupon coupon = new Coupon();
        return coupon;
    }
}
