package com.creal.nest;

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

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.GetCouponsAction;
import com.creal.nest.model.Coupon;
import com.creal.nest.model.Pagination;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.HeaderView;
import com.creal.nest.views.ptr.PTRListAdapter;

import java.util.ArrayList;
import java.util.List;

public class TryLuckyActivity extends ListActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "XYK-MyCouponsActivity";

    private SwipeRefreshLayout mSwipeRefreshWidget;

    private LuckyCouponsListAdapter mCouponsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_swipe_list);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.try_lucky);

        mSwipeRefreshWidget = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshWidget.setProgressViewOffset(false, 0, 100);

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon());
        coupons.add(new Coupon());
        coupons.add(new Coupon());
        coupons.add(new Coupon(true, false));
        coupons.add(new Coupon(true, false));
        coupons.add(new Coupon(true, false));
        mCouponsListAdapter = new LuckyCouponsListAdapter(this, R.layout.view_list_item_try_lucky, coupons);
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
                        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
                    }
                    public void onFailure(AbstractAction.ActionError error){
                        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
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
    }

    public class LuckyCouponsListAdapter extends PTRListAdapter<Coupon> {
        private LayoutInflater mInflater;
        public LuckyCouponsListAdapter(Context context, int resource, List<Coupon> objects) {
            super(context, resource, objects);
            mInflater = LayoutInflater.from(context);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final Coupon coupon = getItem(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate( R.layout.view_list_item_try_lucky, parent, false);
                holder = new ViewHolder();
                holder.couponThumbnail = (CustomizeImageView) convertView.findViewById(R.id.id_coupon_thumbnail);
                holder.name = (TextView) convertView.findViewById(R.id.id_txt_lucky_coupons_name);
                holder.desc = (TextView) convertView.findViewById(R.id.id_txt_lucky_coupons_desc);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if(coupon.isStarted() && !coupon.isExpired()) {
                convertView.findViewById(R.id.id_btn_try_lucky).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                    Intent intent = new Intent(TryLuckyActivity.this, SnapCouponDialogActivity.class);
                    startActivity(intent);
                    }
                });
            }

            return convertView;
        }

        class ViewHolder {
            CustomizeImageView couponThumbnail;
            TextView  name;
            TextView  desc;
        }
    }
}
