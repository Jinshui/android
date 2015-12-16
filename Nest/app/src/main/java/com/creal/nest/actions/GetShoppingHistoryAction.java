package com.creal.nest.actions;

import android.content.Context;
import android.util.Log;

import com.creal.nest.model.Shopping;

import org.json.JSONException;
import org.json.JSONObject;


public class GetShoppingHistoryAction extends PaginationAction<Shopping> {
    private static final String tag = "TT-GetRechargeAction";
    //request keys
    private static final String NAME = "name";

    public GetShoppingHistoryAction(Context context, int pageIndex, int pageSize){
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

    public GetShoppingHistoryAction cloneCurrentPageAction(){
        GetShoppingHistoryAction action = new GetShoppingHistoryAction(
                mAppContext,
                getPageIndex(),
                getPageSize()
        );
        return action;
    }

    public Shopping convertJsonToResult(JSONObject item) throws JSONException{
        return Shopping.fromJSON(item);
    }
}
