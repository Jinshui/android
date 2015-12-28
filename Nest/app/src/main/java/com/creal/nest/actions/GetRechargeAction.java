package com.creal.nest.actions;

import android.content.Context;

import com.creal.nest.Constants;
import com.creal.nest.model.Recharge;

import org.json.JSONException;
import org.json.JSONObject;


public class GetRechargeAction extends PaginationAction<Recharge> {
    private static final String tag = "TT-GetRechargeAction";
    private String mCardId;

    public GetRechargeAction(Context context, int pageIndex, int pageSize, String cardId){
        super(context, pageIndex, pageSize);
        mServiceId = "GET_RECHARGE_HISTORY";
        mURL = Constants.URL_GET_RECHARGE_HISTORY;
        mCardId = cardId;
    }

    protected JSONObject getRequestBody(String timeStr) throws JSONException{
        JSONObject parameters = super.getRequestBody(timeStr);
        parameters.put(Constants.KEY_CARD_ID, mCardId);
        parameters.put("state", "");
        parameters.put("start_time", "");
        parameters.put("end_time", "");
        return parameters;
    }

    public GetRechargeAction cloneCurrentPageAction(){
        GetRechargeAction action = new GetRechargeAction(
                mAppContext,
                getPageIndex(),
                getPageSize(),
                mCardId
        );
        return action;
    }

    public Recharge convertJsonToResult(JSONObject item) throws JSONException{
        return Recharge.fromJSON(item);
    }
}
