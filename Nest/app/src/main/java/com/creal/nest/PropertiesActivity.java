package com.creal.nest;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.GetIngotsAction;
import com.creal.nest.model.Ingot;
import com.creal.nest.model.Pagination;
import com.creal.nest.property.RepairsActivity;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.HeaderView;
import com.creal.nest.views.ptr.LoadingSupportPTRListView;
import com.creal.nest.views.ptr.PTRListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

public class PropertiesActivity extends Activity {

    private static final String TAG = "XYK-LatestActivities";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties_management);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.property_management);

        findViewById(R.id.id_btn_property_pay).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PropertiesActivity.this, PropertiesPayActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.id_btn_property_repair).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PropertiesActivity.this, PropertiesRepairActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onRechargeClick(View view) {
        Intent intent = new Intent(this, RechargeActivity.class);
        startActivity(intent);
    }

    public void onPayHistoryClick(View view) {
        Intent intent = new Intent(this, RepairsActivity.class);
        startActivity(intent);
    }

    public void onRepairHistoryClick(View view) {
        Intent intent = new Intent(this, RechargeActivity.class);
        startActivity(intent);
    }
}
