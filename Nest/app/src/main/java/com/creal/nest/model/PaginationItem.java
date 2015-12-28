package com.creal.nest.model;

import org.json.JSONException;
import org.json.JSONObject;

public interface PaginationItem<T> {
    public T fillWithJSON(JSONObject jsonObject) throws JSONException;
}
