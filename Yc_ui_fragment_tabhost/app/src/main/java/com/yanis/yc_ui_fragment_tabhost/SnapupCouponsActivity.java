package com.yanis.yc_ui_fragment_tabhost;

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

public class SnapupCouponsActivity extends ListActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "XYK-MyCouponsActivity";

    private SwipeRefreshLayout mSwipeRefreshWidget;

    private SnapCouponsListAdapter mCouponsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snapup_coupons);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.snapup_coupons);

        mSwipeRefreshWidget = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshWidget.setProgressViewOffset(false, 0, 100);

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon());
        coupons.add(new Coupon());
        coupons.add(new Coupon());
        coupons.add(new Coupon(false, false));
        coupons.add(new Coupon(false, false));
        coupons.add(new Coupon(false, false));
        mCouponsListAdapter = new SnapCouponsListAdapter(this, R.layout.view_my_coupons_list_item, coupons);
        setListAdapter(mCouponsListAdapter);
        getListView().setDivider(null);
    }

    public void onRefresh() {
        loadCoupons();
    }

    private void loadCoupons(){
        Log.d(TAG, "loadCoupons");
        GetCouponsAction getCouponsAction = new GetCouponsAction(this, 1, 10);
        getCouponsAction.execute(
                new AbstractAction.BackgroundCallBack<Pagination<Coupon>>() {
                    public void onSuccess(Pagination<Coupon> result) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new AbstractAction.UICallBack<Pagination<Coupon>>() {
                    public void onSuccess(Pagination<Coupon> result) {
                        List<Coupon> coupons = new ArrayList<>();
                        coupons.add(new Coupon());
                        coupons.add(new Coupon());
                        mCouponsListAdapter.addMoreToTop(coupons);
                        mSwipeRefreshWidget.setRefreshing(false);
                    }

                    public void onFailure(AbstractAction.ActionError error) {
//                        Toast.makeText(getBaseContext(), R.string.load_failed, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getBaseContext(), "成功加载两条新优惠券。", Toast.LENGTH_SHORT).show();
                        //TODO: TEST CODE
                        List<Coupon> coupons = new ArrayList<>();
                        coupons.add(new Coupon());
                        coupons.add(new Coupon());
                        mCouponsListAdapter.addMoreToTop(coupons);
                        mSwipeRefreshWidget.setRefreshing(false);
                    }
                }
        );
    }

    protected void onListItemClick(ListView l, View v, int position, long id){
        Toast.makeText(getBaseContext(), "clicked: " + position + ", enabled: " + v.isEnabled(), Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, CouponDetailActivity.class);
//        startActivity(intent);
    }

    public class SnapCouponsListAdapter extends PTRListAdapter<Coupon> {
        private LayoutInflater mInflater;
        public SnapCouponsListAdapter(Context context, int resource, List<Coupon> objects) {
            super(context, resource, objects);
            mInflater = LayoutInflater.from(context);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final Coupon coupon = getItem(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate( R.layout.view_snap_coupons_list_item, parent, false);
                holder = new ViewHolder();
                holder.couponThumbnail = (CustomizeImageView) convertView.findViewById(R.id.id_coupon_thumbnail);
                holder.total = (TextView) convertView.findViewById(R.id.id_txt_snapup_coupons_total);
                holder.remaining = (TextView) convertView.findViewById(R.id.id_txt_snapup_coupons_remaining);
                holder.name = (TextView) convertView.findViewById(R.id.id_txt_snapup_coupons_name);
                holder.snapNowBtn = (TextView) convertView.findViewById(R.id.id_coupon_snap_now);
                holder.time = (TextView) convertView.findViewById(R.id.id_coupon_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if(coupon.isStarted() && !coupon.isExpired()) {
                convertView.findViewById(R.id.id_coupon_snap_now).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(SnapupCouponsActivity.this, SnapCouponDialogActivity.class);
                        startActivity(intent);
                    }
                });
            }

            if(Build.VERSION.SDK_INT > 10) {
                convertView.findViewById(R.id.id_header).setAlpha(1f);
                convertView.findViewById(R.id.id_body).setAlpha(1f);
                if (!coupon.isStarted()) {
                    convertView.findViewById(R.id.id_header).setAlpha(0.3f);
                    convertView.findViewById(R.id.id_body).setAlpha(0.3f);
                }
            }

            return convertView;
        }

        class ViewHolder {
            CustomizeImageView couponThumbnail;
            TextView  total;
            TextView  remaining;
            TextView  name;
            TextView  snapNowBtn;
            TextView  time;
        }
    }
}
