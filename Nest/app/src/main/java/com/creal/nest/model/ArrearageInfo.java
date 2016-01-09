package com.creal.nest.model;

import android.os.Parcel;

import com.creal.nest.actions.ActionRespObject;

import org.json.JSONException;
import org.json.JSONObject;

public class ArrearageInfo extends BaseModel implements ActionRespObject<ArrearageInfo>{

    public static final Creator<ArrearageInfo> CREATOR
            = new Creator<ArrearageInfo>() {
        public ArrearageInfo createFromParcel(Parcel in) {
            return new ArrearageInfo(in);
        }

        public ArrearageInfo[] newArray(int size) {
            return new ArrearageInfo[size];
        }
    };
    private String name;
    private int money;
    public ArrearageInfo(){
    }

    public ArrearageInfo(Parcel in){
        super(in);
        name = in.readString();
        money = in.readInt();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
        dest.writeInt(money);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public static ArrearageInfo fromJSON(JSONObject json) throws JSONException {
        if(json == null)
            throw new IllegalArgumentException("JSONObject is null");
        ArrearageInfo arrInfo = new ArrearageInfo();
        if(json.has("id")){
            arrInfo.setId(json.getString("id"));
        }
        if(json.has("name")){
            arrInfo.setName(json.getString("name"));
        }
        if(json.has("money")){
            arrInfo.setMoney(json.getInt("money"));
        }
        return arrInfo;
    }

    @Override
    public ArrearageInfo fillWithJSON(JSONObject jsonObject) throws JSONException {
        return fromJSON(jsonObject);
    }
}
