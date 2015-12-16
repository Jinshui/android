package com.creal.nest;

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
import com.creal.nest.actions.GetShopAction;
import com.creal.nest.model.Recharge;
import com.creal.nest.model.Shop;
import com.creal.nest.model.Pagination;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.ptr.LoadingSupportPTRListView;
import com.creal.nest.views.ptr.PTRListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends ListActivity implements PullToRefreshBase.OnRefreshListener2<ListView> {

    private static final String TAG = "XYK-IngotsExchangeAct";

    private View mSearchHistoryPanel;
    private LoadingSupportPTRListView mLoadingSupportPTRListView;
    private ShopArrayAdapter mShopListAdapter;
    private GetShopAction mGetShopAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mSearchHistoryPanel = findViewById(R.id.id_search_history);
        mLoadingSupportPTRListView = (LoadingSupportPTRListView)findViewById(R.id.refresh_widget);
        mLoadingSupportPTRListView.setOnRefreshListener(this);
        mLoadingSupportPTRListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
    }

    public void onSearchClick(View view){
        Log.d(TAG, "onSearchClick");
        mSearchHistoryPanel.setVisibility(View.INVISIBLE);
        mLoadingSupportPTRListView.setVisibility(View.VISIBLE);
        mLoadingSupportPTRListView.showLoadingView();
        mGetShopAction = new GetShopAction(this, "", 1, Constants.PAGE_SIZE);
        mGetShopAction.execute(
            new AbstractAction.BackgroundCallBack<Pagination<Shop>>() {
                public void onSuccess(Pagination<Shop> result) {
                    try { Thread.sleep(1000); } catch (InterruptedException e) { }
                }
                public void onFailure(AbstractAction.ActionError error){
                    try { Thread.sleep(1000); } catch (InterruptedException e) { }
                }
            },
            new AbstractAction.UICallBack<Pagination<Shop>>() {
                public void onSuccess(Pagination<Shop> result) {
                    List<Shop> coupons = new ArrayList<>();
                    coupons.add(new Shop());
                    coupons.add(new Shop());
                    coupons.add(new Shop());
                    coupons.add(new Shop());
                    coupons.add(new Shop());
                    coupons.add(new Shop());
                    coupons.add(new Shop());
                    coupons.add(new Shop());
                    mShopListAdapter = new ShopArrayAdapter(getBaseContext(), R.layout.view_list_item_ingots_exchange_history, coupons);
                    setListAdapter(mShopListAdapter);
                    getListView().setDivider(null);
                    mLoadingSupportPTRListView.showListView();
                    mLoadingSupportPTRListView.refreshComplete();
                }

                public void onFailure(AbstractAction.ActionError error) {
                    mGetShopAction = mGetShopAction.cloneCurrentPageAction();
                    List<Shop> coupons = new ArrayList<>();
                    coupons.add(new Shop());
                    coupons.add(new Shop());
                    coupons.add(new Shop());
                    mShopListAdapter = new ShopArrayAdapter(getBaseContext(), R.layout.view_list_item_ingots_exchange_history, coupons);
                    setListAdapter(mShopListAdapter);
                    getListView().setDivider(null);
                    mLoadingSupportPTRListView.showListView();
                    mLoadingSupportPTRListView.refreshComplete();
                }
            }
        );
    }

    private void loadNextPage(){
        Log.d(TAG, "loadNextPage");
        mGetShopAction = mGetShopAction.getNextPageAction();
        mGetShopAction.execute(
                new AbstractAction.BackgroundCallBack<Pagination<Shop>>() {
                    public void onSuccess(Pagination<Shop> result) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                    }
                },
                new AbstractAction.UICallBack<Pagination<Shop>>() {
                    public void onSuccess(Pagination<Shop> shopList) {
                        List<Shop> coupons = new ArrayList<>();
                        coupons.add(new Shop());
                        coupons.add(new Shop());
                        mShopListAdapter.addMore(coupons);
                        mLoadingSupportPTRListView.refreshComplete();
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        Toast.makeText(SearchActivity.this, R.string.load_failed, Toast.LENGTH_SHORT).show();
                        mGetShopAction = (GetShopAction) mGetShopAction.getPreviousPageAction();
                        mGetShopAction = mGetShopAction.getPreviousPageAction();
                        Toast.makeText(getBaseContext(), "成功加载两条新优惠券。", Toast.LENGTH_SHORT).show();
                        //TODO: TEST CODE
                        List<Shop> coupons = new ArrayList<>();
                        coupons.add(new Shop());
                        coupons.add(new Shop());
                        mShopListAdapter.addMore(coupons);
                        mLoadingSupportPTRListView.refreshComplete();
                    }
                }
        );

    }

    public void onBackClick(View view){
        finish();
    }

    public void onCleanClick(View view){

    }

    protected void onListItemClick(ListView l, View v, int position, long id){
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadNextPage();
    }

    public class ShopArrayAdapter extends PTRListAdapter<Shop> {
        private LayoutInflater mInflater;
        public ShopArrayAdapter(Context context, int res, List<Shop> items) {
            super(context, res, items);
            mInflater = LayoutInflater.from(context);
        }

        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            final Shop shop = getItem(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate( R.layout.view_list_item_shop, parent, false);
                holder = new ViewHolder();
                holder.newsThumbnail = (CustomizeImageView) convertView.findViewById(R.id.id_shop_thumbnail);
                holder.newsTitle = (TextView) convertView.findViewById(R.id.id_shop_title);
                holder.newsVideoSign = convertView.findViewById(R.id.id_nearby_item_address);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            convertView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    Intent showNewsDetailIntent = new Intent(getContext(), ShopDetailActivity.class);
                    startActivity(showNewsDetailIntent);
                }
            });

            return convertView;
        }

        class ViewHolder {
            CustomizeImageView newsThumbnail;
            TextView  newsTitle;
            View newsVideoSign;
            View newsSpecialSign;
        }
    }
}