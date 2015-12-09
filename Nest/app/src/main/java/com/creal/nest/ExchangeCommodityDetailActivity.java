package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.creal.nest.views.HeaderView;

public class ExchangeCommodityDetailActivity extends Activity {

    private static final String TAG = "XYK-MyCouponsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_exchange_commodity_detail);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.commodity_detail);
    }

    public void onExchangeClick(View view){
        Intent intent = new Intent(this, ExchangeCommodityRecipientActivity.class);
        startActivity(intent);
    }

    public void onViewMoreClick(View view){

    }
}
