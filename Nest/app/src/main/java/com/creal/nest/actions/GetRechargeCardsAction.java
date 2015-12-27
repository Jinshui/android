package com.creal.nest.actions;

import android.content.Context;

import com.creal.nest.model.RechargeCard;

import org.json.JSONException;
import org.json.JSONObject;


public class GetRechargeCardsAction extends PaginationAction<RechargeCard> {
    private static final String tag = "TT-GetRechargeCardsAction";

    public GetRechargeCardsAction(Context context, int pageIndex, int pageSize){
        super(context, pageIndex, pageSize);
        mServiceId = "GET_INGOTS";
        mURL = URL_GET_RECHARGE_CARD;
    }

    public GetRechargeCardsAction cloneCurrentPageAction(){
        GetRechargeCardsAction action = new GetRechargeCardsAction( mAppContext, getPageIndex(), getPageSize());
        return action;
    }

    public RechargeCard convertJsonToResult(JSONObject item) throws JSONException{
        return RechargeCard.fromJSON(item);
    }

    protected String getContentsKey(){
        return "rechargecard";
    }
}
