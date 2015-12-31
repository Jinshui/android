package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.creal.nest.model.Coupon;

public class SnapCouponDialogActivity extends Activity {

    private static final String TAG = "XYK-SnapCouponDialog";
    public static final String INTENT_EXTRA_COUPON = "coupon";
    private TextView mAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_snap_coupons_succ);
        Coupon coupon = getIntent().getParcelableExtra(INTENT_EXTRA_COUPON);
        mAmount = (TextView)findViewById(R.id.id_txt_amount);
        mAmount.setText(coupon.getValue() > 0 ? String.valueOf(coupon.getValue()) : "");
    }

    public void onViewDetailClick(View view){
        Intent intent = new Intent(this, MyCouponsActivity.class);
        startActivity(intent);
        finish();
    }
}
