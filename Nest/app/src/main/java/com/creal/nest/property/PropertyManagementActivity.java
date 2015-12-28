package com.creal.nest.property;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.R;
import com.creal.nest.RechargeActivity;
import com.creal.nest.RechargeHistoryActivity;
import com.creal.nest.actions.AbstractAction;
import com.creal.nest.Constants;
import com.creal.nest.actions.JSONObjectAction;
import com.creal.nest.util.JSONUtil;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.UIUtil;
import com.creal.nest.views.HeaderView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PropertyManagementActivity extends Activity {

    private static final String TAG = "XYK-LatestActivities";
    private TextView mSex;
    private TextView mName;
    private TextView mCommunityName;
    private TextView mAddress;
    private TextView mBill;
    private TextView m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_management);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.property_management);

        mSex = (TextView)findViewById(R.id.id_text_user_sex);
        mName = (TextView)findViewById(R.id.id_text_user_name);
        mCommunityName = (TextView)findViewById(R.id.id_text_property_name);
        mAddress = (TextView)findViewById(R.id.id_text_property_address);
        mSex = (TextView)findViewById(R.id.id_text_user_sex);
        mBill = (TextView)findViewById(R.id.id_txt_bill);


        findViewById(R.id.id_btn_property_pay).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PropertyManagementActivity.this, PropertyPayActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.id_btn_property_repair).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PropertyManagementActivity.this, PropertyRepairActivity.class);
                startActivity(intent);
            }
        });

        init();
    }


    private void init(){
        String cardId = PreferenceUtil.getString(this, Constants.APP_USER_CARD_ID, null);
        Map parameters = new HashMap<>();
        parameters.put(Constants.KEY_CARD_ID, cardId);
        final Dialog dialog = UIUtil.showLoadingDialog(this, getString(R.string.loading), true);
        JSONObjectAction action = new JSONObjectAction(this, Constants.URL_BIND_PROPERTY, parameters);
        action.execute(new AbstractAction.UICallBack<JSONObject>() {
            public void onSuccess(JSONObject info) {
                mSex.setText(String.format(getString(R.string.property_user_sex), JSONUtil.getString(info, "gender", "")));
                mCommunityName.setText(JSONUtil.getString(info, "residential_quarter", ""));
                mName.setText(JSONUtil.getString(info, "name", ""));
                mAddress.setText(JSONUtil.getString(info, "address", ""));
                mBill.setText(JSONUtil.getString(info, "arrear", ""));
                dialog.dismiss();
            }

            public void onFailure(AbstractAction.ActionError error) {
                dialog.dismiss();
                Toast.makeText(PropertyManagementActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



    public void onRechargeClick(View view) {
        Intent intent = new Intent(this, RechargeActivity.class);
        startActivity(intent);
    }

    public void onPayHistoryClick(View view) {
        Intent intent = new Intent(this, RechargeHistoryActivity.class);
        startActivity(intent);
    }

    public void onRepairHistoryClick(View view) {
        Intent intent = new Intent(this, RepairsActivity.class);
        startActivity(intent);
    }
}
