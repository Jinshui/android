package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExchangeSuccDialog extends Activity {

    private static final String TAG = "XYK-SnapCouponDialog";

    private TextView mMsgView;
    private Button mButton;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_echange_commodity_succ);
        mMsgView = (TextView)findViewById(R.id.id_text_message);
        mButton = (Button)findViewById(R.id.id_btn_back_to_mall);
        from = getIntent().getStringExtra("from");
        String btnText = getIntent().getStringExtra("btnText");
        String message = getIntent().getStringExtra("message");
        if(btnText != null)
            mButton.setText(btnText);
        if(message != null)
            mMsgView.setText(message);
    }

    public void onViewHistoryClick(View view){
        if("ingots_mall".equals(from)){
            Intent intent = new Intent(this, IngotsExchangeHistoryActivity.class);
            startActivity(intent);
        }else if("points_mall".equals(from)){
            Intent intent = new Intent(this, ExchangePointHistoryActivity.class);
            startActivity(intent);
        }
        finish();
    }

    public void onBackClick(View view){
        finish();
    }



}
