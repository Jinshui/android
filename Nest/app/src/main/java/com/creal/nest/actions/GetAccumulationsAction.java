package com.creal.nest.actions;

import android.content.Context;

import com.creal.nest.model.AccumulationItem;

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
    protected JSONObject getRequestBody(String timeStr) throws JSONException{
        return super.getRequestBody(timeStr);
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
