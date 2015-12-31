package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.Utils;
import com.creal.nest.views.GestureLockViewGroup;
import com.creal.nest.views.HeaderView;

import java.util.List;


public class GesturePwdActivity extends Activity {
    private static final String tag           = "XYK-GesturePwdActivity";
    public static final String INTENT_EXTRA_ACTION_TYPE           = "action_type";
    public enum State{
        SET_PWD,
        CONFIRM_PWD,
        INPUT_OLD_PWD,
        INPUT_NEW_PWD,
        CONFIRM_NEW_PWD,
        VERIFY_PWD
    }
    public static final String INTENT_EXTRA_TITLE                 = "title";

    private GestureLockViewGroup mGestureLockViewGroup;
    private TextView mPhone;
    private HeaderView mHeaderView;
    private String mPassword;
    private State mState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_pwd);
        mHeaderView = (HeaderView) findViewById(R.id.header);
        mHeaderView.hideRightImage();

        mPhone = (TextView) findViewById(R.id.id_text_phone);
        mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.id_gestureLockViewGroup);

        mGestureLockViewGroup.setAnswer(new int[]{1, 2, 3, 4, 5});
        mGestureLockViewGroup.setOnGestureLockViewListener(new GestureLockViewGroup.OnGestureLockViewListener() {
            @Override
            public void onUnmatchedExceedBoundary() {
                Toast.makeText(GesturePwdActivity.this, "错误5次...", Toast.LENGTH_SHORT).show();
                mGestureLockViewGroup.setUnMatchExceedBoundary(5);
            }

            @Override
            public void onInputComplete(List<Integer> password) {
                String pwd = "";
                for (Integer integer : password) {
                    pwd += integer;
                }
                handleInput(pwd);
            }

            @Override
            public void onBlockSelected(int cId) {
            }
        });

        init(State.valueOf(getIntent().getStringExtra(INTENT_EXTRA_ACTION_TYPE)));
    }


    private void handleInput(String password){
        Log.d(tag, "handleInput： " + mState + ", " + password);
        switch (mState) {
            case SET_PWD:
                mPassword = password;
                init(State.CONFIRM_PWD);
                break;
            case CONFIRM_NEW_PWD:
            case CONFIRM_PWD:
                if(checkAnswer(mPassword, password)){
                    String phone = PreferenceUtil.getString(this, Constants.APP_USER_PHONE, "");
                    mPhone.setTextColor(Color.WHITE);
                    mPhone.setText(phone);
                    PreferenceUtil.saveString(this, Constants.APP_USER_GESTURE_PWD, Utils.md5(password));
                    startMainActivity();
                }else{
                    mPhone.setText(R.string.guesture_pwd_confirm_failed);
                    Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                    mPhone.setTextColor(Color.RED);
                    mPhone.startAnimation(shake);
                    mGestureLockViewGroup.reset();
                }
                break;
            case VERIFY_PWD:
                String savedPwd = PreferenceUtil.getString(this, Constants.APP_USER_GESTURE_PWD, null);
                if(checkAnswer(savedPwd, Utils.md5(password))){
                    startMainActivity();
                }else{
                    init(State.VERIFY_PWD);
                }
                break;
            case INPUT_OLD_PWD:
                String oldPwd = PreferenceUtil.getString(this, Constants.APP_USER_GESTURE_PWD, null);
                if(checkAnswer(oldPwd, Utils.md5(password))){
                    init(State.INPUT_NEW_PWD);
                }else{
                    init(State.INPUT_OLD_PWD);
                }
                break;
            case INPUT_NEW_PWD:
                mPassword = password;
                init(State.CONFIRM_NEW_PWD);
                break;
        }
    }

    private boolean checkAnswer(String password1, String password2)
    {
        Log.d(tag, "password1： " + password1 + ", password2: " + password2);
        return password1 != null && TextUtils.equals(password1, password2);
    }

    private void init(State state){
        mState = state;
        mGestureLockViewGroup.reset();
        mHeaderView.hideRightText();
        mPhone.setTextColor(Color.WHITE);
        mPhone.setText(PreferenceUtil.getString(this, Constants.APP_USER_PHONE, ""));
        switch (mState) {
            case SET_PWD:
                mPassword = null;
                mHeaderView.setTitle(getIntent().getIntExtra(INTENT_EXTRA_TITLE, R.string.guesture_pwd_set));
                mHeaderView.setRightText(R.string.guesture_pwd_skip);
                mHeaderView.setRightTextListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        startMainActivity();
                    }
                });
//                findViewById(R.id.id_panel_action).setVisibility(View.GONE);
                String phone = PreferenceUtil.getString(this, Constants.APP_USER_PHONE, "");
                mPhone.setText(phone);
                break;
            case CONFIRM_PWD:
                mHeaderView.setTitle(R.string.guesture_pwd_confirm);
                break;
            case INPUT_OLD_PWD:
                mHeaderView.setTitle(R.string.guesture_pwd_input_old_pwd);
                break;
            case INPUT_NEW_PWD:
                mPassword = null;
                mHeaderView.setTitle(R.string.guesture_pwd_new);
//                mHeaderView.setRightText(R.string.guesture_pwd_skip);
//                mHeaderView.setRightTextListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        startMainActivity();
//                    }
//                });
                break;
            case CONFIRM_NEW_PWD:
                mHeaderView.setTitle(R.string.guesture_pwd_new_confirm);
                mHeaderView.setRightText(R.string.guesture_pwd_skip);
                mHeaderView.setRightTextListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        startMainActivity();
                    }
                });
                break;
            case VERIFY_PWD:
                mHeaderView.setTitle(R.string.guesture_pwd_verify);
                break;
        }
    }

    private void startMainActivity(){
        Intent intent = new Intent(GesturePwdActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onForgotPwdClick(View view){
        Intent intent = new Intent(GesturePwdActivity.this, LoginDialog.class);
        startActivityForResult(intent, LoginDialog.DIALOG_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LoginDialog.DIALOG_ID) {
            if(data.getBooleanExtra(Constants.APP_USER_AUTHORIZED, Boolean.FALSE)){
                init(State.INPUT_NEW_PWD);
            }
        }
    }

    public void onChangePwdClick(View view) {
        init(State.INPUT_OLD_PWD);
    }
}
