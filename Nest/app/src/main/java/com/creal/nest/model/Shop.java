package com.creal.nest.model;

import android.annotation.SuppressLint;

import com.creal.nest.actions.ActionRespObject;

import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint("ParcelCreator")
public class Shop extends BaseModel implements ActionRespObject<Shop> {

    private String logo;
    private String title;
    private String address;
    private String description;
    private String keyword;
    private String latitude;
    private String longitude;
    private String detail;
    private String category;
    private String phone;
    private String imageUrl;
    private boolean recommend;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static Shop fromJSON(JSONObject json) throws JSONException {
        if(json == null)
            throw new IllegalArgumentException("JSONObject is null");
        Shop shop = new Shop();
        if(json.has("id")){
            shop.setId(json.getString("id"));
        }
        if(json.has("detail")){
            shop.setDetail(json.getString("detail"));
        }
        if(json.has("logo")){
            shop.setLogo(json.getString("logo"));
        }
        if(json.has("title")){
            shop.setTitle(json.getString("title"));
        }
        if(json.has("img")){
            shop.setImageUrl(json.getString("img"));
        }
        if(json.has("tel")){
            shop.setPhone(json.getString("tel"));
        }
        if(json.has("address")){
            shop.setAddress(json.getString("address"));
        }
        if(json.has("class")){
            shop.setCategory(json.getString("class"));
        }
        if(json.has("description")){
            shop.setDescription(json.getString("description"));
        }
        if(json.has("keyword")){
            shop.setKeyword(json.getString("keyword"));
        }
        if(json.has("longitude")){
            shop.setLongitude(json.getString("longitude"));
        }
        if(json.has("latitude")){
            shop.setLatitude(json.getString("latitude"));
        }
        if(json.has("recommend")){
            shop.setRecommend("1".equals(json.getString("recommend")));
        }
        return shop;
    }

    @Override
    public Shop fillWithJSON(JSONObject jsonObject) throws JSONException {
        return fromJSON(jsonObject);
    }
}
