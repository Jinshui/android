package com.creal.nest;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.GetCommoditiesAction;
import com.creal.nest.actions.GetIngotsAction;
import com.creal.nest.actions.GetMyIngotsAction;
import com.creal.nest.actions.JSONConstants;
import com.creal.nest.model.Coupon;
import com.creal.nest.model.Ingot;
import com.creal.nest.model.Pagination;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.HeaderView;
import com.creal.nest.views.ptr.LoadingSupportPTRListView;
import com.creal.nest.views.ptr.PTRListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

public class IngotsMallActivity extends ListActivity implements PullToRefreshBase.OnRefreshListener2<ListView> {

    private static final String TAG = "XYK-LatestActivities";

    private LoadingSupportPTRListView mLoadingSupportPTRListView;
    private IngotListAdapter mActivityListAdapter;
    private GetIngotsAction mGetIngotsAction;
    private TextView mIngotsTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_ptr_list);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.ingots_mall_title);
        headerView.setRightText(R.string.ingots_mall_help);
        headerView.setRightTextListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: what is ingots mall
            }
        });

        mLoadingSupportPTRListView = (LoadingSupportPTRListView) findViewById(R.id.refresh_widget);
        mLoadingSupportPTRListView.setPadding(10, 0, 10, 0);
        mLoadingSupportPTRListView.setOnRefreshListener(this);
        mLoadingSupportPTRListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        View listHeaderView = LayoutInflater.from(this).inflate(R.layout.view_list_header_ingots_mall, null, false);
        mLoadingSupportPTRListView.addViewToListHeader(listHeaderView, LinearLayout.LayoutParams.WRAP_CONTENT);
        mIngotsTxt = (TextView) listHeaderView.findViewById(R.id.id_txt_my_points);
    }

    public void onResume(){
        super.onResume();
        loadFirstPage(true);
    }

    private void loadFirstPage(boolean isInitialLoad) {
        Log.d(TAG, "loadFirstPage");
        if (isInitialLoad)
            mLoadingSupportPTRListView.showLoadingView();
        mGetIngotsAction = new GetIngotsAction(this, 1, 10);
        mGetIngotsAction.execute(
                new AbstractAction.UICallBack<Pagination<Ingot>>() {
                    public void onSuccess(Pagination<Ingot> result) {
                        mActivityListAdapter = new IngotListAdapter(getBaseContext(), R.layout.view_list_item_my_coupons, result.getItems());
                        setListAdapter(mActivityListAdapter);
                        getListView().setDivider(null);
                        mLoadingSupportPTRListView.showListView();
                        mLoadingSupportPTRListView.refreshComplete();
                        loadMyIngots();
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        mGetIngotsAction = mGetIngotsAction.cloneCurrentPageAction();
                        setListAdapter(new ErrorAdapter(getBaseContext(), R.layout.view_list_item_error));
                        getListView().setDivider(null);
                        mLoadingSupportPTRListView.showListView();
                        mLoadingSupportPTRListView.refreshComplete();
                        Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        loadMyIngots();
                    }
                }
        );
    }

    private void loadMyIngots(){
        PreferenceUtil.getString(this, JSONConstants.KEY_CARD_ID, null);
        GetMyIngotsAction getMyIngotsAction = new GetMyIngotsAction(this, PreferenceUtil.getString(this, JSONConstants.KEY_CARD_ID, null));
        getMyIngotsAction.execute(new AbstractAction.UICallBack<String>() {
            public void onSuccess(String result) {
                mIngotsTxt.setText(result);
            }

            public void onFailure(AbstractAction.ActionError error) {
                mIngotsTxt.setText("加载失败，请下拉刷新");
            }
        });
    }

    protected void onListItemClick(ListView l, View v, int position, long id){
    }

    public void onShoppingHistoryClick(View view) {
        Intent intent = new Intent(this, ShoppingHistoryActivity.class);
        startActivity(intent);
    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage");
        mGetIngotsAction = mGetIngotsAction.getNextPageAction();
        mGetIngotsAction.execute(
                new AbstractAction.UICallBack<Pagination<Ingot>>() {
                    public void onSuccess(Pagination<Ingot> result) {
                        if(result.getItems().isEmpty()){
                            Toast.makeText(getBaseContext(), R.string.load_done, Toast.LENGTH_SHORT).show();
                        }else {
                            mActivityListAdapter.addMore(result.getItems());
                        }
                        mLoadingSupportPTRListView.refreshComplete();
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        Toast.makeText(getBaseContext(), R.string.load_failed, Toast.LENGTH_SHORT).show();
                        mGetIngotsAction = mGetIngotsAction.getPreviousPageAction();
                        mLoadingSupportPTRListView.refreshComplete();
                    }
                }
        );
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadFirstPage(false);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadNextPage();
    }

    public class ErrorAdapter extends ArrayAdapter<String>{
        public ErrorAdapter(Context context, int resource) {
            super(context, resource, new String[]{"nothing"});
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            return LayoutInflater.from(getBaseContext()).inflate(R.layout.view_list_item_error, parent, false);
        }
    }

    public class IngotListAdapter extends PTRListAdapter<Ingot> {
        private LayoutInflater mInflater;

        public IngotListAdapter(Context context, int resource, List<Ingot> objects) {
            super(context, resource, objects);
            mInflater = LayoutInflater.from(context);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final Ingot ingot = getItem(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.view_list_item_ingots_mall, parent, false);
                holder = new ViewHolder();
                holder.img = (TextView) convertView.findViewById(R.id.id_ingot_thumbnail);
                holder.name = (TextView) convertView.findViewById(R.id.id_ingot_title);
                holder.amount = (TextView) convertView.findViewById(R.id.id_ingot_amount);
                holder.info = (TextView)convertView.findViewById(R.id.id_ingot_info);
                holder.exechange = (TextView)convertView.findViewById(R.id.id_ingot_exchange);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.exechange.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(IngotsMallActivity.this, ExchangeIngotsConfirmDialog.class);
                    startActivity(intent);
                }
            });
            holder.img.setText(String.valueOf(ingot.getAmount()));
            holder.name.setText(ingot.getName());
            holder.amount.setText(String.format(getString(R.string.ingots_num), ingot.getNum()));
            holder.info.setText(ingot.getDesc());
            return convertView;
        }


        class ViewHolder {
            TextView img;
            TextView name;
            TextView amount;
            TextView info;
            TextView exechange;
        }
    }
}
