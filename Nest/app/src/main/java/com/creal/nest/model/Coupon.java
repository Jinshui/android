package com.creal.nest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.creal.nest.actions.ActionRespObject;

import org.json.JSONException;
import org.json.JSONObject;

public class Coupon extends BaseModel implements ActionRespObject<Coupon>{

    public static final Parcelable.Creator<Coupon> CREATOR
            = new Parcelable.Creator<Coupon>() {
        public Coupon createFromParcel(Parcel in) {
            return new Coupon(in);
        }

        public Coupon[] newArray(int size) {
            return new Coupon[size];
        }
    };

    private String name;
    private int value;
    private String imageUrl;
    private String time;
    private int count;
    private int total;
    private int rest;
    private boolean active;
    private String desc;
    private String startTime;
    private String endTime;
    private int useCount;
    private String type;
    private int integral;

//    coupons_value:”XX”,

    public Coupon(){
    }

    public Coupon(Parcel in){
        super(in);
        name = in.readString();
        value = in.readInt();
        imageUrl = in.readString();
        time = in.readString();
        count = in.readInt();
        total = in.readInt();
        rest = in.readInt();
        active = in.readInt() == 1;

        desc = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        useCount = in.readInt();

        type = in.readString();
        integral = in.readInt();
//        boolean[] booleans = new boolean[2];
//        in.readBooleanArray(booleans);
//        isExpired = booleans[0];
//        started = booleans[1];
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
        dest.writeInt(value);
        dest.writeString(imageUrl);
        dest.writeString(time);

        dest.writeInt(count);
        dest.writeInt(total);
        dest.writeInt(rest);
        dest.writeInt(active ? 1 : 0);

        dest.writeString(desc);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeInt(useCount);

        dest.writeString(type);
        dest.writeInt(integral);
//        boolean[] booleans = new boolean[]{isExpired, started};
//        dest.writeBooleanArray(booleans);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }


    public static Coupon fromJSON(JSONObject json) throws JSONException {
        if(json == null)
            throw new IllegalArgumentException("JSONObject is null");
        Coupon coupon = new Coupon();
        if(json.has("coupons_id")){
            coupon.setId(json.getString("coupons_id"));
        }
        if(json.has("coupons_name")){
            coupon.setName(json.getString("coupons_name"));
        }
        if(json.has("coupons_value")){
            coupon.setValue(json.getInt("coupons_value"));
        }
        if(json.has("coupons_img")){
            coupon.setImageUrl(json.getString("coupons_img"));
        }
        if(json.has("coupons_time")){
            coupon.setTime(json.getString("coupons_time"));
        }
        if(json.has("coupons_count") && json.getString("coupons_count").length() > 0){
            coupon.setCount(json.getInt("coupons_count"));
        }
        if(json.has("coupons_total") && json.getString("coupons_total").length() > 0){
            coupon.setTotal(json.getInt("coupons_total"));
        }
        if(json.has("coupons_rest")){
            coupon.setRest(json.getInt("coupons_rest"));
        }
        if(json.has("coupons_flag")){
            coupon.setActive(json.getInt("coupons_flag") == 1);
        }
        if(json.has("coupons_des")){
            coupon.setDesc(json.getString("coupons_des"));
        }
        if(json.has("start_time")){
            coupon.setStartTime(json.getString("start_time"));
        }
        if(json.has("end_time")){
            coupon.setEndTime(json.getString("end_time"));
        }
        if(json.has("use_count")) {
            coupon.setUseCount(json.getInt("use_count"));
        }
        if(json.has("coupons_type")){
            coupon.setType(json.getString("coupons_type"));
        }
        if(json.has("integral")) {
            coupon.setIntegral(json.getInt("integral"));
        }
        return coupon;
    }

    @Override
    public Coupon fillWithJSON(JSONObject jsonObject) throws JSONException {
        return fromJSON(jsonObject);
    }
}
