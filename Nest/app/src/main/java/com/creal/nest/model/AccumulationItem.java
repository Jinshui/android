package com.creal.nest.model;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jinshui on 15-12-6.
 */
public class AccumulationItem extends BaseModel {

    public static final Creator<AccumulationItem> CREATOR
            = new Creator<AccumulationItem>() {
        public AccumulationItem createFromParcel(Parcel in) {
            return new AccumulationItem(in);
        }

        public AccumulationItem[] newArray(int size) {
            return new AccumulationItem[size];
        }
    };

    private String title;
    private String time;
    private String desc;
    private int amount;

    public AccumulationItem(){
    }

    public AccumulationItem(Parcel in){
        super(in);
        title = in.readString();
        time = in.readString();
        desc = in.readString();
        amount = in.readInt();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(time);
        dest.writeString(desc);
        dest.writeInt(amount);
    }


    public static AccumulationItem fromJSON(JSONObject json) throws JSONException {
        if(json == null)
            throw new IllegalArgumentException("JSONObject is null");
        AccumulationItem coupon = new AccumulationItem();
        return coupon;
    }
}
