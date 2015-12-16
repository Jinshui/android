package com.creal.nest;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.R;
import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.LoginAction;
import com.creal.nest.views.HeaderView;

public class LoginActivity extends Activity {
    private final static String tag = "N-LoginActivity";
    private EditText mCardId;
    private EditText mPassword;
    private Button mBtnLogin;
    private TextView mBtnForgotPwd;

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
    }

    public void onLoginClick(View view){
        final LoginAction loginAction = new LoginAction(this, "9999900030", "8000");
        loginAction.execute(new AbstractAction.UICallBack() {
            public void onSuccess(Object result) {
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            }

            public void onFailure(AbstractAction.ActionError error) {
                Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = new Intent(this, GesturePwdActivity.class);
        startActivity(intent);
        finish();
    }

}
