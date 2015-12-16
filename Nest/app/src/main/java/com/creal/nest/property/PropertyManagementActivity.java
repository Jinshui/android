package com.creal.nest.property;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.creal.nest.R;
import com.creal.nest.RechargeActivity;
import com.creal.nest.RechargeHistoryActivity;
import com.creal.nest.views.HeaderView;

public class PropertyManagementActivity extends Activity {

    private static final String TAG = "XYK-LatestActivities";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_management);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.property_management);

        findViewById(R.id.id_btn_property_pay).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PropertyManagementActivity.this, PropertyPayActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.id_btn_property_repair).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PropertyManagementActivity.this, PropertyRepairActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onRechargeClick(View view) {
        Intent intent = new Intent(this, RechargeActivity.class);
        startActivity(intent);
    }

    public void onPayHistoryClick(View view) {
        Intent intent = new Intent(this, RechargeHistoryActivity.class);
        startActivity(intent);
    }

    public void onRepairHistoryClick(View view) {
        Intent intent = new Intent(this, RepairsActivity.class);
        startActivity(intent);
    }
}
