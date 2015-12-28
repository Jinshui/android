package com.creal.nest.model;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

public class Shopping extends BaseModel implements PaginationItem<Shopping>{

    public enum State {
        UNKNOWN("未知"),
        SHOPPING("消费"),
        RETURN("退货"),
        SUBSCRIPTION("订金"),
        RETURN_SUBSCRIPTION("退订金"),
        CANCELLED("作废"),;

        String name;
        State(String name){
            this.name = name;
        }

        public String toString(){
            return name;
        }

        static State from(int value){
            switch (value){
                case 1:
                    return SHOPPING;
                case 2:
                    return RETURN;
                case 3:
                    return SUBSCRIPTION;
                case 4:
                    return RETURN_SUBSCRIPTION;
                case 5:
                    return CANCELLED;
            }
            return UNKNOWN;
        }
    }

    public static final Creator<Shopping> CREATOR = new Creator<Shopping>() {
        public Shopping createFromParcel(Parcel in) {
            return new Shopping(in);
        }

        public Shopping[] newArray(int size) {
            return new Shopping[size];
        }
    };

    private String sellerName;
    private String orderId;
    private String orderTime;
    private int amount;
    private State state = State.UNKNOWN;

    public Shopping() {
    }

    public Shopping(Parcel in) {
        super(in);
        sellerName = in.readString();
        orderId = in.readString();
        orderTime = in.readString();
        amount = in.readInt();
        state = State.valueOf(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sellerName);
        dest.writeString(orderId);
        dest.writeString(orderTime);
        dest.writeInt(amount);
        dest.writeString(state.name());
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public static Shopping fromJSON(JSONObject json) throws JSONException {
        if (json == null)
            throw new IllegalArgumentException("JSONObject is null");
        Shopping shopping = new Shopping();
        if (json.has("seller_name"))
            shopping.setSellerName(json.getString("seller_name"));
        if (json.has("order_id"))
            shopping.setOrderId(json.getString("order_id"));
        if (json.has("order_time"))
            shopping.setOrderTime(json.getString("order_time"));
        if (json.has("money") && json.getString("money").matches("[0-9]+"))
            shopping.setAmount(json.getInt("money"));
        if (json.has("state") && json.getString("state").matches("[1-4]"))
            shopping.setState(State.from(json.getInt("state")));
        return shopping;
    }


    public Shopping fillWithJSON(JSONObject json) throws JSONException {
        if (json == null)
            throw new IllegalArgumentException("JSONObject is null");
        return fromJSON(json);
    }
}
