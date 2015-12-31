package com.creal.nest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.LoginAction;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.UIUtil;
import com.creal.nest.views.HeaderView;

public class LoginDialog extends Activity {
    public static final int DIALOG_ID = R.layout.dialog_login;
    private EditText mCardId;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login);

        mCardId = (EditText)findViewById(R.id.id_txt_card_id);
        mPassword = (EditText)findViewById(R.id.id_txt_password);

        mCardId.setText(PreferenceUtil.getString(this, Constants.APP_USER_CARD_NUM, ""));
//        mPassword.setText(PreferenceUtil.getString(this, Constants.APP_USER_PWD, ""));
    }

    public void onLoginClick(View view){
        CharSequence cardId = mCardId.getText();
        if(TextUtils.isEmpty(cardId)){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mCardId.startAnimation(shake);
            return;
        }
        CharSequence password = mPassword.getText();
        if(TextUtils.isEmpty(password)){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mPassword.startAnimation(shake);
            return;
        }

        PreferenceUtil.saveString(this, Constants.APP_USER_CARD_NUM, cardId.toString());
        PreferenceUtil.saveString(this, Constants.APP_USER_PWD, password.toString());
        final LoginAction loginAction = new LoginAction(this, cardId.toString(), password.toString());
        final Dialog progressDialog = UIUtil.createLoadingDialog(this, getString(R.string.signing), false);
        progressDialog.show();
        loginAction.execute(new AbstractAction.UICallBack() {
            public void onSuccess(Object result) {
                progressDialog.dismiss();
                Intent activityResult = new Intent();
                activityResult.putExtra(Constants.APP_USER_AUTHORIZED, Boolean.TRUE);
                setResult(DIALOG_ID, activityResult);
                finish();
            }

            public void onFailure(AbstractAction.ActionError error) {
                progressDialog.dismiss();
                Toast.makeText(LoginDialog.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onBackPressed() {
    }

    public void onCancelClick(View view){
        Intent activityResult = new Intent();
        activityResult.putExtra(Constants.APP_USER_AUTHORIZED, Boolean.FALSE);
        setResult(DIALOG_ID, activityResult);
        finish();
    }

}
