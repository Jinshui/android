package com.creal.nest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.creal.nest.actions.ActionRespObject;

import org.json.JSONException;
import org.json.JSONObject;

public class SalesActivity extends BaseModel implements ActionRespObject<SalesActivity>{

    public static final Parcelable.Creator<SalesActivity> CREATOR
            = new Parcelable.Creator<SalesActivity>() {
        public SalesActivity createFromParcel(Parcel in) {
            return new SalesActivity(in);
        }

        public SalesActivity[] newArray(int size) {
            return new SalesActivity[size];
        }
    };

    private String imageUrl;
    private String url;
    private String title;
    private String content;

    public SalesActivity(){
    }

    public SalesActivity(Parcel in){
        super(in);
        title = in.readString();
        imageUrl = in.readString();
        url = in.readString();
        content = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeString(url);
        dest.writeString(content);
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static SalesActivity fromJSON(JSONObject json) throws JSONException {
        if(json == null)
            throw new IllegalArgumentException("JSONObject is null");
        SalesActivity salesActivity = new SalesActivity();
        if(json.has("id")){
            salesActivity.setId(json.getString("id"));
        }
        if(json.has("articleimg")){
            salesActivity.setImageUrl(json.getString("articleimg"));
        }
        if(json.has("url")){
            salesActivity.setUrl(json.getString("url"));
        }
        if(json.has("title")){
            salesActivity.setTitle(json.getString("title"));
        }
        if(json.has("content")){
            salesActivity.setContent(json.getString("content"));
        }
        return salesActivity;
    }

    @Override
    public SalesActivity fillWithJSON(JSONObject jsonObject) throws JSONException {
        return fromJSON(jsonObject);
    }
}
