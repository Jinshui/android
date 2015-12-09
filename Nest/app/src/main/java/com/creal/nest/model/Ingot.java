package com.creal.nest.model;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

public class Ingot extends BaseModel {

    public static final Creator<Ingot> CREATOR = new Creator<Ingot>() {
        public Ingot createFromParcel(Parcel in) {
            return new Ingot(in);
        }

        public Ingot[] newArray(int size) {
            return new Ingot[size];
        }
    };

    public Ingot() {
        super();
    }

    public Ingot(Parcel in) {
        super(in);
    }


    public static Ingot fromJSON(JSONObject json) throws JSONException {
        if (json == null)
            throw new IllegalArgumentException("JSONObject is null");
        Ingot commodity = new Ingot();
        return commodity;
    }
}
