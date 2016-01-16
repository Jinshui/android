package com.creal.nest.model;

import android.os.Parcel;

import com.creal.nest.actions.ActionRespObject;

import org.json.JSONException;
import org.json.JSONObject;

public class Recharge extends BaseModel implements ActionRespObject<Recharge> {

    public static final Creator<Recharge> CREATOR
            = new Creator<Recharge>() {
        public Recharge createFromParcel(Parcel in) {
            return new Recharge(in);
        }

        public Recharge[] newArray(int size) {
            return new Recharge[size];
        }
    };

    public enum State {
        SUCC,
        FAILED;

        static State from(int value){
            switch (value){
                case 1:
                    return SUCC;
                case 2:
                    return FAILED;
            }
            return SUCC;
        }
    }
    private String sellerName;
    private String orderId;
    private String orderTime;
    private int amount;
    private State state;
    private int payAmount;//实付金额
    private String paymentId;

    public Recharge(){
    }

    public Recharge(Parcel in){
        super(in);
        sellerName = in.readString();
        orderId = in.readString();
        orderTime = in.readString();
        amount = in.readInt();
        state = State.valueOf(in.readString());
        payAmount = in.readInt();
        paymentId = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sellerName);
        dest.writeString(orderId);
        dest.writeString(orderTime);
        dest.writeInt(amount);
        dest.writeString(state.name());
        dest.writeInt(payAmount);
        dest.writeString(paymentId);
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

    public int getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(int payAmount) {
        this.payAmount = payAmount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public static Recharge fromJSON(JSONObject json) throws JSONException {
        if(json == null)
            throw new IllegalArgumentException("JSONObject is null");

//                "arrear_name": "2013年度物业费用",
//                "payment_time": "0000-00-00 00:00:00",
//                "alias": "",
//                "area_name": "国中华宅"
        Recharge recharge = new Recharge();
        if (json.has("id"))
            recharge.setId(json.getString("id"));
        if (json.has("seller_name"))
            recharge.setSellerName(json.getString("seller_name"));
        if (json.has("order_id"))
            recharge.setOrderId(json.getString("order_id"));
        if (json.has("order_time"))
            recharge.setOrderTime(json.getString("order_time"));
        if (json.has("money") && json.getString("money").length() > 0)
            recharge.setAmount(json.getInt("money"));
        if (json.has("state") && json.getString("state").length() > 0)
            recharge.setState(State.from(json.getInt("state")));
        if (json.has("pay_money") && json.getString("pay_money").length() > 0)
            recharge.setPayAmount(json.getInt("pay_money"));
        if (json.has("payment_id"))
            recharge.setPaymentId(json.getString("payment_id"));
        return recharge;
    }

    @Override
    public Recharge fillWithJSON(JSONObject jsonObject) throws JSONException {
        return fromJSON(jsonObject);
    }
}
