package com.creal.nest.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static class Step implements Parcelable {

        public static final Creator<Step> CREATOR = new Creator<Step>() {
            public Step createFromParcel(Parcel in) {
                return new Step(in);
            }
            public Step[] newArray(int size) {
                return new Step[size];
            }
        };

        public String desc;
        public String date;

        public Step(Parcel in){
            desc = in.readString();
            date = in.readString();
        }

        public Step(String desc, String date){
            this.date = date;
            this.desc = desc;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(desc);
            dest.writeString(date);
        }
    }

    private String name;

    private boolean done;

    private List<Step> stepList;


    public Repair(){
    }

    public Repair(boolean done){
        this.done = done;
    }

    public Repair(Parcel in){
        super(in);
        name = in.readString();
        in.readTypedList( getStepList(), Step.CREATOR);
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
        dest.writeTypedList(getStepList());
    }

    public List<Step> getStepList() {
        if(stepList == null)
            stepList = new ArrayList<>();
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
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
