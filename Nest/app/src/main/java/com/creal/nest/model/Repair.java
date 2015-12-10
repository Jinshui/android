package com.creal.nest.model;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

public class Repair extends BaseModel {

    public static final Creator<Repair> CREATOR
            = new Creator<Repair>() {
        public Repair createFromParcel(Parcel in) {
            return new Repair(in);
        }

        public Repair[] newArray(int size) {
            return new Repair[size];
        }
    };

    private String name;

    private boolean done;

    public Repair(){
    }

    public Repair(boolean done){
        this.done = done;
    }

    public Repair(Parcel in){
        super(in);
        name = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }


    public static Repair fromJSON(JSONObject json) throws JSONException {
        if(json == null)
            throw new IllegalArgumentException("JSONObject is null");
        Repair activity = new Repair();
        return activity;
    }
}
