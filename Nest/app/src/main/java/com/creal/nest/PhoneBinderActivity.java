package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.creal.nest.R;
import com.creal.nest.test.TestActivity;
import com.creal.nest.views.HeaderView;

public class PhoneBinderActivity extends Activity {

    private EditText mCardId;
    private EditText mPassword;
    private Button mBtnLogin;
    private TextView mBtnForgotPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_binder);

        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.binding_title);
        headerView.setTitleLeft();

        headerView.setRightText(R.string.register);
        headerView.setRightTextListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PhoneBinderActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mCardId = (EditText)findViewById(R.id.id_txt_card_id);
        mPassword = (EditText)findViewById(R.id.id_txt_password);
    }

    public void onBindClick(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onTestClick(View view){
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
        finish();
    }


    public void onSendVerificationCodeClick(View view){

    }

    public void onCanntGetVerificationCodeClick(View view){

    }

}
