package com.creal.nest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.LoginAction;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.UIUtil;
import com.creal.nest.views.HeaderView;

public class LoginActivity extends Activity {
    private EditText mCardId;
    private EditText mPassword;
    public static final String INTENT_EXTRA_ACTION_TYPE = "action_type";
    public static final String ACTION_REST_GESTURE_PWD = "reset_gesture_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.login_account_login);
        headerView.setTitleLeft();

        mCardId = (EditText)findViewById(R.id.id_txt_card_id);
        mPassword = (EditText)findViewById(R.id.id_txt_password);

        mCardId.setText(PreferenceUtil.getString(this, Constants.APP_USER_CARD_NUM, ""));
        mPassword.setText(PreferenceUtil.getString(this, Constants.APP_USER_PWD, ""));
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
                Intent intent = new Intent(LoginActivity.this, GesturePwdActivity.class);
                String savedPwd = PreferenceUtil.getString(LoginActivity.this, Constants.APP_USER_GESTURE_PWD, null);
                if(ACTION_REST_GESTURE_PWD.equals(getIntent().getStringExtra(INTENT_EXTRA_ACTION_TYPE)) || TextUtils.isEmpty(savedPwd))
                    intent.putExtra(GesturePwdActivity.INTENT_EXTRA_ACTION_TYPE, GesturePwdActivity.State.SET_PWD.name());
                else
                    intent.putExtra(GesturePwdActivity.INTENT_EXTRA_ACTION_TYPE, GesturePwdActivity.State.VERIFY_PWD.name());
                startActivity(intent);
                finish();
            }

            public void onFailure(AbstractAction.ActionError error) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
