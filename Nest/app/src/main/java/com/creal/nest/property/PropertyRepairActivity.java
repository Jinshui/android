package com.creal.nest.property;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.creal.nest.R;
import com.creal.nest.actions.AbstractAction;
import com.creal.nest.Constants;
import com.creal.nest.actions.JSONObjectAction;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.UIUtil;
import com.creal.nest.views.HeaderView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PropertyRepairActivity extends Activity {

    private static final String TAG = "XYK-PropertyRepairActivity";

    private RadioButton mBtnCommon;
    private RadioButton mBtnPersonal;

    private EditText mTitle;
    private EditText mUname;
    private EditText mPhone;
    private EditText mSummary;
//    private EditText mVcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_repair);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.property_repair);

        mTitle = (EditText)findViewById(R.id.id_txt_repair_title);
        mUname = (EditText)findViewById(R.id.id_txt_repair_uname);
        mPhone = (EditText)findViewById(R.id.id_txt_repair_phone);
        mSummary = (EditText)findViewById(R.id.id_txt_repair_summary);
//        mVcode = (EditText)findViewById(R.id.id_txt_repair_vecode);

        mBtnCommon = (RadioButton)findViewById(R.id.id_btn_common);
        mBtnPersonal = (RadioButton)findViewById(R.id.id_btn_personal);
        mBtnCommon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mBtnPersonal.setChecked(false);
                }
            }
        });
        mBtnPersonal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mBtnCommon.setChecked(false);
                }
            }
        });
    }


    public void onChangeVerificationCodeClick(View view) {

    }

    public void onSubmitClick(View view) {

        final CharSequence title = mTitle.getText();
        if(TextUtils.isEmpty(title) || title.length() > 20){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            Toast.makeText(this, R.string.property_repair_err_name, Toast.LENGTH_LONG).show();
            mTitle.startAnimation(shake);
            return;
        }

        final CharSequence contact = mUname.getText();
        if(TextUtils.isEmpty(contact)){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            Toast.makeText(this, R.string.property_repair_err_uname, Toast.LENGTH_LONG).show();
            mUname.startAnimation(shake);
            return;
        }

        final CharSequence phone = mPhone.getText();
        if(TextUtils.isEmpty(phone)){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mPhone.startAnimation(shake);
            return;
        }

        final CharSequence summary = mSummary.getText();
        if(TextUtils.isEmpty(summary)){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mSummary.startAnimation(shake);
            return;
        }

        String cardId = PreferenceUtil.getString(this, Constants.APP_USER_CARD_ID, null);
        Map parameters = new HashMap<>();
        parameters.put(Constants.KEY_CARD_ID, cardId);
        parameters.put("repair_type", mBtnCommon.isChecked()? 1 : 2 );
        parameters.put("title", mTitle.getText().toString() );
        parameters.put("linkman", mUname.getText().toString() );
        parameters.put("contact", mPhone.getText().toString() );
        parameters.put("description", mSummary.getText().toString() );
//        parameters.put("safecode", mVcode.getText().toString() );
        final Dialog dialog = UIUtil.showLoadingDialog(this, getString(R.string.loading), true);
        JSONObjectAction action = new JSONObjectAction(this, Constants.URL_REPORT_REPAIR, parameters);
        action.execute(new AbstractAction.UICallBack<JSONObject>() {
            public void onSuccess(JSONObject info) {
                dialog.dismiss();
                Intent intent = new Intent(PropertyRepairActivity.this, RepairsActivity.class);
                startActivity(intent);
                finish();
            }

            public void onFailure(AbstractAction.ActionError error) {
                dialog.dismiss();
                Toast.makeText(PropertyRepairActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
