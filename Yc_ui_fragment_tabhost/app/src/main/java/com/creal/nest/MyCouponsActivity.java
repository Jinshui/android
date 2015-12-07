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

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.creal.nest.R;
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
        setContentView(R.layout.activity_simple_list);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.my_coupons);
        mLoadingSupportPTRListView = (LoadingSupportPTRListView)findViewById(R.id.refresh_widget);
        mLoadingSupportPTRListView.setOnRefreshListener(this);
        mLoadingSupportPTRListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        loadFirstPage(true);
    }

    private void loadFirstPage(boolean isInitialLoad){
        Log.d(TAG, "loadCoupons");
        if(isInitialLoad)
            mLoadingSupportPTRListView.showLoadingView();
        mGetCouponsAction = new GetCouponsAction(this, 1, 10);
        mGetCouponsAction.execute(
            new AbstractAction.BackgroundCallBack<Pagination<Coupon>>() {
                public void onSuccess(Pagination<Coupon> result) {
                    try { Thread.sleep(2000); } catch (InterruptedException e) { }
                }
                public void onFailure(AbstractAction.ActionError error){
                    try { Thread.sleep(2000); } catch (InterruptedException e) { }
                }
            },
            new AbstractAction.UICallBack<Pagination<Coupon>>() {
                public void onSuccess(Pagination<Coupon> result) {
                    List<Coupon> coupons = new ArrayList<>();
                    coupons.add(new Coupon());
                    coupons.add(new Coupon());
                    coupons.add(new Coupon());
                    coupons.add(new Coupon(true));
                    coupons.add(new Coupon(true));
                    coupons.add(new Coupon(true));
                    mCouponsListAdapter = new MyCouponsListAdapter(getBaseContext(), R.layout.view_list_item_my_coupons, coupons);
                    setListAdapter(mCouponsListAdapter);
                    getListView().setDivider(null);
                    mLoadingSupportPTRListView.showListView();
                    mLoadingSupportPTRListView.refreshComplete();
                }

                public void onFailure(AbstractAction.ActionError error) {
                    mGetCouponsAction = (GetCouponsAction)mGetCouponsAction.cloneCurrentPageAction();
                    List<Coupon> coupons = new ArrayList<>();
                    coupons.add(new Coupon());
                    coupons.add(new Coupon());
                    coupons.add(new Coupon());
                    coupons.add(new Coupon(true));
                    coupons.add(new Coupon(true));
                    coupons.add(new Coupon(true));
                    mCouponsListAdapter = new MyCouponsListAdapter(getBaseContext(), R.layout.view_list_item_my_coupons, coupons);
                    setListAdapter(mCouponsListAdapter);
                    getListView().setDivider(null);
                    mLoadingSupportPTRListView.showListView();
                    mLoadingSupportPTRListView.refreshComplete();
                }
            }
        );
    }

    private void loadNextPage(){
        Log.d(TAG, "loadNextPage");
        mGetCouponsAction = mGetCouponsAction.getNextPageAction();
        mGetCouponsAction.execute(
            new AbstractAction.BackgroundCallBack<Pagination<Coupon>>() {
                public void onSuccess(Pagination<Coupon> result) {
                    try { Thread.sleep(2000); } catch (InterruptedException e) { }
                }
                public void onFailure(AbstractAction.ActionError error){
                    try { Thread.sleep(2000); } catch (InterruptedException e) { }
                }
            },
            new AbstractAction.UICallBack<Pagination<Coupon>>() {
                public void onSuccess(Pagination<Coupon> result) {
                    List<Coupon> coupons = new ArrayList<>();
                    coupons.add(new Coupon());
                    coupons.add(new Coupon());
                    mCouponsListAdapter.addMore(coupons);
                    mLoadingSupportPTRListView.refreshComplete();
                }

                public void onFailure(AbstractAction.ActionError error) {
//                        Toast.makeText(getBaseContext(), R.string.load_failed, Toast.LENGTH_SHORT).show();
                    mGetCouponsAction = mGetCouponsAction.getPreviousPageAction();
                    Toast.makeText(getBaseContext(), "成功加载两条新优惠券。", Toast.LENGTH_SHORT).show();
                    //TODO: TEST CODE
                    List<Coupon> coupons = new ArrayList<>();
                    coupons.add(new Coupon());
                    coupons.add(new Coupon());
                    mCouponsListAdapter.addMore(coupons);
                    mLoadingSupportPTRListView.refreshComplete();
                }
            }
        );
    }

    protected void onListItemClick(ListView l, View v, int position, long id){
        Toast.makeText(getBaseContext(), "clicked: " + position + ", enabled: " + v.isEnabled(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CouponDetailActivity.class);
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
