package com.yanis.yc_ui_fragment_tabhost.actions;

import android.content.Context;
import android.util.Log;

import com.yanis.yc_ui_fragment_tabhost.model.Coupon;
import com.yanis.yc_ui_fragment_tabhost.model.Shop;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;


public class GetCouponsAction extends PaginationAction<Coupon> {
    private static final String tag = "TT-GetNewsAction";
    //request keys
    private static final String NAME = "name";

    public GetCouponsAction(Context context, int pageIndex, int pageSize){
        super(context, pageIndex, pageSize);
        mServiceId = SERVICE_ID_NEWS;
    }

    @Override
    public void addRequestParameters(JSONObject parameters) throws JSONException {
        super.addRequestParameters(parameters);
        try{
//            parameters.put(NAME, URLEncoder.encode(mCategory, "UTF-8"));
        }catch(Exception e){
            Log.d(tag, "failed to add parameters", e);
        }
    }

    public GetCouponsAction cloneCurrentPageAction(){
        GetCouponsAction action = new GetCouponsAction(
                mAppContext,
                getPageIndex(),
                getPageSize()
        );
        return action;
    }

    public Coupon convertJsonToResult(JSONObject item) throws JSONException{
        return Coupon.fromJSON(item);
    }
}
