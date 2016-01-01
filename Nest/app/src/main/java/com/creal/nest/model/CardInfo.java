package com.creal.nest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.creal.nest.actions.ActionRespObject;

import org.json.JSONException;
import org.json.JSONObject;

public class CardInfo  extends BaseModel implements ActionRespObject<CardInfo>{

    public static final Parcelable.Creator<CardInfo> CREATOR = new Parcelable.Creator<CardInfo>() {
        public CardInfo createFromParcel(Parcel in) {
            return new CardInfo(in);
        }

        public CardInfo[] newArray(int size) {
            return new CardInfo[size];
        }
    };

    private int points;
    private int money;
    private String cardNum;

    public CardInfo() {
        super();
    }

    public CardInfo(Parcel in) {
        super(in);
        points = in.readInt();
        money = in.readInt();
        cardNum = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(points);
        dest.writeInt(money);
        dest.writeString(cardNum);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    @Override
    public CardInfo fillWithJSON(JSONObject jsonObject) throws JSONException {
        if(jsonObject.has("integrate") && jsonObject.getString("integrate").matches("[0-9]+")){
            setPoints(jsonObject.getInt("integrate"));
        }
        if(jsonObject.has("money") && jsonObject.getString("money").matches("[0-9]+")){
            setMoney(jsonObject.getInt("money"));
        }
        if(jsonObject.has("card_num")) {
            setCardNum(jsonObject.getString("card_num"));
        }
        return this;
    }
}
