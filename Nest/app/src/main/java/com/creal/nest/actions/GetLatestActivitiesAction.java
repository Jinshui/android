package com.creal.nest.actions;

import android.content.Context;

import com.creal.nest.model.SalesActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class GetLatestActivitiesAction extends PaginationAction<SalesActivity> {
    private static final String tag = "TT-GetLatestActivities";
    //request keys
    private static final String NAME = "name";

    public GetLatestActivitiesAction(Context context, int pageIndex, int pageSize){
        super(context, pageIndex, pageSize);
        mServiceId = "GET_LATEST_ACTIVITIES";
        mURL = URL_GET_LATEST_ACTIVITIES;
    }

    public GetLatestActivitiesAction cloneCurrentPageAction(){
        GetLatestActivitiesAction action = new GetLatestActivitiesAction(
                mAppContext,
                getPageIndex(),
                getPageSize()
        );
        return action;
    }

    public SalesActivity convertJsonToResult(JSONObject item) throws JSONException{
        return SalesActivity.fromJSON(item);
    }

    protected String getContentsKey(){
        return "article";
    }
}
