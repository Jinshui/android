package com.creal.nest.model;

import android.os.Parcel;

import com.creal.nest.actions.ActionRespObject;

import org.json.JSONException;
import org.json.JSONObject;

public class Payment extends BaseModel implements ActionRespObject<Payment>{

    public static final Creator<Payment> CREATOR
            = new Creator<Payment>() {
        public Payment createFromParcel(Parcel in) {
            return new Payment(in);
        }

        public Payment[] newArray(int size) {
            return new Payment[size];
        }
    };

    public enum State {
        PAYED,
        NOT_PAYED;

        static State from(int value){
            switch (value){
                case 1:
                    return PAYED;
                case 2:
                    return NOT_PAYED;
            }
            return NOT_PAYED;
        }
    }

    private State state;
    private int amount;
    private String desc;
    private String time;
    private String alias;
    private String communityName;

    public Payment(){
    }

    public Payment(Parcel in){
        super(in);
        state = State.valueOf(in.readString());
        amount = in.readInt();
        desc = in.readString();
        time = in.readString();
        alias = in.readString();
        communityName = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(state.name());
        dest.writeInt(amount);
        dest.writeString(desc);
        dest.writeString(time);
        dest.writeString(alias);
        dest.writeString(communityName);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public static Payment fromJSON(JSONObject json) throws JSONException {
        if(json == null)
            throw new IllegalArgumentException("JSONObject is null");
        Payment recharge = new Payment();
        if (json.has("id"))
            recharge.setId(json.getString("id"));
        if (json.has("state"))
            recharge.setState(State.from(json.getInt("state")));
        if (json.has("money"))
            recharge.setAmount(json.getInt("money"));
        if (json.has("arrear_name"))
            recharge.setDesc(json.getString("arrear_name"));
        if (json.has("payment_time"))
            recharge.setTime(json.getString("payment_time"));
        if (json.has("alias"))
            recharge.setAlias(json.getString("alias"));
        if (json.has("area_name"))
            recharge.setCommunityName(json.getString("area_name"));
        return recharge;
    }

    @Override
    public Payment fillWithJSON(JSONObject jsonObject) throws JSONException {
        return fromJSON(jsonObject);
    }
}
