package com.creal.nest.balance;

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

import com.creal.nest.R;
import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.GetAccumulationsAction;
import com.creal.nest.model.AccumulationItem;
import com.creal.nest.model.Pagination;
import com.creal.nest.views.HeaderView;
import com.creal.nest.views.ptr.LoadingSupportPTRListView;
import com.creal.nest.views.ptr.PTRListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

public class AccumulationHistoryActivity extends ListActivity implements PullToRefreshBase.OnRefreshListener2<ListView> {

    private static final String TAG = "XYK-LatestActivities";

    private LoadingSupportPTRListView mLoadingSupportPTRListView;
    private AccumulationItemsListAdapter mActivityListAdapter;
    private GetAccumulationsAction mGetAccumulationsAction;
    private String mAccumulationType = ACCUMULATION_TYPE_POINTS;
    public static final String ACCUMULATION_TYPE = "accumulation_type";
    public static final String ACCUMULATION_TYPE_INGOTS = "ingots";
    public static final String ACCUMULATION_TYPE_POINTS = "points";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_ptr_list);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        mAccumulationType = getIntent().getStringExtra(ACCUMULATION_TYPE);
        headerView.setTitle(ACCUMULATION_TYPE_INGOTS.equals(mAccumulationType) ? R.string.ingots_accumulation_history : R.string.points_accumulation_history);

        mLoadingSupportPTRListView = (LoadingSupportPTRListView)findViewById(R.id.refresh_widget);
        mLoadingSupportPTRListView.setOnRefreshListener(this);
        mLoadingSupportPTRListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);

        loadFirstPage(true);
    }

    private void loadFirstPage(boolean isInitialLoad){
        Log.d(TAG, "loadCoupons");
        if(isInitialLoad)
            mLoadingSupportPTRListView.showLoadingView();
        mGetAccumulationsAction = new GetAccumulationsAction(this, 1, 10);
        mGetAccumulationsAction.execute(
                new AbstractAction.BackgroundCallBack<Pagination<AccumulationItem>>() {
                    public void onSuccess(Pagination<AccumulationItem> result) {
                        try { Thread.sleep(2000); } catch (InterruptedException e) { }
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        try { Thread.sleep(2000); } catch (InterruptedException e) {  }
                    }
                },
                new AbstractAction.UICallBack<Pagination<AccumulationItem>>() {
                    public void onSuccess(Pagination<AccumulationItem> result) {
                        testLoadFirstPage();
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        mGetAccumulationsAction = mGetAccumulationsAction.cloneCurrentPageAction();
                        testLoadFirstPage();
                    }
                }
        );
    }

    private void testLoadFirstPage(){
        List<AccumulationItem> repairs = new ArrayList<>();
        repairs.add(new AccumulationItem());
        repairs.add(new AccumulationItem());
        repairs.add(new AccumulationItem());
        repairs.add(new AccumulationItem());
        repairs.add(new AccumulationItem());
        repairs.add(new AccumulationItem());
        mActivityListAdapter = new AccumulationItemsListAdapter(getBaseContext(), R.layout.view_list_item_accumulation_history, repairs);
        setListAdapter(mActivityListAdapter);
        getListView().setDivider(null);
        mLoadingSupportPTRListView.showListView();
        mLoadingSupportPTRListView.refreshComplete();
    }

    private void loadNextPage(){
        Log.d(TAG, "loadNextPage");
        mGetAccumulationsAction = mGetAccumulationsAction.getNextPageAction();
        mGetAccumulationsAction.execute(
            new AbstractAction.BackgroundCallBack<Pagination<AccumulationItem>>() {
                public void onSuccess(Pagination<AccumulationItem> result) {
                    try { Thread.sleep(2000); } catch (InterruptedException e) { }
                }
                public void onFailure(AbstractAction.ActionError error){
                    try { Thread.sleep(2000); } catch (InterruptedException e) { }
                }
            },
            new AbstractAction.UICallBack<Pagination<AccumulationItem>>() {
                public void onSuccess(Pagination<AccumulationItem> result) {
                    List<AccumulationItem> coupons = new ArrayList<>();
                    coupons.add(new AccumulationItem());
                    coupons.add(new AccumulationItem());
                    mActivityListAdapter.addMore(coupons);
                    mLoadingSupportPTRListView.refreshComplete();
                }

                public void onFailure(AbstractAction.ActionError error) {
//                        Toast.makeText(getBaseContext(), R.string.load_failed, Toast.LENGTH_SHORT).show();
                    mGetAccumulationsAction = mGetAccumulationsAction.getPreviousPageAction();
                    Toast.makeText(getBaseContext(), "成功加载两条最新活动", Toast.LENGTH_SHORT).show();
                    //TODO: TEST CODE
                    List<AccumulationItem> coupons = new ArrayList<>();
                    coupons.add(new AccumulationItem());
                    coupons.add(new AccumulationItem());
                    mActivityListAdapter.addMore(coupons);
                    mLoadingSupportPTRListView.refreshComplete();
                }
            }
        );
    }

    protected void onListItemClick(ListView l, View v, int position, long id){
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) { loadFirstPage(false); }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadNextPage();
    }

    public class AccumulationItemsListAdapter extends PTRListAdapter<AccumulationItem> {
        private LayoutInflater mInflater;
        public AccumulationItemsListAdapter(Context context, int resource, List<AccumulationItem> objects) {
            super(context, resource, objects);
            mInflater = LayoutInflater.from(context);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final AccumulationItem repair = getItem(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate( R.layout.view_list_item_accumulation_history, parent, false);
                holder = new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.id_txt_item_title);
                holder.amount = (TextView) convertView.findViewById(R.id.id_txt_item_amount);
                holder.time = (TextView) convertView.findViewById(R.id.id_txt_item_time);
                holder.desc = (TextView) convertView.findViewById(R.id.id_txt_item_desc);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if(ACCUMULATION_TYPE_INGOTS.equals(mAccumulationType)){
                holder.amount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.balance_my_ingots, 0, 0, 0);
            }else {
                holder.amount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.balance_my_points, 0, 0, 0);
            }
            return convertView;
        }

        class ViewHolder {
            TextView  title;
            TextView  amount;
            TextView  time;
            TextView  desc;
        }
    }
}
