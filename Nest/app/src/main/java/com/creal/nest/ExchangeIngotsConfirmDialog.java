package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.CommonObjectAction;
import com.creal.nest.model.RechargeCard;
import com.creal.nest.util.PreferenceUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ExchangeIngotsConfirmDialog extends Activity {

    private static final String TAG = "XYK-SnapCouponDialog";
    public static final String INTENT_EXTRA_RECHARGE_CARD = "recharge_card";
    private RechargeCard mRechargeCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_echange_ingots_confirm);
        TextView mMsgView = (TextView)findViewById(R.id.id_text_message);
        mRechargeCard = getIntent().getParcelableExtra(INTENT_EXTRA_RECHARGE_CARD);
        mMsgView.setText(String.format(getString(R.string.exchange_recharge_card_hint), mRechargeCard.getNum(), mRechargeCard.getAmount()));
    }

    public void onConfirmExchange(View view){
        String cardId = PreferenceUtil.getString(this, Constants.APP_USER_CARD_ID, null);
        Map parameters = new HashMap();
        parameters.put(Constants.KEY_CARD_ID, cardId);
        parameters.put("recharge_card_id", mRechargeCard.getId());
        parameters.put("exchange_num", mRechargeCard.getNum());
        CommonObjectAction exchangeIngotsAction = new CommonObjectAction(this, Constants.URL_EXCHANGE_INGOTS, parameters, null);
        exchangeIngotsAction.execute(new AbstractAction.UICallBack<Objects>() {
            public void onSuccess(Objects result) {
                showSuccessDialog();
            }

            public void onFailure(AbstractAction.ActionError error) {
                Toast.makeText(ExchangeIngotsConfirmDialog.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showSuccessDialog(){
        Intent intent = new Intent(this, ExchangeSuccDialog.class);
        intent.putExtra(ExchangeSuccDialog.INTENT_EXTRA_FROM, ExchangeSuccDialog.INTENT_EXTRA_FROM_INGOT_MALL);
        intent.putExtra(ExchangeSuccDialog.INTENT_EXTRA_TITLE, getString(R.string.exchange_succ));
        intent.putExtra(ExchangeSuccDialog.INTENT_EXTRA_BTN1_TXT, getString(R.string.back_to_ingots_mall));
        intent.putExtra(ExchangeSuccDialog.INTENT_EXTRA_MSG, String.format(getString(R.string.exchange_ingots_succ_desc), mRechargeCard.getNum()));
        startActivity(intent);
        finish();
    }


    public void onBackClick(View view){
        finish();
    }

}
