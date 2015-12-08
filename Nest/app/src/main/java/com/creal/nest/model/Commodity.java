package com.creal.nest.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Commodity extends BaseModel {

    public static final Parcelable.Creator<Commodity> CREATOR = new Parcelable.Creator<Commodity>() {
        public Commodity createFromParcel(Parcel in) {
            return new Commodity(in);
        }

        public Commodity[] newArray(int size) {
            return new Commodity[size];
        }
    };

    public Commodity() {
        super();
    }

    public Commodity(Parcel in) {
        super(in);
    }


    public static Commodity fromJSON(JSONObject json) throws JSONException {
        if (json == null)
            throw new IllegalArgumentException("JSONObject is null");
        Commodity commodity = new Commodity();
        return commodity;
    }
}
