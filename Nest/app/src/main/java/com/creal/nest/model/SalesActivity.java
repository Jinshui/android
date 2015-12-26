package com.creal.nest.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class SalesActivity extends Ad {

    public static final Parcelable.Creator<SalesActivity> CREATOR
            = new Parcelable.Creator<SalesActivity>() {
        public SalesActivity createFromParcel(Parcel in) {
            return new SalesActivity(in);
        }

        public SalesActivity[] newArray(int size) {
            return new SalesActivity[size];
        }
    };

    public SalesActivity(){
    }

    public SalesActivity(Parcel in){
        super(in);
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }


    public static SalesActivity fromJSON(JSONObject json) throws JSONException {
        if(json == null)
            throw new IllegalArgumentException("JSONObject is null");
        SalesActivity salesActivity = new SalesActivity();
        Ad ad = Ad.fromJSON(json);
        salesActivity.setId(ad.getId());
        salesActivity.setImageUrl(ad.getImageUrl());
        salesActivity.setUrl(ad.getUrl());
        return salesActivity;
    }
}
