package com.creal.nest.actions;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;


import com.creal.nest.model.Shop;


public class GetShopListAction extends PaginationAction<Shop> {
    private static final String tag = "TT-GetNewsAction";
    //request keys
    private static final String NAME = "name";
    //local variables
    private String mCategoryId;
    private double mLatitude;
    private double mLongitude;

    public GetShopListAction(Context context, String categoryId, double latitude, double longitude, int pageIndex, int pageSize){
        super(context, pageIndex, pageSize);
        mCategoryId = categoryId;
        mLatitude = latitude;
        mLongitude = longitude;
        mServiceId = "GET_SHOPS";
        mURL = URL_GET_SHOPS;
    }

    @Override
    protected JSONObject getRequestBody(String timeStr) throws JSONException{
        JSONObject parameters = super.getRequestBody(timeStr);
        parameters.put("class_id", mCategoryId);
//        parameters.put("latitude", mLatitude);
//        parameters.put("longitude", mLongitude);
        return parameters;
    }

    public GetShopListAction cloneCurrentPageAction(){
        GetShopListAction action = new GetShopListAction(
                mAppContext,
                mCategoryId, mLatitude, mLongitude,
                getPageIndex(),
                getPageSize()
        );
        return action;
    }

    public Shop convertJsonToResult(JSONObject item) throws JSONException{
        return Shop.fromJSON(item);
    }


    protected String getContentsKey(){
        return "cominfo";
    }
}
