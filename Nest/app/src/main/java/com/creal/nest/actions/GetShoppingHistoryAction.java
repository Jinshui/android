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
