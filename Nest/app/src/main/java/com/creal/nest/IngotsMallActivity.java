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
import com.creal.nest.actions.GetCommoditiesAction;
import com.creal.nest.actions.GetIngotsAction;
import com.creal.nest.model.Ingot;
import com.creal.nest.model.Pagination;
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
    private TextView mPointTxt;
    private TextView mDescTxt;

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
        mLoadingSupportPTRListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);


        View listHeaderView = LayoutInflater.from(this).inflate(R.layout.view_list_header_ingots_mall, null, false);
        mLoadingSupportPTRListView.addViewToListHeader(listHeaderView, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPointTxt = (TextView) listHeaderView.findViewById(R.id.id_txt_my_points);
        mDescTxt = (TextView) listHeaderView.findViewById(R.id.id_txt_my_points_desc);
        loadFirstPage(true);
    }

    private void loadFirstPage(boolean isInitialLoad) {
        Log.d(TAG, "loadCoupons");
        if (isInitialLoad)
            mLoadingSupportPTRListView.showLoadingView();
        mGetIngotsAction = new GetIngotsAction(this, 1, 10);
        mGetIngotsAction.execute(
                new AbstractAction.BackgroundCallBack<Pagination<Ingot>>() {
                    public void onSuccess(Pagination<Ingot> result) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                        }
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                        }
                    }
                },
                new AbstractAction.UICallBack<Pagination<Ingot>>() {
                    public void onSuccess(Pagination<Ingot> result) {
                        testLoadFirstPage();
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        mGetIngotsAction = mGetIngotsAction.cloneCurrentPageAction();
                        testLoadFirstPage();
                    }
                }
        );
    }

    protected void onListItemClick(ListView l, View v, int position, long id){
        Toast.makeText(this, position + " item was clicked", Toast.LENGTH_LONG).show();
    }

    public void onShoppingHistoryClick(View view) {

    }

    private void testLoadFirstPage() {
        List<Ingot> coupons = new ArrayList<>();
        coupons.add(new Ingot());
        coupons.add(new Ingot());
        coupons.add(new Ingot());
        coupons.add(new Ingot());
        mActivityListAdapter = new IngotListAdapter(getBaseContext(), R.layout.view_list_item_my_coupons, coupons);
        setListAdapter(mActivityListAdapter);
        getListView().setDivider(null);
        mLoadingSupportPTRListView.showListView();
        mLoadingSupportPTRListView.refreshComplete();
    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage");
        mGetIngotsAction = mGetIngotsAction.getNextPageAction();
        mGetIngotsAction.execute(
                new AbstractAction.BackgroundCallBack<Pagination<Ingot>>() {
                    public void onSuccess(Pagination<Ingot> result) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                        }
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                        }
                    }
                },
                new AbstractAction.UICallBack<Pagination<Ingot>>() {
                    public void onSuccess(Pagination<Ingot> result) {
                        List<Ingot> coupons = new ArrayList<>();
                        coupons.add(new Ingot());
                        coupons.add(new Ingot());
                        mActivityListAdapter.addMore(coupons);
                        mLoadingSupportPTRListView.refreshComplete();
                    }

                    public void onFailure(AbstractAction.ActionError error) {
//                        Toast.makeText(getBaseContext(), R.string.load_failed, Toast.LENGTH_SHORT).show();
                        mGetIngotsAction = mGetIngotsAction.getPreviousPageAction();
                        Toast.makeText(getBaseContext(), "成功加载两条最新活动", Toast.LENGTH_SHORT).show();
                        //TODO: TEST CODE
                        List<Ingot> coupons = new ArrayList<>();
                        coupons.add(new Ingot());
                        coupons.add(new Ingot());
                        mActivityListAdapter.addMore(coupons);
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

    public class IngotListAdapter extends PTRListAdapter<Ingot> {
        private LayoutInflater mInflater;

        public IngotListAdapter(Context context, int resource, List<Ingot> objects) {
            super(context, resource, objects);
            mInflater = LayoutInflater.from(context);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final Ingot Ingot = getItem(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.view_list_item_ingots_mall, parent, false);
                holder = new ViewHolder();
                holder.itemThumbnail1 = (CustomizeImageView) convertView.findViewById(R.id.id_ingot_thumbnail);
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
                    Intent intent = new Intent(IngotsMallActivity.this, ExchangeSuccDialog.class);
                    intent.putExtra("message", "您兑换的1000元宝已经兑换成功。");
                    startActivity(intent);
                }
            });
            return convertView;
        }

        class ViewHolder {
            CustomizeImageView itemThumbnail1;
            TextView name;
            TextView amount;
            TextView info;
            TextView exechange;
        }
    }
}
