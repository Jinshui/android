package com.creal.nest.actions;

import android.content.Context;
import android.util.Log;

import com.creal.nest.model.Commodity;
import com.creal.nest.model.Ingot;

import org.json.JSONException;
import org.json.JSONObject;


public class GetIngotsAction extends PaginationAction<Ingot> {
    private static final String tag = "TT-GetIngotsAction";

    public GetIngotsAction(Context context, int pageIndex, int pageSize){
        super(context, pageIndex, pageSize);
        mServiceId = "GET_INGOTS";
        mURL = URL_GET_INGOTS;
    }

    @Override
    protected void addRequestParameters(JSONObject parameters, String timeStr) throws JSONException {
        super.addRequestParameters(parameters, timeStr);
    }

    public GetIngotsAction cloneCurrentPageAction(){
        GetIngotsAction action = new GetIngotsAction( mAppContext, getPageIndex(), getPageSize());
        return action;
    }

    public Ingot convertJsonToResult(JSONObject item) throws JSONException{
        return Ingot.fromJSON(item);
    }

    protected String getContentsKey(){
        return "rechargecard";
    }
}
