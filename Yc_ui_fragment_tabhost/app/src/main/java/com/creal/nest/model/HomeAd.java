package com.creal.nest.model;

import android.annotation.SuppressLint;

@SuppressLint("ParcelCreator")
public class HomeAd extends BaseModel{
    private String photoUrl = "";

    public String getPhotoUrl(){
        return photoUrl;
    }
}
