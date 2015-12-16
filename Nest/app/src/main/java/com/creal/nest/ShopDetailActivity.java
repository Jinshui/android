package com.creal.nest;

import android.app.Activity;
import android.os.Bundle;

import com.creal.nest.views.HeaderView;

public class ShopDetailActivity extends Activity {

    private static final String TAG = "XYK-MyCouponsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle("咖啡之巽-咖啡厅");
    }
}
