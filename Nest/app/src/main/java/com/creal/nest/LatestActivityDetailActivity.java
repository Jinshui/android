package com.creal.nest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.creal.nest.model.SalesActivity;
import com.creal.nest.views.HeaderView;

public class LatestActivityDetailActivity extends Activity {

    private static final String TAG = "XYK-LatestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_activity_detail);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();

        SalesActivity salesActivity = getIntent().getParcelableExtra("salesActivity");
//        headerView.setTitle(salesActivity.getName());
    }

    public void onViewMoreClick(View view){
        Toast.makeText(this, "view more clicked", Toast.LENGTH_SHORT).show();
    }
}