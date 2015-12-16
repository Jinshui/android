package com.creal.nest.model;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

public class ExchangePoint extends BaseModel {

    public static final Creator<ExchangePoint> CREATOR
            = new Creator<ExchangePoint>() {
        public ExchangePoint createFromParcel(Parcel in) {
            return new ExchangePoint(in);
        }

        public ExchangePoint[] newArray(int size) {
            return new ExchangePoint[size];
        }
    };

    private String name;
    private String desc;
    private String dateTime;

    public ExchangePoint(){
    }

    public ExchangePoint(Parcel in){
        super(in);
        name = in.readString();
        desc = in.readString();
        dateTime = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeString(dateTime);
    }


    public static ExchangePoint fromJSON(JSONObject json) throws JSONException {
        if(json == null)
            throw new IllegalArgumentException("JSONObject is null");
        ExchangePoint recharge = new ExchangePoint();
        return recharge;
    }
}
