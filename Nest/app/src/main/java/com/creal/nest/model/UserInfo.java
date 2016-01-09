package com.creal.nest.model;

import android.os.Parcel;

import com.creal.nest.actions.ActionRespObject;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInfo extends BaseModel implements ActionRespObject<UserInfo>{

    public static final Creator<UserInfo> CREATOR
            = new Creator<UserInfo>() {
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
    private String cardId;
    private String name;
    private String gender;
    private String cardNum;
    private String phone;
    public UserInfo(){
    }

    public UserInfo(Parcel in){
        super(in);
        cardId = in.readString();
        name = in.readString();
        gender = in.readString();
        cardNum = in.readString();
        phone = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(cardId);
        dest.writeString(name);
        dest.writeString(gender);
        dest.writeString(cardNum);
        dest.writeString(phone);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public static UserInfo fromJSON(JSONObject json) throws JSONException {
        if(json == null)
            throw new IllegalArgumentException("JSONObject is null");
        UserInfo userInfo = new UserInfo();
        if(json.has("card_id")){
            userInfo.setCardId(json.getString("card_id"));
        }
        if(json.has("name")){
            userInfo.setName(json.getString("name"));
        }
        if(json.has("gender")){
            userInfo.setGender(json.getString("gender"));
        }
        if(json.has("card_num")){
            userInfo.setCardNum(json.getString("card_num"));
        }
        if(json.has("mobile")){
            userInfo.setPhone(json.getString("mobile"));
        }
        return userInfo;
    }

    @Override
    public UserInfo fillWithJSON(JSONObject jsonObject) throws JSONException {
        return fromJSON(jsonObject);
    }
}
