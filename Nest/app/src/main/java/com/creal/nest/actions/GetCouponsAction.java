package com.creal.nest.actions;

import android.content.Context;
import android.util.Log;

import com.creal.nest.model.Coupon;
import com.creal.nest.model.Shop;

import org.json.JSONException;
import org.json.JSONObject;


public class GetCouponsAction extends PaginationAction<Coupon> {
    private static final String tag = "TT-GetNewsAction";
    //request keys
    private static final String NAME = "name";

    public GetCouponsAction(Context context, int pageIndex, int pageSize){
        super(context, pageIndex, pageSize);
        mServiceId = SERVICE_ID_NEWS;
        mURL = "";
    }


    @Override
    protected void addRequestParameters(JSONObject parameters, String timeStr) throws JSONException {
        super.addRequestParameters(parameters, timeStr);
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
