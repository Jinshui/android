package com.creal.nest.model;

import android.os.Parcel;

import com.creal.nest.actions.ActionRespObject;

import org.json.JSONException;
import org.json.JSONObject;

public class ShopCategory extends BaseModel implements ActionRespObject<ShopCategory>{

    public static final Creator<ShopCategory> CREATOR
            = new Creator<ShopCategory>() {
        public ShopCategory createFromParcel(Parcel in) {
            return new ShopCategory(in);
        }

        public ShopCategory[] newArray(int size) {
            return new ShopCategory[size];
        }
    };

    private String name;

    public ShopCategory(){
    }

    public ShopCategory(Parcel in){
        super(in);
        name = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ShopCategory fromJSON(JSONObject json) throws JSONException {
        if(json == null)
            throw new IllegalArgumentException("JSONObject is null");
        ShopCategory recharge = new ShopCategory();
        if(json.has("id"))
            recharge.setId(json.getString("id"));
        if(json.has("title"))
            recharge.name = json.getString("title");
        return recharge;
    }

    @Override
    public ShopCategory fillWithJSON(JSONObject jsonObject) throws JSONException {
        return fromJSON(jsonObject);
    }
}
