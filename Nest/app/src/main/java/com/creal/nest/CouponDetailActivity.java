package com.creal.nest;

import android.app.Activity;
import android.os.Bundle;

import com.creal.nest.R;
import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.GetCouponsAction;
import com.creal.nest.model.Coupon;
import com.creal.nest.model.Pagination;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.HeaderView;

public class CouponDetailActivity extends Activity {

    private static final String TAG = "XYK-MyCouponsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_detail);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.coupon_detail);
    }
}
