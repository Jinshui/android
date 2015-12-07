package com.creal.nest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.creal.nest.R;
import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.GetCouponsAction;
import com.creal.nest.model.Coupon;
import com.creal.nest.model.Pagination;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.HeaderView;

public class SnapCouponDialogActivity extends Activity {

    private static final String TAG = "XYK-SnapCouponDialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_snap_coupons_succ);
    }

    public void onOkClick(View view){
        finish();
    }
}
