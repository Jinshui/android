package com.creal.nest.property;

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

import com.creal.nest.Constants;
import com.creal.nest.R;
import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.CommonPaginationAction;
import com.creal.nest.actions.GetRepairsAction;
import com.creal.nest.model.Pagination;
import com.creal.nest.model.Repair;
import com.creal.nest.util.ErrorAdapter;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.UIUtil;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.HeaderView;
import com.creal.nest.views.ptr.LoadingSupportPTRListView;
import com.creal.nest.views.ptr.PTRListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

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
        String cardId = PreferenceUtil.getString(this, Constants.APP_USER_CARD_ID, null);
        final Dialog dialog = UIUtil.showLoadingDialog(this, getString(R.string.loading), true);
        mGetRepairsAction = new GetRepairsAction(this, 1, 10, cardId);
        mGetRepairsAction.execute(
                new AbstractAction.UICallBack<Pagination<Repair>>() {
                    public void onSuccess(Pagination<Repair> result) {
                        mActivityListAdapter = new RepairsListAdapter(getBaseContext(), R.layout.view_list_item_repair_report, result.getItems());
                        setListAdapter(mActivityListAdapter);
                        getListView().setDivider(null);
                        mLoadingSupportPTRListView.showListView();
                        mLoadingSupportPTRListView.refreshComplete();
                        dialog.dismiss();
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        mGetRepairsAction = mGetRepairsAction.cloneCurrentPageAction();
                        setListAdapter(new ErrorAdapter(getBaseContext(), R.layout.view_list_item_error));
                        getListView().setDivider(null);
                        mLoadingSupportPTRListView.showListView();
                        mLoadingSupportPTRListView.refreshComplete();
                        Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
        );
    }

    private void loadNextPage(){
        Log.d(TAG, "loadNextPage");
        mGetRepairsAction = mGetRepairsAction.getNextPageAction();
        mGetRepairsAction.execute(
            new AbstractAction.UICallBack<Pagination<Repair>>() {
                public void onSuccess(Pagination<Repair> result) {
                    if(result.getItems().isEmpty()){
                        Toast.makeText(getBaseContext(), R.string.load_done, Toast.LENGTH_SHORT).show();
                    }else {
                        mActivityListAdapter.addMore(result.getItems());
                    }
                    mLoadingSupportPTRListView.refreshComplete();
                }

                public void onFailure(AbstractAction.ActionError error) {
                    Toast.makeText(getBaseContext(), R.string.load_failed, Toast.LENGTH_SHORT).show();
                    mGetRepairsAction = mGetRepairsAction.getPreviousPageAction();
                    mLoadingSupportPTRListView.refreshComplete();
                }
            }
        );
    }

    protected void onListItemClick(ListView l, View v, int position, long id){
        Toast.makeText(getBaseContext(), "clicked: " + position + ", enabled: " + v.isEnabled(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, RepairDetailActivity.class);
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
                holder.name = (TextView)convertView.findViewById(R.id.id_txt_repair_name);
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

            holder.name.setText(repair.getTitle());
            holder.reportTime.setText(repair.getTime());
            holder.finishedTime.setText(repair.getTime());
            return convertView;
        }

        class ViewHolder {
            CustomizeImageView repairThumbnail;
            TextView name;
            TextView finishedTime;
            TextView reportTime;
        }
    }
}
