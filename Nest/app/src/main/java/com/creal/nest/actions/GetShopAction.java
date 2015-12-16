package com.creal.nest.actions;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;


import com.creal.nest.model.Shop;


public class GetShopAction extends PaginationAction<Shop> {
    private static final String tag = "TT-GetNewsAction";
    //request keys
    private static final String NAME = "name";
    //local variables
    private String mCategory;

    public GetShopAction(Context context, String category, int pageIndex, int pageSize){
        super(context, pageIndex, pageSize);
        mCategory = category;
        mServiceId = SERVICE_ID_NEWS;
        mURL = "";
    }

    @Override
    protected void addRequestParameters(JSONObject parameters, String timeStr) throws JSONException {
        super.addRequestParameters(parameters, timeStr);
        try{
            parameters.put(NAME, URLEncoder.encode(mCategory, "UTF-8"));
        }catch(Exception e){
            Log.d(tag, "failed to add parameters", e);
        }
    }

    public GetShopAction cloneCurrentPageAction(){
        GetShopAction action = new GetShopAction(
                mAppContext,
                mCategory,
                getPageIndex(),
                getPageSize()
        );
        return action;
    }

    public Shop convertJsonToResult(JSONObject item) throws JSONException{
        return Shop.fromJSON(item);
    }
}
