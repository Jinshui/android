package com.creal.nest.model;

import android.os.Parcel;

import com.creal.nest.actions.ActionRespObject;

import org.json.JSONException;
import org.json.JSONObject;

public class Ad extends BaseModel implements ActionRespObject<Ad>{

    public static final Creator<Ad> CREATOR = new Creator<Ad>() {
        public Ad createFromParcel(Parcel in) {
            return new Ad(in);
        }

        public Ad[] newArray(int size) {
            return new Ad[size];
        }
    };

    private String imageUrl;
    private String url;

    public Ad() {
        super();
    }

    public Ad(Parcel in) {
        super(in);
        imageUrl = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(imageUrl);
        dest.writeString(url);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public static Ad fromJSON(JSONObject json) throws JSONException {
        if (json == null)
            throw new IllegalArgumentException("JSONObject is null");
        Ad ad = new Ad();
        if(json.has("id")){
            ad.setId(json.getString("id"));
        }
        if(json.has("articleimg")){
            ad.setImageUrl(json.getString("articleimg"));
        }
        if(json.has("url")){
            ad.setUrl(json.getString("url"));
        }
        return ad;
    }

    @Override
    public Ad fillWithJSON(JSONObject jsonObject) throws JSONException {
        return fromJSON(jsonObject);
    }
}
