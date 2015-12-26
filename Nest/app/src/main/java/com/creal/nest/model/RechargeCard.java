package com.creal.nest.model;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

public class RechargeCard extends BaseModel {

    public static final Creator<RechargeCard> CREATOR = new Creator<RechargeCard>() {
        public RechargeCard createFromParcel(Parcel in) {
            return new RechargeCard(in);
        }

        public RechargeCard[] newArray(int size) {
            return new RechargeCard[size];
        }
    };

    private String name;
    private String desc;
    private String num;
    private int amount;

    public RechargeCard() {
        super();
    }

    public RechargeCard(Parcel in) {
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



    public static RechargeCard fromJSON(JSONObject json) throws JSONException {
        if (json == null)
            throw new IllegalArgumentException("JSONObject is null");
        RechargeCard rechargeCard = new RechargeCard();
        if(json.has("id")){
            rechargeCard.setId(json.getString("id"));
        }
        if(json.has("name")){
            rechargeCard.setName(json.getString("name"));
        }
        if(json.has("amount")){
            rechargeCard.setAmount(json.getInt("amount"));
        }
        if(json.has("description")){
            rechargeCard.setDesc(json.getString("description"));
        }
        if(json.has("num")){
            rechargeCard.setNum(json.getString("num"));
        }
        return rechargeCard;
    }
}
