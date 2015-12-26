package com.creal.nest.actions;

import android.content.Context;

import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePwdAction extends AbstractAction<Boolean> {

    private String mCardId;
    private String mOldPassword;
    private String mPassword;
    public final static String KEY_OLD_PWD = "old_pwd";
    public final static String KEY_NEW_PWD = "new_pwd";

    public ChangePwdAction(Context context, String cardId, String oldPwd, String newPwd) {
        super(context);
        this.mCardId = cardId;
        this.mOldPassword = oldPwd;
        this.mPassword = newPwd;
        this.mServiceId = "URL_CHANGE_PWD";
        mURL = URL_CHANGE_PWD;
    }

    protected void addRequestParameters(JSONObject parameters, String timeStr) throws JSONException {
        parameters.put(KEY_CARD_ID, mCardId);
        parameters.put(KEY_OLD_PWD, mOldPassword);
        parameters.put(KEY_NEW_PWD, mPassword);
    }

    @Override
    protected Boolean createRespObject(JSONObject response) throws JSONException {
        return Boolean.TRUE;
    }
}