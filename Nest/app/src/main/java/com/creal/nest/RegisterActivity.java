package com.creal.nest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.RegisterAction;
import com.creal.nest.actions.StringAction;
import com.creal.nest.util.TimeCountUtil;
import com.creal.nest.util.UIUtil;
import com.creal.nest.util.Utils;
import com.creal.nest.views.HeaderView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity {

    private EditText mPhone;
    private EditText mVerificationCode;
    private Button mBtnGetCode;
    private EditText mPassword;
    private EditText mUserName;
    private EditText mUserID;
    private EditText mDetailAddress;
    private CheckBox mCheckBoxAcceptTerms;
    private RadioGroup mRadioPost;
    private Dialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.register_title);
        headerView.setTitleLeft();

        mPhone = (EditText)findViewById(R.id.id_txt_phone);
        mBtnGetCode = (Button)findViewById(R.id.id_btn_get_code);
        mVerificationCode = (EditText)findViewById(R.id.id_txt_verification_code);
        mPassword = (EditText)findViewById(R.id.id_txt_password);
        mUserName = (EditText)findViewById(R.id.id_txt_user_name);
        mUserID = (EditText)findViewById(R.id.id_txt_user_id);
        mDetailAddress = (EditText)findViewById(R.id.id_txt_detail_addr);
        mCheckBoxAcceptTerms = (CheckBox)findViewById(R.id.id_checkbox_accept_terms);
        Spannable span = new SpannableString(mCheckBoxAcceptTerms.getText().toString());//获取按钮的文字
        span.setSpan(new ForegroundColorSpan(Color.parseColor("#1093e3")), 9, 17, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//讲倒计时时间显示为红色
        mCheckBoxAcceptTerms.setText(span);
        mRadioPost = (RadioGroup)findViewById(R.id.id_radio_post);
        mProgressDialog = UIUtil.createLoadingDialog(this, getString(R.string.dlg_msg_registering), false);
    }

    public void onGetVerificationCodeClick(View view){
        mVerificationCode.setText("");
        CharSequence phone = mPhone.getText();
        if(TextUtils.isEmpty(phone) || phone.length() != 11){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mPhone.startAnimation(shake);
            return;
        }
        final TimeCountUtil timeCountUtil = new TimeCountUtil(this, 60000, 1000, mBtnGetCode);
        timeCountUtil.start();
        Map parameters = new HashMap<>();
        parameters.put("mobile", phone.toString());
        StringAction getVerificationCodeAction = new StringAction(this, Constants.URL_GET_VERIFICATION_CODE, parameters, "code");
        getVerificationCodeAction.execute(new AbstractAction.UICallBack<String>() {
            public void onSuccess(String result) {
                mVerificationCode.setText(result);
                Toast.makeText(RegisterActivity.this, getString(R.string.send_succ), Toast.LENGTH_SHORT).show();
            }

            public void onFailure(AbstractAction.ActionError error) {
                if(error.getError() == AbstractAction.ErrorCode.NETWORK_ERROR){
                    timeCountUtil.cancel();
                    timeCountUtil.onFinish();
                }
                Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onRegisterClick(View view){
        final CharSequence phone = mPhone.getText();
        if(TextUtils.isEmpty(phone) || phone.length() != 11){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mPhone.startAnimation(shake);
            return;
        }
        CharSequence password = mPassword.getText();
        if(TextUtils.isEmpty(password) || password.length() < 6){
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
        if(!mCheckBoxAcceptTerms.isChecked()){
            Toast.makeText(RegisterActivity.this, getString(R.string.hint_msg_check_items), Toast.LENGTH_SHORT).show();
            return;
        }

        String name = mUserName.getText().toString();
        String id = mUserID.getText().toString();
        String address = mDetailAddress.getText().toString();
        boolean delivery = mRadioPost.getCheckedRadioButtonId() == R.id.id_radio_yes;
        if(delivery && (address==null || address.isEmpty())){
            Toast.makeText(RegisterActivity.this, getString(R.string.hint_msg_input_address), Toast.LENGTH_SHORT).show();
            return;
        }

        Map parameters = new HashMap();
        parameters.put(Constants.KEY_MOBILE, phone.toString());
        parameters.put(Constants.KEY_VERIFICATION_CODE, code.toString());
        parameters.put(Constants.KEY_PASSWORD, Utils.md5(password.toString()));
        if(name != null)
            parameters.put("name", name);
        if(id != null)
            parameters.put("id_card", id);
        if(address != null)
            parameters.put("address", address);
        parameters.put("is_mail", delivery);
        RegisterAction action = new RegisterAction(this, Constants.URL_REGISTER, parameters);
        mProgressDialog.show();
        action.execute(new AbstractAction.UICallBack<JSONObject>() {
            public void onSuccess(JSONObject response) {
                mProgressDialog.dismiss();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            public void onFailure(AbstractAction.ActionError error) {
                mProgressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onViewTermsClick(View view){

    }
}
