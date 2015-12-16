package com.creal.nest.model;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

public class Recharge extends BaseModel {

    public static final Creator<Recharge> CREATOR
            = new Creator<Recharge>() {
        public Recharge createFromParcel(Parcel in) {
            return new Recharge(in);
        }

        public Recharge[] newArray(int size) {
            return new Recharge[size];
        }
    };

    private int amount;
    private String dateTime;

    public Recharge(){
    }

    public Recharge(Parcel in){
        super(in);
        amount = in.readInt();
        dateTime = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(amount);
        dest.writeString(dateTime);
    }


    public static Recharge fromJSON(JSONObject json) throws JSONException {
        if(json == null)
            throw new IllegalArgumentException("JSONObject is null");
        Recharge recharge = new Recharge();
        return recharge;
    }
}
