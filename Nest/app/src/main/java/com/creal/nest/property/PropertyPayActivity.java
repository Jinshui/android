package com.creal.nest.property;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.creal.nest.R;
import com.creal.nest.RechargeActivity;
import com.creal.nest.views.HeaderView;

public class PropertyPayActivity extends Activity {

    private static final String TAG = "XYK-LatestActivities";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_pay);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.property_pay);

    }

    public void onRechargeClick(View view) {
        Intent intent = new Intent(this, RechargeActivity.class);
        startActivity(intent);
    }

    public void onPayClick(View view) {

    }
}
