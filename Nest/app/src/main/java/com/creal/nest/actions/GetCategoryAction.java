package com.creal.nest.actions;

import android.content.Context;

import com.creal.nest.Constants;
import com.creal.nest.model.ShopCategory;

import org.json.JSONException;
import org.json.JSONObject;

public class GetCategoryAction extends PaginationAction<ShopCategory>{

    public GetCategoryAction(Context context, int pageIndex, int pageSize) {
        super(context, pageIndex, pageSize);
        mServiceId = "GET_SHOP_CATEGORY";
        mURL = Constants.URL_GET_SHOP_CATEGORY;
    }

    @Override
    public PaginationAction<ShopCategory> cloneCurrentPageAction() {
        return new GetCategoryAction(mAppContext, getPageIndex(), getPageSize());
    }

    @Override
    public ShopCategory convertJsonToResult(JSONObject json) throws JSONException {
        return ShopCategory.fromJSON(json);
    }

    protected String getContentsKey(){
        return "comclass";
    }
}