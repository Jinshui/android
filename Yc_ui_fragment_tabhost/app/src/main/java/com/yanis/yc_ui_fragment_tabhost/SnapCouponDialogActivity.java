package com.yanis.yc_ui_fragment_tabhost;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

public class SnapCouponDialogActivity extends Activity {

    private static final String TAG = "XYK-SnapCouponDialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_snap_coupons_succ);
    }

    public void onOkClick(View view){
        finish();
    }
}
