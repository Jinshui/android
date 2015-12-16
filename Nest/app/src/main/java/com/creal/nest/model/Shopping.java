package com.creal.nest.model;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

public class Shopping extends BaseModel {

    public static final Creator<Shopping> CREATOR
            = new Creator<Shopping>() {
        public Shopping createFromParcel(Parcel in) {
            return new Shopping(in);
        }

        public Shopping[] newArray(int size) {
            return new Shopping[size];
        }
    };

    private String card;
    private String status;
    private String dateTime;
    private String points;

    public Shopping(){
    }

    public Shopping(Parcel in){
        super(in);
        card = in.readString();
        status = in.readString();
        dateTime = in.readString();
        points = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(card);
        dest.writeString(status);
        dest.writeString(dateTime);
        dest.writeString(points);
    }


    public static Shopping fromJSON(JSONObject json) throws JSONException {
        if(json == null)
            throw new IllegalArgumentException("JSONObject is null");
        Shopping recharge = new Shopping();
        return recharge;
    }
}
