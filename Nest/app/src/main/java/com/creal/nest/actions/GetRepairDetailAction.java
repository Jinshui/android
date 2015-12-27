package com.creal.nest.actions;

import android.content.Context;

import com.creal.nest.model.Recharge;
import com.creal.nest.model.Repair;

import org.json.JSONException;
import org.json.JSONObject;


public class GetRepairDetailAction extends AbstractAction<Repair> {
    private static final String tag = "TT-GetRepairDetailAction";
    private String mCardId;
    private String mRepairId;


    public GetRepairDetailAction(Context context,String cardId, String repairId){
        super(context);
        mCardId = cardId;
        mRepairId = repairId;
        mServiceId = "URL_REPORT_REPAIR_DETAIL";
        mURL = URL_REPORT_REPAIR_DETAIL;
    }

    protected JSONObject getRequestBody(String timeStr) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_CARD_ID, mCardId);
        jsonObject.put("repair_id", mRepairId);
        return jsonObject;
    }

    @Override
    protected Repair createRespObject(JSONObject response) throws JSONException {
        return Repair.fromJSON(response);
    }
}
