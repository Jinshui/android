package com.yanis.yc_ui_fragment_tabhost.actions;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class GetCategoryAction extends PaginationAction<String>{
    public GetCategoryAction(Context context, int pageIndex, int pageSize) {
        super(context, pageIndex, pageSize);
        mServiceId = SERVICE_ID_CATEGORY;
    }

    public void addRequestParameters(JSONObject parameters) throws JSONException {
        super.addRequestParameters(parameters);
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