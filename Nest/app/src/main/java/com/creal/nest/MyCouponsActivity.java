package com.creal.nest;

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

import com.creal.nest.actions.JSONConstants;
import com.creal.nest.util.PreferenceUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.GetCouponsAction;
import com.creal.nest.model.Coupon;
import com.creal.nest.model.Pagination;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.HeaderView;
import com.creal.nest.views.ptr.LoadingSupportPTRListView;
import com.creal.nest.views.ptr.PTRListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyCouponsActivity extends ListActivity implements PullToRefreshBase.OnRefreshListener2<ListView> {

    private static final String TAG = "XYK-MyCouponsActivity";

    private LoadingSupportPTRListView mLoadingSupportPTRListView;
    private MyCouponsListAdapter mCouponsListAdapter;
    private GetCouponsAction mGetCouponsAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_ptr_list);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.my_coupons);
        mLoadingSupportPTRListView = (LoadingSupportPTRListView)findViewById(R.id.refresh_widget);
        mLoadingSupportPTRListView.setOnRefreshListener(this);
        mLoadingSupportPTRListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        loadFirstPage(true);
    }

    private void loadFirstPage(boolean isInitialLoad){
        Log.d(TAG, "loadFirstPage");
        if(isInitialLoad)
            mLoadingSupportPTRListView.showLoadingView();
        String cardId = PreferenceUtil.getString(this, JSONConstants.KEY_CARD_ID, null);
        mGetCouponsAction = new GetCouponsAction(this, 1, 10, cardId, GetCouponsAction.Type.MY_COUPONS);
        mGetCouponsAction.execute(
            new AbstractAction.UICallBack<Pagination<Coupon>>() {
                public void onSuccess(Pagination<Coupon> result) {
                    mCouponsListAdapter = new MyCouponsListAdapter(getBaseContext(), R.layout.view_list_item_my_coupons, result.getItems());
                    setListAdapter(mCouponsListAdapter);
                    getListView().setDivider(null);
                    mLoadingSupportPTRListView.showListView();
                    mLoadingSupportPTRListView.refreshComplete();
                }

                public void onFailure(AbstractAction.ActionError error) {
                    mGetCouponsAction = mGetCouponsAction.cloneCurrentPageAction();
                    mLoadingSupportPTRListView.showListView();
                    mLoadingSupportPTRListView.refreshComplete();
                    Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this, CouponDetailActivity.class);
        intent.putExtra(CouponDetailActivity.INTENT_EXTRA_CARD_ID, PreferenceUtil.getString(this, JSONConstants.KEY_CARD_ID, null));
        intent.putExtra(CouponDetailActivity.INTENT_EXTRA_COUPON_ID, ((Coupon)getListView().getItemAtPosition(position)).getId());
        startActivity(intent);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadFirstPage(false);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadNextPage();
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
                convertView = mInflater.inflate( R.layout.view_list_item_my_coupons, parent, false);
                holder = new ViewHolder();
                holder.couponThumbnail = (CustomizeImageView) convertView.findViewById(R.id.id_coupon_thumbnail);
                holder.expireDate = (TextView) convertView.findViewById(R.id.id_txt_expire_date);
                holder.description = (TextView) convertView.findViewById(R.id.id_txt_coupon_desc);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.couponThumbnail.loadImage(coupon.getImageUrl());
            holder.description.setText(coupon.getDesc());
            holder.expireDate.setText(String.format(getString(R.string.my_coupons_expire_date), coupon.getStartTime(), coupon.getEndTime()));

            if(Build.VERSION.SDK_INT > 10) {
                convertView.findViewById(R.id.id_content).setAlpha(1f);
                if (coupon.getUseCount() > 0) {
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
