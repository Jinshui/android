package com.creal.nest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class CommodityExchangeSuccDialog extends Activity {

    private static final String TAG = "XYK-SnapCouponDialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_echange_commodity_succ);
    }

    public void onViewHistoryClick(View view){
        finish();
    }
}
