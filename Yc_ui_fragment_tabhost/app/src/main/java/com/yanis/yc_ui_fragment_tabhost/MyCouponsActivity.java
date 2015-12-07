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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yanis.yc_ui_fragment_tabhost.actions.AbstractAction;
import com.yanis.yc_ui_fragment_tabhost.actions.GetCategoryAction;
import com.yanis.yc_ui_fragment_tabhost.actions.GetCouponsAction;
import com.yanis.yc_ui_fragment_tabhost.model.Coupon;
import com.yanis.yc_ui_fragment_tabhost.model.Pagination;
import com.yanis.yc_ui_fragment_tabhost.views.CustomizeImageView;
import com.yanis.yc_ui_fragment_tabhost.views.HeaderView;
import com.yanis.yc_ui_fragment_tabhost.views.ptr.PTRListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyCouponsActivity extends ListActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "XYK-MyCouponsActivity";

    private SwipeRefreshLayout mSwipeRefreshWidget;

    private MyCouponsListAdapter mCouponsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coupons);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.my_coupons);

        mSwipeRefreshWidget = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshWidget.setProgressViewOffset(false, 0, 100);

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon());
        coupons.add(new Coupon());
        coupons.add(new Coupon());
        coupons.add(new Coupon(true, true));
        coupons.add(new Coupon(true, true));
        coupons.add(new Coupon(true, true));
        mCouponsListAdapter = new MyCouponsListAdapter(this, R.layout.view_my_coupons_list_item, coupons);
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
        Intent intent = new Intent(this, CouponDetailActivity.class);
        startActivity(intent);
    }

    public class MyCouponsListAdapter extends PTRListAdapter<Coupon> {
        private LayoutInflater mInflater;
        public MyCouponsListAdapter(Context context, int resource, List<Coupon> objects) {
            super(context, resource, objects);
            mInflater = LayoutInflater.from(context);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final Coupon coupon = getItem(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate( R.layout.view_my_coupons_list_item, parent, false);
                holder = new ViewHolder();
                holder.couponThumbnail = (CustomizeImageView) convertView.findViewById(R.id.id_coupon_thumbnail);
                holder.expireDate = (TextView) convertView.findViewById(R.id.id_txt_expire_date);
                holder.description = (TextView) convertView.findViewById(R.id.id_txt_coupon_desc);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if(Build.VERSION.SDK_INT > 10) {
                convertView.findViewById(R.id.id_content).setAlpha(1f);
                if (coupon.isExpired()) {
                    convertView.findViewById(R.id.id_content).setAlpha(0.3f);
                }
            }

            return convertView;
        }

        class ViewHolder {
            CustomizeImageView couponThumbnail;
            TextView  expireDate;
            TextView  description;
        }
    }
}
