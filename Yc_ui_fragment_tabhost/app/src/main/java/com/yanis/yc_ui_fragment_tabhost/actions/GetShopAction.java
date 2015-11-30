package com.yanis.yc_ui_fragment_tabhost.actions;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;


import com.yanis.yc_ui_fragment_tabhost.model.Shop;


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
    }

    @Override
    public void addRequestParameters(JSONObject parameters) throws JSONException {
        super.addRequestParameters(parameters);
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
