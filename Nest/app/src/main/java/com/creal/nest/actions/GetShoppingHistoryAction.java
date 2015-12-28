package com.creal.nest.actions;

import android.content.Context;

import com.creal.nest.model.Shopping;

import org.json.JSONException;
import org.json.JSONObject;


public class GetShoppingHistoryAction extends PaginationAction<Shopping> {
    private static final String tag = "TT-GetRechargeAction";
    private String mCardId;
    public GetShoppingHistoryAction(Context context, int pageIndex, int pageSize, String cardId){
        super(context, pageIndex, pageSize);
        mServiceId = "GET_SHOP_HISTORY";
        mURL = URL_GET_SHOP_HISTORY;
        mCardId = cardId;
    }

    public GetShoppingHistoryAction cloneCurrentPageAction(){
        GetShoppingHistoryAction action = new GetShoppingHistoryAction(
                mAppContext,
                getPageIndex(),
                getPageSize(),
                mCardId
        );
        return action;
    }

    protected JSONObject getRequestBody(String timeStr) throws JSONException{
        JSONObject parameters = super.getRequestBody(timeStr);
        parameters.put(KEY_CARD_ID, mCardId);
        parameters.put("state", "");
        parameters.put("start_time", "");
        parameters.put("end_time", "");
        return parameters;
    }

    public Shopping convertJsonToResult(JSONObject item) throws JSONException{
        return Shopping.fromJSON(item);
    }
}
