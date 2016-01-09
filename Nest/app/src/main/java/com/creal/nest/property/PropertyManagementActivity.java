package com.creal.nest.property;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.R;
import com.creal.nest.RechargeActivity;
import com.creal.nest.RechargeHistoryActivity;
import com.creal.nest.actions.AbstractAction;
import com.creal.nest.Constants;
import com.creal.nest.actions.CommonObjectAction;
import com.creal.nest.actions.JSONObjectAction;
import com.creal.nest.model.CardInfo;
import com.creal.nest.model.HouseInfo;
import com.creal.nest.util.JSONUtil;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.UIUtil;
import com.creal.nest.views.HeaderView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PropertyManagementActivity extends Activity {

    private static final String TAG = "XYK-LatestActivities";
    public static final String INTENT_EXTRA_HOUSE_INFO = "house_info";
    private TextView mSex;
    private TextView mName;
    private TextView mCommunityName;
    private TextView mAddress;
    private TextView mBill;
    private TextView mRemainingBalance;
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
        mRemainingBalance = (TextView)findViewById(R.id.id_txt_remaining_balance);

        findViewById(R.id.id_btn_property_pay).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PropertyManagementActivity.this, PropertyBillListActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.id_btn_property_repair).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PropertyManagementActivity.this, PropertyRepairActivity.class);
                startActivity(intent);
            }
        });

        View.OnClickListener baseInfoListener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PropertyManagementActivity.this, PropertyBaseInfoActivity.class);
                startActivity(intent);
            }
        };
        mSex.setOnClickListener(baseInfoListener);
        mName.setOnClickListener(baseInfoListener);
        mCommunityName.setOnClickListener(baseInfoListener);

        HouseInfo info = getIntent().getParcelableExtra(INTENT_EXTRA_HOUSE_INFO);
        init(info);
    }

    private void init(HouseInfo info){
        mSex.setText(String.format(getString(R.string.property_user_sex), info.getGender()));
        mCommunityName.setText(info.getCommunityName());
        mName.setText(info.getName());
        mAddress.setText(info.getAddress());
        mBill.setText(info.getBill());
    }

    public void onQueryBalanceClick(View view) {
        final Dialog dialog = UIUtil.showLoadingDialog(this, getString(R.string.loading), false);
        String cardId = PreferenceUtil.getString(this, Constants.APP_USER_CARD_ID, null);
        Map paras = new HashMap();
        paras.put(Constants.KEY_CARD_ID, cardId);
        CommonObjectAction<CardInfo> action = new CommonObjectAction<>(this, Constants.URL_GET_CARD_INFO, paras, CardInfo.class);
        action.execute(new AbstractAction.UICallBack<CardInfo>() {
            public void onSuccess(CardInfo cardInfo) {
                mRemainingBalance.setText(String.valueOf(cardInfo.getMoney()));
                dialog.dismiss();
            }

            public void onFailure(AbstractAction.ActionError error) {
                Toast.makeText(PropertyManagementActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
    }

    public void onRechargeClick(View view) {
        Intent intent = new Intent(this, RechargeActivity.class);
        startActivity(intent);
    }

    public void onPayHistoryClick(View view) {
        Intent intent = new Intent(this, PayHistoryActivity.class);
        startActivity(intent);
    }

    public void onRepairHistoryClick(View view) {
        Intent intent = new Intent(this, RepairsActivity.class);
        startActivity(intent);
    }
}