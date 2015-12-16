package com.creal.nest.model;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

public class ExchangeIngot extends BaseModel {

    public static final Creator<ExchangeIngot> CREATOR
            = new Creator<ExchangeIngot>() {
        public ExchangeIngot createFromParcel(Parcel in) {
            return new ExchangeIngot(in);
        }

        public ExchangeIngot[] newArray(int size) {
            return new ExchangeIngot[size];
        }
    };

    private String amount;
    private String desc;
    private String dateTime;

    public ExchangeIngot(){
    }

    public ExchangeIngot(Parcel in){
        super(in);
        amount = in.readString();
        desc = in.readString();
        dateTime = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(amount);
        dest.writeString(desc);
        dest.writeString(dateTime);
    }


    public static ExchangeIngot fromJSON(JSONObject json) throws JSONException {
        if(json == null)
            throw new IllegalArgumentException("JSONObject is null");
        ExchangeIngot recharge = new ExchangeIngot();
        return recharge;
    }
}
