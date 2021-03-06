package com.creal.nest.actions;

import android.content.Context;

import com.creal.nest.Constants;
import com.creal.nest.model.ExchangePoint;

import org.json.JSONException;
import org.json.JSONObject;


public class GetExchangePointAction extends PaginationAction<ExchangePoint> {
    private static final String tag = "TT-GetExchangePointAction";
    //request keys
    private static final String NAME = "name";

    public GetExchangePointAction(Context context, int pageIndex, int pageSize){
        super(context, pageIndex, pageSize);
        mServiceId = Constants.SERVICE_ID_NEWS;
        mURL = "";
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
