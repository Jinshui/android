package com.creal.nest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.UIUtil;
import com.creal.nest.util.Utils;
import com.creal.nest.views.GestureLockViewGroup;
import com.creal.nest.views.HeaderView;

import java.util.List;
import java.util.Stack;


public class GesturePwdActivity extends Activity {
    private static final String tag = "XYK-GesturePwdActivity";
    public static final String INTENT_EXTRA_ACTION_TYPE = "action_type";

    public enum State {
        SET_PWD,
        CONFIRM_PWD,
        INPUT_OLD_PWD,
        INPUT_NEW_PWD,
        CONFIRM_NEW_PWD,
        VERIFY_PWD
    }

    public static final String INTENT_EXTRA_TITLE = "title";

    private GestureLockViewGroup mGestureLockViewGroup;
    private TextView mPhone;
    private View mOperationPanel;
    private HeaderView mHeaderView;
    private String mPassword;
    private Stack<State> mStateStack;
    private State mState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_pwd);
        mStateStack = new Stack<>();
        mHeaderView = (HeaderView) findViewById(R.id.header);
        mHeaderView.hideRightImage();
        mHeaderView.setLeftBtnListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mStateStack.isEmpty()) {
                    init(mStateStack.pop());
                }
            }
        });

        mOperationPanel = findViewById(R.id.id_panel_action);
        mPhone = (TextView) findViewById(R.id.id_text_phone);
        mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.id_gestureLockViewGroup);

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


    private void handleInput(String password) {
        Log.d(tag, "handleInput： " + mState + ", " + password);
        switch (mState) {
            case SET_PWD:
                mPassword = password;
                init(State.CONFIRM_PWD);
                break;
            case CONFIRM_NEW_PWD:
            case CONFIRM_PWD:
                if (checkAnswer(mPassword, password)) {
                    String phone = PreferenceUtil.getString(this, Constants.APP_USER_PHONE, "");
                    mPhone.setTextColor(Color.WHITE);
                    mPhone.setText(phone);
                    PreferenceUtil.saveString(this, Constants.APP_USER_GESTURE_PWD, Utils.md5(password));
                    startMainActivity();
                } else {
                    mPhone.setText(R.string.guesture_pwd_confirm_failed);
                    Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                    mPhone.setTextColor(Color.RED);
                    mPhone.startAnimation(shake);
                    mGestureLockViewGroup.reset();
                }
                break;
            case VERIFY_PWD:
                String savedPwd = PreferenceUtil.getString(this, Constants.APP_USER_GESTURE_PWD, null);
                if (checkAnswer(savedPwd, Utils.md5(password))) {
                    startMainActivity();
                } else {
                    init(State.VERIFY_PWD);
                }
                break;
            case INPUT_OLD_PWD:
                String oldPwd = PreferenceUtil.getString(this, Constants.APP_USER_GESTURE_PWD, null);
                if (checkAnswer(oldPwd, Utils.md5(password))) {
                    init(State.INPUT_NEW_PWD);
                } else {
                    init(State.INPUT_OLD_PWD);
                }
                break;
            case INPUT_NEW_PWD:
                mPassword = password;
                init(State.CONFIRM_NEW_PWD);
                break;
        }
    }

    private boolean checkAnswer(String password1, String password2) {
        return password1 != null && TextUtils.equals(password1, password2);
    }

    private void init(State state) {
        mState = state;
        mOperationPanel.setVisibility(View.INVISIBLE);
        mGestureLockViewGroup.reset();
        mHeaderView.hideRightText();
        mPhone.setTextColor(Color.WHITE);
        mPhone.setText(PreferenceUtil.getString(this, Constants.APP_USER_PHONE, ""));
        switch (mState) {
            case SET_PWD:
                mPassword = null;
                PreferenceUtil.removeKey(this, Constants.APP_USER_GESTURE_PWD);
                mHeaderView.setTitle(getIntent().getIntExtra(INTENT_EXTRA_TITLE, R.string.guesture_pwd_set));
                mHeaderView.setRightText(R.string.guesture_pwd_skip);
                mHeaderView.setRightTextListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        startMainActivity();
                    }
                });
                String phone = PreferenceUtil.getString(this, Constants.APP_USER_PHONE, "");
                mPhone.setText(phone);
                break;
            case CONFIRM_PWD:
                mStateStack.push(State.SET_PWD);
                mHeaderView.setTitle(R.string.guesture_pwd_confirm);
                break;
            case INPUT_OLD_PWD:
                mStateStack.push(State.VERIFY_PWD);
                mHeaderView.setTitle(R.string.guesture_pwd_input_old_pwd);
                break;
            case INPUT_NEW_PWD:
                mPassword = null;
                mStateStack.push(State.INPUT_OLD_PWD);
                mHeaderView.setTitle(R.string.guesture_pwd_new);
                break;
            case CONFIRM_NEW_PWD:
                mStateStack.push(State.INPUT_NEW_PWD);
                mHeaderView.setTitle(R.string.guesture_pwd_new_confirm);
                mHeaderView.setRightTextListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        startMainActivity();
                    }
                });
                break;
            case VERIFY_PWD:
                mHeaderView.setTitle(R.string.guesture_pwd_verify);
                mOperationPanel.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(GesturePwdActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onForgotPwdClick(View view) {
        UIUtil.showConfirmDialog(this,
                R.string.guesture_pwd_forgot_dlg,
                R.string.guesture_pwd_login,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        GesturePwdActivity.this.finish();
                        Intent intent = new Intent(GesturePwdActivity.this, LoginActivity.class);
                        intent.putExtra(LoginActivity.INTENT_EXTRA_ACTION_TYPE, LoginActivity.ACTION_REST_GESTURE_PWD);
                        startActivity(intent);
                        finish();
                    }
                },
                android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    public void onChangePwdClick(View view) {
        init(State.INPUT_OLD_PWD);
    }
}
