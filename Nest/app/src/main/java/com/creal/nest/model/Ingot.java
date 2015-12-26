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

    private String name;
    private String desc;
    private String num;
    private int amount;

    public Ingot() {
        super();
    }

    public Ingot(Parcel in) {
        super(in);
        name = in.readString();
        desc = in.readString();
        num = in.readString();
        amount = in.readInt();
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeString(num);
        dest.writeInt(amount);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }



    public static Ingot fromJSON(JSONObject json) throws JSONException {
        if (json == null)
            throw new IllegalArgumentException("JSONObject is null");
        Ingot ingot = new Ingot();
        if(json.has("id")){
            ingot.setId(json.getString("id"));
        }
        if(json.has("name")){
            ingot.setName(json.getString("name"));
        }
        if(json.has("amount")){
            ingot.setAmount(json.getInt("amount"));
        }
        if(json.has("description")){
            ingot.setDesc(json.getString("description"));
        }
        if(json.has("num")){
            ingot.setNum(json.getString("num"));
        }
        return ingot;
    }
}
