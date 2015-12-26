package com.creal.nest;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.GetCouponDetailAction;
import com.creal.nest.actions.GetCouponsAction;
import com.creal.nest.actions.JSONConstants;
import com.creal.nest.actions.ReceiveCouponAction;
import com.creal.nest.model.Coupon;
import com.creal.nest.model.Pagination;
import com.creal.nest.model.ReceiveCouponResult;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.UIUtil;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.HeaderView;
import com.creal.nest.views.ptr.LoadingSupportPTRListView;
import com.creal.nest.views.ptr.PTRListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

public class SnapupCouponsActivity extends ListActivity implements PullToRefreshBase.OnRefreshListener2<ListView> {

    private static final String TAG = "XYK-MyCouponsActivity";

    private LoadingSupportPTRListView mLoadingSupportPTRListView;
    private SnapCouponsListAdapter mCouponsListAdapter;
    private GetCouponsAction mGetCouponsAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_ptr_list);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.snapup_coupons);

        mLoadingSupportPTRListView = (LoadingSupportPTRListView) findViewById(R.id.refresh_widget);
        mLoadingSupportPTRListView.setOnRefreshListener(this);
        mLoadingSupportPTRListView.setMode(PullToRefreshBase.Mode.BOTH);

//        mSwipeRefreshWidget = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_widget);
//        mSwipeRefreshWidget.setOnRefreshListener(this);
//        mSwipeRefreshWidget.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
//        mSwipeRefreshWidget.setProgressViewOffset(false, 0, 100);
    }

    public void onResume(){
        super.onResume();
        loadFirstPage(true);
    }

    private void loadFirstPage(boolean isInitialLoad) {
        Log.d(TAG, "loadCoupons");
        if (isInitialLoad)
            mLoadingSupportPTRListView.showLoadingView();
        mGetCouponsAction = new GetCouponsAction(this, 1, 10, GetCouponsAction.Type.SNAP_COUPONS);
        mGetCouponsAction.execute(
            new AbstractAction.UICallBack<Pagination<Coupon>>() {
                public void onSuccess(Pagination<Coupon> result) {
                    mCouponsListAdapter = new SnapCouponsListAdapter(SnapupCouponsActivity.this, R.layout.view_list_item_snap_coupons, result.getItems());
                    setListAdapter(mCouponsListAdapter);
                    getListView().setDivider(null);
                    mLoadingSupportPTRListView.showListView();
                    mLoadingSupportPTRListView.refreshComplete();
                }

                public void onFailure(AbstractAction.ActionError error) {
                    mGetCouponsAction = mGetCouponsAction.cloneCurrentPageAction();
                    mLoadingSupportPTRListView.showListView();
                    mLoadingSupportPTRListView.refreshComplete();
                    Toast.makeText(SnapupCouponsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        );
    }

    private void loadNextPage(){
        Log.d(TAG, "loadNextPage");
        mGetCouponsAction = mGetCouponsAction.getNextPageAction();
        mGetCouponsAction.execute(
            new AbstractAction.UICallBack<Pagination<Coupon>>() {
                public void onSuccess(Pagination<Coupon> result) {
                    if(result.getItems().isEmpty()){
                        Toast.makeText(getBaseContext(), R.string.load_done, Toast.LENGTH_SHORT).show();
                    }else {
                        mCouponsListAdapter.addMore(result.getItems());
                    }
                    mLoadingSupportPTRListView.refreshComplete();
                }

                public void onFailure(AbstractAction.ActionError error) {
                    Toast.makeText(getBaseContext(), R.string.load_failed, Toast.LENGTH_SHORT).show();
                    mGetCouponsAction = mGetCouponsAction.getPreviousPageAction();
                    mLoadingSupportPTRListView.refreshComplete();
                }
            }
        );
    }

    protected void onListItemClick(ListView l, View v, int position, long id){
        Coupon coupon = (Coupon)getListView().getItemAtPosition(position);
        if(!coupon.isActive())
            return;
        final Dialog progressDialog = UIUtil.showLoadingDialog(this, getString(R.string.loading), false);
        GetCouponDetailAction getCouponDetailAction = new GetCouponDetailAction(this, coupon.getId());
        getCouponDetailAction.execute(new AbstractAction.UICallBack<Coupon>() {
            public void onSuccess(Coupon result) {
                Intent intent = new Intent(SnapupCouponsActivity.this, SnapCouponDetailActivity.class);
                intent.putExtra("coupon", result);
                startActivity(intent);
                progressDialog.dismiss();
            }

            public void onFailure(AbstractAction.ActionError error) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadFirstPage(false);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadNextPage();
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
                convertView = mInflater.inflate( R.layout.view_list_item_snap_coupons, parent, false);
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

            holder.couponThumbnail.loadImage(coupon.getImageUrl());
            holder.total.setText(String.format(getString(R.string.snapup_coupons_total), coupon.getTotal()));
            holder.remaining.setText(String.format(getString(R.string.snapup_coupons_remaining), coupon.getRest()));
            holder.name.setText(coupon.getName());
            holder.time.setText(coupon.getTime());

            if(coupon.isActive()) {
                holder.snapNowBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String cardId = PreferenceUtil.getString(SnapupCouponsActivity.this, JSONConstants.KEY_CARD_ID, null);
                        final Dialog progressDialog = UIUtil.showLoadingDialog(SnapupCouponsActivity.this, getString(R.string.loading), false);
                        ReceiveCouponAction action = new ReceiveCouponAction(SnapupCouponsActivity.this, cardId, coupon.getId());
                        action.execute(new AbstractAction.UICallBack<ReceiveCouponResult>() {
                            public void onSuccess(ReceiveCouponResult result) {
                                Intent intent = new Intent(SnapupCouponsActivity.this, SnapCouponDialogActivity.class);
                                coupon.setValue(result.getValue());
                                intent.putExtra(SnapCouponDialogActivity.INTENT_EXTRA_COUPON, coupon);
                                startActivity(intent);
                                progressDialog.dismiss();
                            }
                            public void onFailure(AbstractAction.ActionError error) {
                                progressDialog.dismiss();
                                Toast.makeText(SnapupCouponsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }

            if(Build.VERSION.SDK_INT > 10) {
                convertView.findViewById(R.id.id_header).setAlpha(1f);
                convertView.findViewById(R.id.id_body).setAlpha(1f);
                if (!coupon.isActive()) {
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
