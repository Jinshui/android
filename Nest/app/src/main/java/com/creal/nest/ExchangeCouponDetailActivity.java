package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.creal.nest.views.HeaderView;

public class ExchangeCouponDetailActivity extends Activity {

    private static final String TAG = "XYK-MyCouponsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_exchange_coupon_detail);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.coupons_detail);
    }

    public void onExchangeClick(View view){
        Intent intent = new Intent(this, ExchangeSuccDialog.class);
        intent.putExtra("from", "points_mall");
        intent.putExtra("btnText", getString(R.string.back_to_points_mall));
        intent.putExtra("message", getString(R.string.exchange_coupons_succ_desc));
        startActivity(intent);
    }
}