package com.creal.nest;

import android.app.Activity;
import android.os.Bundle;

import com.creal.nest.R;
import com.creal.nest.views.HeaderView;

public class LatestActivityDetailActivity extends Activity {

    private static final String TAG = "XYK-MyCouponsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_activity_detail);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.coupon_detail);
    }
}
