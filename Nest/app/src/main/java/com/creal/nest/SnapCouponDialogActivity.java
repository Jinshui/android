package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.creal.nest.R;
import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.GetCouponsAction;
import com.creal.nest.model.Coupon;
import com.creal.nest.model.Pagination;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.HeaderView;

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
        mAmount.setText(String.valueOf(coupon.getValue()));
    }

    public void onViewDetailClick(View view){
        Intent intent = new Intent(this, MyCouponsActivity.class);
        startActivity(intent);
        finish();
    }
}
