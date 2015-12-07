package com.yanis.yc_ui_fragment_tabhost;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yanis.yc_ui_fragment_tabhost.actions.AbstractAction;
import com.yanis.yc_ui_fragment_tabhost.actions.GetCouponsAction;
import com.yanis.yc_ui_fragment_tabhost.model.Coupon;
import com.yanis.yc_ui_fragment_tabhost.model.Pagination;
import com.yanis.yc_ui_fragment_tabhost.views.CustomizeImageView;
import com.yanis.yc_ui_fragment_tabhost.views.HeaderView;
import com.yanis.yc_ui_fragment_tabhost.views.ptr.PTRListAdapter;

import java.util.ArrayList;
import java.util.List;

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
