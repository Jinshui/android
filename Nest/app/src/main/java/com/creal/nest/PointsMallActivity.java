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
import com.creal.nest.actions.GetLatestActivitiesAction;
import com.creal.nest.model.Commodity;
import com.creal.nest.model.Coupon;
import com.creal.nest.model.Pagination;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.HeaderView;
import com.creal.nest.views.ptr.LoadingSupportPTRListView;
import com.creal.nest.views.ptr.PTRListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

public class PointsMallActivity extends ListActivity implements PullToRefreshBase.OnRefreshListener2<ListView> {

    private static final String TAG = "XYK-LatestActivities";

    private LoadingSupportPTRListView mLoadingSupportPTRListView;
    private CommodityListAdapter mActivityListAdapter;
    private GetCommoditiesAction mGetCommoditiesAction;
    private TextView mPointTxt;
    private TextView mDescTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_ptr_list);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.points_mall);

        mLoadingSupportPTRListView = (LoadingSupportPTRListView) findViewById(R.id.refresh_widget);
        mLoadingSupportPTRListView.setPadding(10, 0, 10, 0);
        mLoadingSupportPTRListView.setOnRefreshListener(this);
        mLoadingSupportPTRListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);


        View listHeaderView = LayoutInflater.from(this).inflate(R.layout.view_list_header_points_mall, null, false);
        mLoadingSupportPTRListView.addViewToListHeader(listHeaderView, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPointTxt = (TextView) listHeaderView.findViewById(R.id.id_txt_my_points);
        mDescTxt = (TextView) listHeaderView.findViewById(R.id.id_txt_my_points_desc);
        loadFirstPage(true);
    }

    private void loadFirstPage(boolean isInitialLoad) {
        Log.d(TAG, "loadCoupons");
        if (isInitialLoad)
            mLoadingSupportPTRListView.showLoadingView();
        mGetCommoditiesAction = new GetCommoditiesAction(this, 1, 10);
        mGetCommoditiesAction.execute(
            new AbstractAction.BackgroundCallBack<Pagination<Commodity>>() {
                public void onSuccess(Pagination<Commodity> result) {
                    try { Thread.sleep(2000); } catch (InterruptedException e) {  }
                }

                public void onFailure(AbstractAction.ActionError error) {
                    try {  Thread.sleep(2000); } catch (InterruptedException e) { }
                }
            },
            new AbstractAction.UICallBack<Pagination<Commodity>>() {
                public void onSuccess(Pagination<Commodity> result) {
                    testLoadFirstPage();
                }

                public void onFailure(AbstractAction.ActionError error) {
                    mGetCommoditiesAction = mGetCommoditiesAction.cloneCurrentPageAction();
                    testLoadFirstPage();
                }
            }
        );
    }

    public void onShoppingHistoryClick(View view) {

    }

    private void testLoadFirstPage() {
        List<Commodity> coupons = new ArrayList<>();
        coupons.add(new Commodity());
        coupons.add(new Commodity());
        coupons.add(new Commodity());
        coupons.add(new Commodity());
        mActivityListAdapter = new CommodityListAdapter(getBaseContext(), R.layout.view_list_item_my_coupons, coupons);
        setListAdapter(mActivityListAdapter);
        getListView().setDivider(null);
        mLoadingSupportPTRListView.showListView();
        mLoadingSupportPTRListView.refreshComplete();
    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage");
        mGetCommoditiesAction = mGetCommoditiesAction.getNextPageAction();
        mGetCommoditiesAction.execute(
                new AbstractAction.BackgroundCallBack<Pagination<Commodity>>() {
                    public void onSuccess(Pagination<Commodity> result) {
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
                new AbstractAction.UICallBack<Pagination<Commodity>>() {
                    public void onSuccess(Pagination<Commodity> result) {
                        List<Commodity> coupons = new ArrayList<>();
                        coupons.add(new Commodity());
                        coupons.add(new Commodity());
                        mActivityListAdapter.addMore(coupons);
                        mLoadingSupportPTRListView.refreshComplete();
                    }

                    public void onFailure(AbstractAction.ActionError error) {
//                        Toast.makeText(getBaseContext(), R.string.load_failed, Toast.LENGTH_SHORT).show();
                        mGetCommoditiesAction = mGetCommoditiesAction.getPreviousPageAction();
                        Toast.makeText(getBaseContext(), "成功加载两条最新活动", Toast.LENGTH_SHORT).show();
                        //TODO: TEST CODE
                        List<Commodity> coupons = new ArrayList<>();
                        coupons.add(new Commodity());
                        coupons.add(new Commodity());
                        mActivityListAdapter.addMore(coupons);
                        mLoadingSupportPTRListView.refreshComplete();
                    }
                }
        );
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getBaseContext(), "clicked: " + position + ", enabled: " + v.isEnabled(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LatestActivityDetailActivity.class);
        com.creal.nest.model.Activity activity = new com.creal.nest.model.Activity();
        activity.setName("潮牌运动风");
        intent.putExtra("activity", activity);
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

    public class CommodityListAdapter extends PTRListAdapter<Commodity> {
        private LayoutInflater mInflater;

        public CommodityListAdapter(Context context, int resource, List<Commodity> objects) {
            super(context, resource, objects);
            mInflater = LayoutInflater.from(context);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final Commodity commodity = getItem(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.view_list_item_points_mall, parent, false);
                holder = new ViewHolder();
                holder.itemThumbnail1 = (CustomizeImageView) convertView.findViewById(R.id.id_item_thumbnail1);
                holder.name1 = (TextView) convertView.findViewById(R.id.id_item_name1);
                holder.desc1 = (TextView) convertView.findViewById(R.id.id_item_desc1);
                holder.item2 = (LinearLayout)convertView.findViewById(R.id.id_item2);
                holder.itemThumbnail2 = (CustomizeImageView) convertView.findViewById(R.id.id_item_thumbnail2);
                holder.name2 = (TextView) convertView.findViewById(R.id.id_item_name2);
                holder.desc2 = (TextView) convertView.findViewById(R.id.id_item_desc2);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            return convertView;
        }

        public int getCount() {
            if(super.getCount() == 0)
                return 0;
            return (super.getCount() + 1) / 2 ;
        }

        class ViewHolder {
            CustomizeImageView itemThumbnail1;
            TextView name1;
            TextView desc1;
            LinearLayout item2;
            CustomizeImageView itemThumbnail2;
            TextView name2;
            TextView desc2;
        }
    }
}
