package com.creal.nest.model;

import android.os.Parcel;

import com.creal.nest.R;
import com.creal.nest.actions.ActionRespObject;
import com.creal.nest.util.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class HouseInfo extends BaseModel implements ActionRespObject<HouseInfo>{

    public static final Creator<HouseInfo> CREATOR
            = new Creator<HouseInfo>() {
        public HouseInfo createFromParcel(Parcel in) {
            return new HouseInfo(in);
        }

        public HouseInfo[] newArray(int size) {
            return new HouseInfo[size];
        }
    };

    private String name;
    private String address;
    private String gender;
    private String communityName;
    private String bill;
    public HouseInfo(){
    }

    public HouseInfo(Parcel in){
        super(in);
        name = in.readString();
        address = in.readString();
        gender = in.readString();
        communityName = in.readString();
        bill = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(gender);
        dest.writeString(communityName);
        dest.writeString(bill);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public static HouseInfo fromJSON(JSONObject json) throws JSONException {
        if(json == null)
            throw new IllegalArgumentException("JSONObject is null");
        HouseInfo houseInfo = new HouseInfo();
        if(json.has("id")){
            houseInfo.setId(json.getString("coupons_id"));
        }
        if(json.has("name")){
            houseInfo.setName(json.getString("name"));
        }
        if(json.has("address")){
            houseInfo.setAddress(json.getString("address"));
        }
        if(json.has("gender")){
            houseInfo.setGender(json.getString("gender"));
        }
        if(json.has("arrear")){
            houseInfo.setBill(json.getString("arrear"));
        }
        if(json.has("residential_quarter")){
            houseInfo.setCommunityName(json.getString("residential_quarter"));
        }
        return houseInfo;
    }

    @Override
    public HouseInfo fillWithJSON(JSONObject jsonObject) throws JSONException {
        return fromJSON(jsonObject);
    }
}
