package com.creal.nest.actions;

import android.content.Context;
import android.util.Log;

import com.creal.nest.model.Commodity;

import org.json.JSONException;
import org.json.JSONObject;


public class GetCommoditiesAction extends PaginationAction<Commodity> {
    private static final String tag = "TT-GetLatestActivities";
    //request keys
    private static final String NAME = "name";

    public GetCommoditiesAction(Context context, int pageIndex, int pageSize){
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

    public GetCommoditiesAction cloneCurrentPageAction(){
        GetCommoditiesAction action = new GetCommoditiesAction(
                mAppContext,
                getPageIndex(),
                getPageSize()
        );
        return action;
    }

    public Commodity convertJsonToResult(JSONObject item) throws JSONException{
        return Commodity.fromJSON(item);
    }
}
