package com.creal.nest.actions;

import android.content.Context;

import com.creal.nest.model.Ad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetAdAction extends AbstractAction<List<Ad>> {

    public GetAdAction(Context context) {
        super(context);
        this.mServiceId = "URL_GET_ADS";
        mURL = URL_GET_ADS;
    }

    protected JSONObject getRequestBody(String timeStr) throws JSONException {
        return new JSONObject();
    }

    @Override
    protected List<Ad> createRespObject(JSONObject response) throws JSONException {
        List<Ad> ads = new ArrayList<>();
        if (response.has("article")) {
            JSONArray array = response.getJSONArray("article");
            for (int i = 0; i < array.length(); i++) {
                ads.add(Ad.fromJSON((JSONObject) array.get(i)));
            }
        }
        return ads;
    }
}