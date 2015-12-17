package com.creal.nest.actions;

import android.content.Context;
import android.util.Log;

import com.creal.nest.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class LoginAction extends AbstractAction {
    private final static String tag = "N-LoginAction";
    private String mCardNum;
    private String mPassword;

    public LoginAction(Context context, String cardNum, String pwd) {
        super(context);
        this.mCardNum = cardNum;
        this.mPassword = pwd;
        this.mServiceId = "LOGIN";
        mURL = "http://manager.go.wuxian114.com/lmk_interface/login/index.php";
    }

    @Override
    protected void addRequestParameters(JSONObject params, String timeStr) throws JSONException {
        params.put("card_num", mCardNum);
        params.put("password", Utils.md5(Utils.md5(mPassword) + "123456789" + timeStr));
    }

    @Override
    protected Object createRespObject(JSONObject response) throws JSONException {
        return null;
    }
}
