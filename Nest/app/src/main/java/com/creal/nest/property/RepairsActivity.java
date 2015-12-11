package com.creal.nest.property;

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

import com.creal.nest.LatestActivityDetailActivity;
import com.creal.nest.R;
import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.GetRepairsAction;
import com.creal.nest.model.Repair;
import com.creal.nest.model.Pagination;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.HeaderView;
import com.creal.nest.views.ptr.LoadingSupportPTRListView;
import com.creal.nest.views.ptr.PTRListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

public class RepairsActivity extends ListActivity implements PullToRefreshBase.OnRefreshListener2<ListView> {

    private static final String TAG = "XYK-LatestActivities";

    private LoadingSupportPTRListView mLoadingSupportPTRListView;
    private RepairsListAdapter mActivityListAdapter;
    private GetRepairsAction mGetRepairsAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_ptr_list);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.property_repair_report_list);

        mLoadingSupportPTRListView = (LoadingSupportPTRListView)findViewById(R.id.refresh_widget);
        mLoadingSupportPTRListView.setOnRefreshListener(this);
        mLoadingSupportPTRListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        loadFirstPage(true);
    }

    private void loadFirstPage(boolean isInitialLoad){
        Log.d(TAG, "loadCoupons");
        if(isInitialLoad)
            mLoadingSupportPTRListView.showLoadingView();
        mGetRepairsAction = new GetRepairsAction(this, 1, 10);
        mGetRepairsAction.execute(
                new AbstractAction.BackgroundCallBack<Pagination<Repair>>() {
                    public void onSuccess(Pagination<Repair> result) {
                        try { Thread.sleep(2000); } catch (InterruptedException e) { }
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        try { Thread.sleep(2000); } catch (InterruptedException e) {  }
                    }
                },
                new AbstractAction.UICallBack<Pagination<Repair>>() {
                    public void onSuccess(Pagination<Repair> result) {
                        testLoadFirstPage();
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        mGetRepairsAction = mGetRepairsAction.cloneCurrentPageAction();
                        testLoadFirstPage();
                    }
                }
        );
    }


    private void testLoadFirstPage(){
        List<Repair> repairs = new ArrayList<>();
        repairs.add(new Repair());
        repairs.add(new Repair());
        repairs.add(new Repair());
        repairs.add(new Repair(true));
        repairs.add(new Repair(true));
        repairs.add(new Repair(true));
        mActivityListAdapter = new RepairsListAdapter(getBaseContext(), R.layout.view_list_item_repair_report, repairs);
        setListAdapter(mActivityListAdapter);
        getListView().setDivider(null);
        mLoadingSupportPTRListView.showListView();
        mLoadingSupportPTRListView.refreshComplete();
    }

    private void loadNextPage(){
        Log.d(TAG, "loadNextPage");
        mGetRepairsAction = mGetRepairsAction.getNextPageAction();
        mGetRepairsAction.execute(
            new AbstractAction.BackgroundCallBack<Pagination<Repair>>() {
                public void onSuccess(Pagination<Repair> result) {
                    try { Thread.sleep(2000); } catch (InterruptedException e) { }
                }
                public void onFailure(AbstractAction.ActionError error){
                    try { Thread.sleep(2000); } catch (InterruptedException e) { }
                }
            },
            new AbstractAction.UICallBack<Pagination<Repair>>() {
                public void onSuccess(Pagination<Repair> result) {
                    List<Repair> coupons = new ArrayList<>();
                    coupons.add(new Repair());
                    coupons.add(new Repair());
                    mActivityListAdapter.addMore(coupons);
                    mLoadingSupportPTRListView.refreshComplete();
                }

                public void onFailure(AbstractAction.ActionError error) {
//                        Toast.makeText(getBaseContext(), R.string.load_failed, Toast.LENGTH_SHORT).show();
                    mGetRepairsAction = mGetRepairsAction.getPreviousPageAction();
                    Toast.makeText(getBaseContext(), "成功加载两条最新活动", Toast.LENGTH_SHORT).show();
                    //TODO: TEST CODE
                    List<Repair> coupons = new ArrayList<>();
                    coupons.add(new Repair());
                    coupons.add(new Repair());
                    mActivityListAdapter.addMore(coupons);
                    mLoadingSupportPTRListView.refreshComplete();
                }
            }
        );
    }

    protected void onListItemClick(ListView l, View v, int position, long id){
        Toast.makeText(getBaseContext(), "clicked: " + position + ", enabled: " + v.isEnabled(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, RepairDetailActivity.class);
        Repair repair = new Repair();
        repair.setName("潮牌运动风");

        repair.getStepList().add(new Repair.Step("维修中...", "2015.6.11 16:20"));
        repair.getStepList().add(new Repair.Step("维修师傅返回准备所需物料", "2015.6.11 16:10"));
        repair.getStepList().add(new Repair.Step("已经安排物业维修师傅上门检查损坏情况", "2015.6.11 15:40"));
        repair.getStepList().add(new Repair.Step("您的报修信息已经开始.", "2015.6.11 15:36"));

        intent.putExtra("repair", repair);
        startActivity(intent);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) { loadFirstPage(false); }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadNextPage();
    }

    public class RepairsListAdapter extends PTRListAdapter<Repair> {
        private LayoutInflater mInflater;
        public RepairsListAdapter(Context context, int resource, List<Repair> objects) {
            super(context, resource, objects);
            mInflater = LayoutInflater.from(context);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final Repair repair = getItem(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate( R.layout.view_list_item_repair_report, parent, false);
                holder = new ViewHolder();
                holder.repairThumbnail = (CustomizeImageView) convertView.findViewById(R.id.id_report_thumbnail);
                holder.finishedTime = (TextView) convertView.findViewById(R.id.id_txt_repair_finish_time);
                holder.reportTime = (TextView) convertView.findViewById(R.id.id_txt_repair_report_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if(repair.isDone()){
                holder.repairThumbnail.setBackgroundResource(R.drawable.ic_property_repair_report2);
                convertView.findViewById(R.id.id_finish_section).setVisibility(View.VISIBLE);
            }else{
                holder.repairThumbnail.setBackgroundResource(R.drawable.ic_property_repair_report);
                convertView.findViewById(R.id.id_finish_section).setVisibility(View.INVISIBLE);
            }

            return convertView;
        }

        class ViewHolder {
            CustomizeImageView repairThumbnail;
            TextView  finishedTime;
            TextView  reportTime;
        }
    }
}
