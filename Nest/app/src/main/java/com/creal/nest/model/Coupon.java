package com.creal.nest.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jinshui on 15-12-6.
 */
public class Coupon extends BaseModel {

    public static final Parcelable.Creator<Coupon> CREATOR
            = new Parcelable.Creator<Coupon>() {
        public Coupon createFromParcel(Parcel in) {
            return new Coupon(in);
        }

        public Coupon[] newArray(int size) {
            return new Coupon[size];
        }
    };

    private boolean isExpired = false;
    private boolean started = true;

    public Coupon(){
    }

    public Coupon(Parcel in){
        super(in);
        boolean[] booleans = new boolean[2];
        in.readBooleanArray(booleans);
        isExpired = booleans[0];
        started = booleans[1];
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        boolean[] booleans = new boolean[]{isExpired, started};
        dest.writeBooleanArray(booleans);
    }

    public Coupon(boolean isExpired){
        this.isExpired = isExpired;
    }
    public Coupon(boolean isExpired, boolean started){
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

    public static Coupon fromJSON(JSONObject json) throws JSONException {
        if(json == null)
            throw new IllegalArgumentException("JSONObject is null");
        Coupon coupon = new Coupon();
        return coupon;
    }
}
