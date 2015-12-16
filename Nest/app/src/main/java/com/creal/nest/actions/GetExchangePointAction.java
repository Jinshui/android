package com.creal.nest.actions;

import android.content.Context;
import android.util.Log;

import com.creal.nest.model.ExchangePoint;
import com.creal.nest.model.Recharge;

import org.json.JSONException;
import org.json.JSONObject;


public class GetExchangePointAction extends PaginationAction<ExchangePoint> {
    private static final String tag = "TT-GetRechargeAction";
    //request keys
    private static final String NAME = "name";

    public GetExchangePointAction(Context context, int pageIndex, int pageSize){
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

    public GetExchangePointAction cloneCurrentPageAction(){
        GetExchangePointAction action = new GetExchangePointAction(
                mAppContext,
                getPageIndex(),
                getPageSize()
        );
        return action;
    }

    public ExchangePoint convertJsonToResult(JSONObject item) throws JSONException{
        return ExchangePoint.fromJSON(item);
    }
}