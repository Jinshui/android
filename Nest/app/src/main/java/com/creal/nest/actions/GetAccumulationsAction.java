package com.creal.nest.actions;

import android.content.Context;
import android.util.Log;

import com.creal.nest.model.AccumulationItem;
import com.creal.nest.model.Repair;

import org.json.JSONException;
import org.json.JSONObject;


public class GetAccumulationsAction extends PaginationAction<AccumulationItem> {
    private static final String tag = "TT-GetLatestActivities";
    //request keys
    private static final String NAME = "name";

    public GetAccumulationsAction(Context context, int pageIndex, int pageSize){
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

    public GetAccumulationsAction cloneCurrentPageAction(){
        GetAccumulationsAction action = new GetAccumulationsAction(
                mAppContext,
                getPageIndex(),
                getPageSize()
        );
        return action;
    }

    public AccumulationItem convertJsonToResult(JSONObject item) throws JSONException{
        return AccumulationItem.fromJSON(item);
    }
}
