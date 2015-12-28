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
    private final static String tag = "N-LoginActivity";
    private EditText mCardId;
    private EditText mPassword;
    private Button mBtnLogin;
    private TextView mBtnForgotPwd;
//    private ProgressDialog mProgressDialog;
    private Dialog mProgressDialog;

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
        mBtnLogin = (Button)findViewById(R.id.id_btn_login);
        mBtnForgotPwd = (TextView)findViewById(R.id.id_btn_forget_pwd);

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
//        mProgressDialog = ProgressDialog.show(this, null, "正在登录中...", true, false);
        mProgressDialog = UIUtil.createLoadingDialog(this, getString(R.string.signing), false);
        mProgressDialog.show();
        loginAction.execute(new AbstractAction.UICallBack() {
            public void onSuccess(Object result) {
                mProgressDialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, GesturePwdActivity.class);
                startActivity(intent);
                finish();
            }

            public void onFailure(AbstractAction.ActionError error) {
                mProgressDialog.dismiss();
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
