package com.creal.nest.model;

import com.creal.nest.actions.ActionRespObject;

import org.json.JSONException;
import org.json.JSONObject;

public class ReceiveCouponResult implements ActionRespObject<ReceiveCouponResult> {

    private int value;
    private int receiveId;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(int receiveId) {
        this.receiveId = receiveId;
    }

    @Override
    public ReceiveCouponResult fillWithJSON(JSONObject response) throws JSONException {
        if(response.has("value") && response.getString("value").length() > 0)
            this.setValue(response.getInt("value"));
        if(response.has("receive_id"))
            this.setReceiveId(response.getInt("receive_id"));
        return this;
    }
}
