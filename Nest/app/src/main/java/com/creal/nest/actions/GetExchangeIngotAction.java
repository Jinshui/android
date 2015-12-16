package com.creal.nest.actions;

import android.content.Context;
import android.util.Log;

import com.creal.nest.model.ExchangeIngot;
import com.creal.nest.model.ExchangePoint;

import org.json.JSONException;
import org.json.JSONObject;


public class GetExchangeIngotAction extends PaginationAction<ExchangeIngot> {
    private static final String tag = "TT-GetRechargeAction";
    //request keys
    private static final String NAME = "name";

    public GetExchangeIngotAction(Context context, int pageIndex, int pageSize){
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

    public GetExchangeIngotAction cloneCurrentPageAction(){
        GetExchangeIngotAction action = new GetExchangeIngotAction(
                mAppContext,
                getPageIndex(),
                getPageSize()
        );
        return action;
    }

    public ExchangeIngot convertJsonToResult(JSONObject item) throws JSONException{
        return ExchangeIngot.fromJSON(item);
    }
}
