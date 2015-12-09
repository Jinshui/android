package com.creal.nest.actions;

import android.content.Context;
import android.util.Log;

import com.creal.nest.model.Commodity;
import com.creal.nest.model.Ingot;

import org.json.JSONException;
import org.json.JSONObject;


public class GetIngotsAction extends PaginationAction<Ingot> {
    private static final String tag = "TT-GetLatestActivities";
    //request keys
    private static final String NAME = "name";

    public GetIngotsAction(Context context, int pageIndex, int pageSize){
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

    public GetIngotsAction cloneCurrentPageAction(){
        GetIngotsAction action = new GetIngotsAction(
                mAppContext,
                getPageIndex(),
                getPageSize()
        );
        return action;
    }

    public Ingot convertJsonToResult(JSONObject item) throws JSONException{
        return Ingot.fromJSON(item);
    }
}
