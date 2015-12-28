package com.creal.nest;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.GetExchangePointAction;
import com.creal.nest.model.ExchangePoint;
import com.creal.nest.model.Pagination;
import com.creal.nest.views.HeaderView;
import com.creal.nest.views.ptr.LoadingSupportPTRListView;
import com.creal.nest.views.ptr.PTRListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

public class ExchangePointHistoryActivity extends ListActivity implements PullToRefreshBase.OnRefreshListener2<ListView> {

    private static final String TAG = "XYK-MyCouponsActivity";

    private LoadingSupportPTRListView mLoadingSupportPTRListView;
    private RechargeListAdapter mCouponsListAdapter;
    private GetExchangePointAction mGetExchangePointAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_ptr_list);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.points_exchange_history);

        mLoadingSupportPTRListView = (LoadingSupportPTRListView) findViewById(R.id.refresh_widget);
        mLoadingSupportPTRListView.setOnRefreshListener(this);
        mLoadingSupportPTRListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        loadFirstPage(true);
    }

    private void loadFirstPage(boolean isInitialLoad) {
        Log.d(TAG, "loadCoupons");
        if (isInitialLoad)
            mLoadingSupportPTRListView.showLoadingView();
        mGetExchangePointAction = new GetExchangePointAction(this, 1, 10);
        mGetExchangePointAction.execute(
                new AbstractAction.BackgroundCallBack<Pagination<ExchangePoint>>() {
                    public void onSuccess(Pagination<ExchangePoint> result) {
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
                new AbstractAction.UICallBack<Pagination<ExchangePoint>>() {
                    public void onSuccess(Pagination<ExchangePoint> result) {
                        List<ExchangePoint> coupons = new ArrayList<>();
                        coupons.add(new ExchangePoint());
                        coupons.add(new ExchangePoint());
                        coupons.add(new ExchangePoint());
                        mCouponsListAdapter = new RechargeListAdapter(getBaseContext(), R.layout.view_list_item_exchange_history, coupons);
                        setListAdapter(mCouponsListAdapter);
                        getListView().setDivider(null);
                        mLoadingSupportPTRListView.showListView();
                        mLoadingSupportPTRListView.refreshComplete();
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        mGetExchangePointAction = mGetExchangePointAction.cloneCurrentPageAction();
                        List<ExchangePoint> coupons = new ArrayList<>();
                        coupons.add(new ExchangePoint());
                        coupons.add(new ExchangePoint());
                        coupons.add(new ExchangePoint());
                        mCouponsListAdapter = new RechargeListAdapter(getBaseContext(), R.layout.view_list_item_exchange_history, coupons);
                        setListAdapter(mCouponsListAdapter);
                        getListView().setDivider(null);
                        mLoadingSupportPTRListView.showListView();
                        mLoadingSupportPTRListView.refreshComplete();
                    }
                }
        );
    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage");
        mGetExchangePointAction = mGetExchangePointAction.getNextPageAction();
        mGetExchangePointAction.execute(
                new AbstractAction.BackgroundCallBack<Pagination<ExchangePoint>>() {
                    public void onSuccess(Pagination<ExchangePoint> result) {
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
                new AbstractAction.UICallBack<Pagination<ExchangePoint>>() {
                    public void onSuccess(Pagination<ExchangePoint> result) {
                        List<ExchangePoint> coupons = new ArrayList<>();
                        coupons.add(new ExchangePoint());
                        coupons.add(new ExchangePoint());
                        mCouponsListAdapter.addMore(coupons);
                        mLoadingSupportPTRListView.refreshComplete();
                    }

                    public void onFailure(AbstractAction.ActionError error) {
//                        Toast.makeText(getBaseContext(), R.string.load_failed, Toast.LENGTH_SHORT).show();
                        mGetExchangePointAction = mGetExchangePointAction.getPreviousPageAction();
                        Toast.makeText(getBaseContext(), "成功加载两条新优惠券。", Toast.LENGTH_SHORT).show();
                        //TODO: TEST CODE
                        List<ExchangePoint> coupons = new ArrayList<>();
                        coupons.add(new ExchangePoint());
                        coupons.add(new ExchangePoint());
                        mCouponsListAdapter.addMore(coupons);
                        mLoadingSupportPTRListView.refreshComplete();
                    }
                }
        );
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadFirstPage(false);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadNextPage();
    }

    public class RechargeListAdapter extends PTRListAdapter<ExchangePoint> {
        private LayoutInflater mInflater;

        public RechargeListAdapter(Context context, int resource, List<ExchangePoint> objects) {
            super(context, resource, objects);
            mInflater = LayoutInflater.from(context);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final ExchangePoint coupon = getItem(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.view_list_item_exchange_history, parent, false);
                holder = new ViewHolder();
                holder.amount = (TextView) convertView.findViewById(R.id.id_banlance_recharge_amount);
                holder.time = (TextView) convertView.findViewById(R.id.id_banlance_recharge_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            return convertView;
        }

        class ViewHolder {
            TextView amount;
            TextView time;
        }
    }
}
