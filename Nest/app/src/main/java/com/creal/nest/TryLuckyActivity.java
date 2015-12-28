package com.creal.nest;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.GetCouponsAction;
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

import java.util.List;

public class TryLuckyActivity extends ListActivity implements PullToRefreshBase.OnRefreshListener2<ListView>  {

    private static final String TAG = "XYK-MyCouponsActivity";

    private LuckyCouponsListAdapter mCouponsListAdapter;
    private LoadingSupportPTRListView mLoadingSupportPTRListView;
    private GetCouponsAction mGetCouponsAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_ptr_list);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.try_lucky);

        mLoadingSupportPTRListView = (LoadingSupportPTRListView)findViewById(R.id.refresh_widget);
        mLoadingSupportPTRListView.setOnRefreshListener(this);
        mLoadingSupportPTRListView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    public void onResume(){
        super.onResume();
        loadFirstPage(true);
    }

    private void loadFirstPage(boolean isInitialLoad){
        Log.d(TAG, "loadFirstPage");
        if(isInitialLoad)
            mLoadingSupportPTRListView.showLoadingView();
        mGetCouponsAction = new GetCouponsAction(this, 1, 10, GetCouponsAction.Type.LUCKY_COUPON);
        mGetCouponsAction.execute(
                new AbstractAction.UICallBack<Pagination<Coupon>>() {
                    public void onSuccess(Pagination<Coupon> result) {
                        mCouponsListAdapter = new LuckyCouponsListAdapter(getBaseContext(), R.layout.view_list_item_try_lucky, result.getItems());
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
        Toast.makeText(getBaseContext(), "clicked: " + position + ", enabled: " + v.isEnabled(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadFirstPage(false);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadNextPage();
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

            holder.couponThumbnail.loadImage(coupon.getImageUrl());
            holder.name.setText(coupon.getName());
            holder.desc.setText(coupon.getDesc());

            convertView.findViewById(R.id.id_btn_try_lucky).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String cardId = PreferenceUtil.getString(getBaseContext(), Constants.APP_USER_CARD_ID, null);
                    ReceiveCouponAction action = new ReceiveCouponAction(getBaseContext(), cardId, coupon.getId());
                    final Dialog progressDialog = UIUtil.showLoadingDialog(TryLuckyActivity.this, getString(R.string.loading), false);
                    action.execute(new AbstractAction.UICallBack<ReceiveCouponResult>() {
                        public void onSuccess(ReceiveCouponResult result) {
                            if(result.getReceiveId() > 0) {
                                Intent intent = new Intent(TryLuckyActivity.this, SnapCouponDialogActivity.class);
                                coupon.setValue(result.getValue());
                                intent.putExtra(SnapCouponDialogActivity.INTENT_EXTRA_COUPON, coupon);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(TryLuckyActivity.this, TryLuckyFailDialogActivity.class);
                                startActivity(intent);
                            }
                            progressDialog.dismiss();
                        }

                        public void onFailure(AbstractAction.ActionError error) {
                            progressDialog.dismiss();
                            Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(TryLuckyActivity.this, TryLuckyFailDialogActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            });

            return convertView;
        }

        class ViewHolder {
            CustomizeImageView couponThumbnail;
            TextView  name;
            TextView  desc;
        }
    }
}
