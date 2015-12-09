package com.creal.nest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ExchangeSuccDialog extends Activity {

    private static final String TAG = "XYK-SnapCouponDialog";

    private TextView mMsgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_echange_commodity_succ);
        mMsgView = (TextView)findViewById(R.id.id_text_message);
        String message = getIntent().getStringExtra("message");
        if(message != null)
            mMsgView.setText(message);
    }

    public void onViewHistoryClick(View view){
        finish();
    }

}
