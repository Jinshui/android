package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.BindPhoneCardAction;
import com.creal.nest.actions.GetVerificationCodeAction;
import com.creal.nest.test.TestActivity;
import com.creal.nest.util.TimeCountUtil;
import com.creal.nest.util.Utils;
import com.creal.nest.views.HeaderView;

import org.json.JSONObject;

public class PhoneBinderActivity extends Activity {

    private EditText mPhone;
    private EditText mCardNum;
    private EditText mPassword;
    private EditText mVerificationCode;
    private Button mBtnGetCode;

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

        mPhone = (EditText)findViewById(R.id.id_txt_phone);
        mCardNum = (EditText)findViewById(R.id.id_txt_card_num);
        mPassword = (EditText)findViewById(R.id.id_txt_password);
        mVerificationCode = (EditText)findViewById(R.id.id_txt_verification_code);
        mBtnGetCode = (Button)findViewById(R.id.id_btn_get_code);
    }

    public void onBindClick(View view){
        CharSequence phone = mPhone.getText();
        if(TextUtils.isEmpty(phone) || phone.length() != 11){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mPhone.startAnimation(shake);
            return;
        }
        CharSequence cardNum = mCardNum.getText();
        if(TextUtils.isEmpty(cardNum)){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mCardNum.startAnimation(shake);
            return;
        }
        CharSequence password = mPassword.getText();
        if(TextUtils.isEmpty(password)){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mPassword.startAnimation(shake);
            return;
        }
        CharSequence code = mVerificationCode.getText();
        if(TextUtils.isEmpty(code)){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mVerificationCode.startAnimation(shake);
            return;
        }

        BindPhoneCardAction bindPhoneCardAction = new BindPhoneCardAction(this, phone.toString(), cardNum.toString(), password.toString(), code.toString());
        bindPhoneCardAction.execute(new AbstractAction.UICallBack<String>(){
            public void onSuccess(String result) {
                Intent intent = new Intent(PhoneBinderActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            public void onFailure(AbstractAction.ActionError error) {
                Toast.makeText(PhoneBinderActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onSendVerificationCodeClick(View view){
        mVerificationCode.setText("");
        CharSequence phone = mPhone.getText();
        if(TextUtils.isEmpty(phone) || phone.length() != 11){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mPhone.startAnimation(shake);
            return;
        }
        final TimeCountUtil timeCountUtil = new TimeCountUtil(this, 60000, 1000, mBtnGetCode);
        timeCountUtil.start();
        GetVerificationCodeAction getVerificationCodeAction = new GetVerificationCodeAction(this, phone.toString());
        getVerificationCodeAction.execute(new AbstractAction.UICallBack<String>() {
            public void onSuccess(String result) {
                mVerificationCode.setText(result);
                Toast.makeText(PhoneBinderActivity.this, getString(R.string.send_succ), Toast.LENGTH_SHORT).show();
            }

            public void onFailure(AbstractAction.ActionError error) {
                if(error.getError() == AbstractAction.ErrorCode.NETWORK_ERROR){
                    timeCountUtil.cancel();
                    timeCountUtil.onFinish();
                }
                Toast.makeText(PhoneBinderActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onCanntGetVerificationCodeClick(View view){

    }
}
