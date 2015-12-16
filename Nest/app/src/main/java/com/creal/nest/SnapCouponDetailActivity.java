package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SnapCouponDetailActivity extends Activity {

    private static final String TAG = "XYK-SnapCouponDialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_coupons_detail);
    }

    public void onSnapClick(View view){
        Intent intent = new Intent(this, SnapCouponDialogActivity.class);
        startActivity(intent);
        finish();
    }
}
