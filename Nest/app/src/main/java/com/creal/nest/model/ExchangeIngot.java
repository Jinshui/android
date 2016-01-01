package com.creal.nest.model;

import android.os.Parcel;

import com.creal.nest.actions.ActionRespObject;

import org.json.JSONException;
import org.json.JSONObject;

public class ExchangeIngot extends BaseModel implements ActionRespObject<ExchangeIngot>{

    public static final Creator<ExchangeIngot> CREATOR = new Creator<ExchangeIngot>() {
        public ExchangeIngot createFromParcel(Parcel in) {
            return new ExchangeIngot(in);
        }

        public ExchangeIngot[] newArray(int size) {
            return new ExchangeIngot[size];
        }
    };

    private String amount;
    private String money;

    private String dateTime;

    public ExchangeIngot(){
    }

    public ExchangeIngot(Parcel in){
        super(in);
        amount = in.readString();
        money = in.readString();
        dateTime = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(amount);
        dest.writeString(money);
        dest.writeString(dateTime);
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }


    public static ExchangeIngot fromJSON(JSONObject json) throws JSONException {
        if(json == null)
            throw new IllegalArgumentException("JSONObject is null");
        ExchangeIngot recharge = new ExchangeIngot();
        if(json.has("money")){
            recharge.setMoney(json.getString("money"));
        }
        if(json.has("amount")){
            recharge.setAmount(json.getString("amount"));
        }
        if(json.has("time")){
            recharge.setDateTime(json.getString("exchange_time"));
        }
        return recharge;
    }

    @Override
    public ExchangeIngot fillWithJSON(JSONObject jsonObject) throws JSONException {
        return fromJSON(jsonObject);
    }
}
