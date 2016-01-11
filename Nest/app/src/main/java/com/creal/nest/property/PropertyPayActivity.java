package com.creal.nest.property;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.Constants;
import com.creal.nest.ExchangeSuccDialog;
import com.creal.nest.R;
import com.creal.nest.RechargeActivity;
import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.CommonObjectAction;
import com.creal.nest.actions.JSONObjectAction;
import com.creal.nest.model.ArrearageInfo;
import com.creal.nest.model.CardInfo;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.UIUtil;
import com.creal.nest.views.HeaderView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PropertyPayActivity extends Activity {

    private static final String TAG = "XYK-LatestActivities";
    private TextView mRemainingBalance;
    private EditText mPassword;
    private TextView mPayAmount;
    private ArrearageInfo mArrearageInfo;
    public static final String INTENT_EXTRA_ARREARAGE_INFO = "arrearage_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_pay);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.property_pay);
        mRemainingBalance = (TextView)findViewById(R.id.id_txt_remaining_balance);
        mPayAmount = (TextView)findViewById(R.id.id_txt_pay_amount);
        mPassword = (EditText)findViewById(R.id.id_txt_password);
        mArrearageInfo = getIntent().getParcelableExtra(INTENT_EXTRA_ARREARAGE_INFO);
        mPayAmount.setText(String.format(getString(R.string.pay_amount), mArrearageInfo.getMoney()));
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
                Toast.makeText(PropertyPayActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
    }

    public void onRechargeClick(View view) {
        Intent intent = new Intent(this, RechargeActivity.class);
        startActivity(intent);
    }

    public void onPayClick(View view) {

        final CharSequence password = mPassword.getText();
        if(TextUtils.isEmpty(password)){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mPassword.startAnimation(shake);
            return;
        }

        Map map = new HashMap();
        String cardId = PreferenceUtil.getString(this, Constants.APP_USER_CARD_ID, null);
        map.put(Constants.KEY_CARD_ID, cardId);
        map.put("arrear_id", mArrearageInfo.getId());
        map.put(Constants.KEY_PASSWORD, map);
        JSONObjectAction action = new JSONObjectAction(this, Constants.URL_PAY, map);

        Intent intent = new Intent(this, ExchangeSuccDialog.class);
        intent.putExtra(ExchangeSuccDialog.INTENT_EXTRA_TITLE, getString(R.string.property_pay_succ));
        intent.putExtra(ExchangeSuccDialog.INTENT_EXTRA_BTN1_TXT, getString(R.string.property_back_to_property));
        intent.putExtra(ExchangeSuccDialog.INTENT_EXTRA_MSG, getString(R.string.property_pay_succ_msg));
        intent.putExtra(ExchangeSuccDialog.INTENT_EXTRA_BTN2_TXT, getString(R.string.property_view_pay_history));
        intent.putExtra(ExchangeSuccDialog.INTENT_EXTRA_FROM, ExchangeSuccDialog.INTENT_EXTRA_FROM_PROPERTY_PAY);
        startActivity(intent);
        finish();
    }
}
