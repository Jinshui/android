package com.creal.nest.actions;

import android.content.Context;

import com.creal.nest.Constants;
import com.creal.nest.model.Repair;

import org.json.JSONException;
import org.json.JSONObject;


public class GetRepairsAction extends PaginationAction<Repair> {
    private static final String tag = "TT-GetRepairsAction";
    private String mCardId;

    public GetRepairsAction(Context context, int pageIndex, int pageSize, String cardId){
        super(context, pageIndex, pageSize);
        mCardId = cardId;
        mServiceId = "REPORT_REPAIR_LIST";
        mURL = Constants.URL_REPORT_REPAIR_LIST;
    }

    public GetRepairsAction cloneCurrentPageAction(){
        GetRepairsAction action = new GetRepairsAction(
                mAppContext,
                getPageIndex(),
                getPageSize(),
                mCardId
        );
        return action;
    }

    public Repair convertJsonToResult(JSONObject item) throws JSONException{
        return Repair.fromJSON(item);
    }
}
