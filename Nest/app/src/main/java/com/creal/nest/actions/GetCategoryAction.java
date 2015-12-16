package com.creal.nest.actions;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class GetCategoryAction extends PaginationAction<String>{
    public GetCategoryAction(Context context, int pageIndex, int pageSize) {
        super(context, pageIndex, pageSize);
        mServiceId = SERVICE_ID_CATEGORY;
        mURL = "";
    }

    protected void addRequestParameters(JSONObject parameters, String timeStr) throws JSONException {
        super.addRequestParameters(parameters, timeStr);
    }

    @Override
    public PaginationAction<String> cloneCurrentPageAction() {
        return new GetCategoryAction(mAppContext, getPageIndex(), getPageSize());
    }

    @Override
    public String convertJsonToResult(JSONObject item) throws JSONException {
        return item.toString();
    }
}