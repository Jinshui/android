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
    }

    public void onConfirmExchange(View view){
        Intent intent = new Intent(this, ExchangeSuccDialog.class);
        intent.putExtra("from", "ingots_mall");
        intent.putExtra("btnText", getString(R.string.back_to_ingots_mall));
        intent.putExtra("message", getString(R.string.exchange_ingots_succ_desc));
        startActivity(intent);
        finish();
    }

    public void onBackClick(View view){
        finish();
    }

}
