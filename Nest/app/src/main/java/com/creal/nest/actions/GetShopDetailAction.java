package com.creal.nest.actions;

import android.content.Context;

import com.creal.nest.model.Shop;

import org.json.JSONException;
import org.json.JSONObject;


public class GetShopDetailAction extends AbstractAction<Shop> {
    private static final String tag = "TT-GetRepairDetailAction";
    private String mShopId;

    public GetShopDetailAction(Context context, String shopId){
        super(context);
        mShopId = shopId;
        mServiceId = "GET_SHOP_DETAIL";
        mURL = URL_GET_SHOP_DETAIL;
    }

    protected JSONObject getRequestBody(String timeStr) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", mShopId);
        return jsonObject;
    }

    @Override
    protected Shop createRespObject(JSONObject response) throws JSONException {
        return Shop.fromJSON(response);
    }
}
