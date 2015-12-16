package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ExchangeIngotsConfirmDialog extends Activity {

    private static final String TAG = "XYK-SnapCouponDialog";

    private TextView mMsgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_echange_ingots_confirm);
        mMsgView = (TextView)findViewById(R.id.id_text_message);
        String message = getIntent().getStringExtra("message");
        if(message != null)
            mMsgView.setText(message);
    }

    public void onConfirmExchange(View view){
        Intent intent = new Intent(this, ExchangeSuccDialog.class);
        intent.putExtra("message", "您兑换的1000元宝已经兑换成功。");
        startActivity(intent);
        finish();
    }

    public void onBackClick(View view){
        finish();
    }

}
