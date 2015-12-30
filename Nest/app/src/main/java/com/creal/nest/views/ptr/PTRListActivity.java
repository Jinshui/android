package com.creal.nest.views.ptr;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.creal.nest.R;
import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.PaginationAction;
import com.creal.nest.model.Pagination;
import com.creal.nest.util.ErrorAdapter;
import com.creal.nest.views.HeaderView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.List;

public abstract class PTRListActivity<Result> extends ListActivity implements PullToRefreshBase.OnRefreshListener2<ListView> {

    private static final String TAG = "XYK-PTRListActivity";

    private LoadingSupportPTRListView mLoadingSupportPTRListView;
    private ContentListAdapter mPtrListAdapter;
    private PaginationAction<Result> mAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_ptr_list);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(getTitleResId());
        mLoadingSupportPTRListView = (LoadingSupportPTRListView)findViewById(R.id.refresh_widget);
        mLoadingSupportPTRListView.setOnRefreshListener(this);
        mLoadingSupportPTRListView.setMode(getPTRMode());
        if(!initOnResume())
            loadFirstPage(true);
    }

    public void onResume(){
        super.onResume();
        if(initOnResume())
            loadFirstPage(true);
    }

    public LoadingSupportPTRListView getLoadingSupportPTRListView(){
        return mLoadingSupportPTRListView;
    }

    protected boolean initOnResume(){
        return false;
    }

    private void loadFirstPage(boolean isInitialLoad){
        Log.d(TAG, "loadFirstPage");
        if(isInitialLoad)
            mLoadingSupportPTRListView.showLoadingView();
        mAction = getPaginationAction();
        mAction.execute(
                new AbstractAction.UICallBack<Pagination<Result>>() {
                    public void onSuccess(Pagination<Result> result) {
                        mPtrListAdapter = new ContentListAdapter(getBaseContext(), result.getItems());
                        setListAdapter(mPtrListAdapter);
                        getListView().setDivider(null);
                        mLoadingSupportPTRListView.showListView();
                        mLoadingSupportPTRListView.refreshComplete();
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        mAction = mAction.cloneCurrentPageAction();
                        getListView().getLayoutParams().height = AbsListView.LayoutParams.MATCH_PARENT;
                        setListAdapter(getErrorAdapter());
                        getListView().setDivider(null);
                        mLoadingSupportPTRListView.showListView();
                        mLoadingSupportPTRListView.refreshComplete();
                    }
                }
        );
    }

    private void loadNextPage(){
        Log.d(TAG, "loadNextPage");
        mAction = mAction.getNextPageAction();
        mAction.execute(
            new AbstractAction.UICallBack<Pagination<Result>>() {
                public void onSuccess(Pagination<Result> result) {
                    if(result.getItems().isEmpty()){
                        Toast.makeText(getBaseContext(), R.string.load_done, Toast.LENGTH_SHORT).show();
                        mAction = mAction.getPreviousPageAction();
                    }else {
                        mPtrListAdapter.addMore(result.getItems());
                    }
                    mLoadingSupportPTRListView.refreshComplete();
                }

                public void onFailure(AbstractAction.ActionError error) {
                    Toast.makeText(getBaseContext(), getLoadNextPageError(error), Toast.LENGTH_SHORT).show();
                    mAction = mAction.getPreviousPageAction();
                    mLoadingSupportPTRListView.refreshComplete();
                }
            }
        );
    }

    public String getLoadNextPageError(AbstractAction.ActionError error){
        return getString(R.string.load_failed);
    }

    protected void onListItemClick(ListView l, View v, int position, long id){
        if ( getListAdapter() instanceof ErrorAdapter){
            loadFirstPage(true);
            return;
        }

        Result result = (Result)getListView().getItemAtPosition(position);
        showDetailActivity(result);
    }

    public PullToRefreshBase.Mode getPTRMode(){
        return PullToRefreshBase.Mode.BOTH;
    }

    public abstract PaginationAction<Result> getPaginationAction();

    public abstract View getListItemView(Result item, View convertView, ViewGroup parent, LayoutInflater inflater);

    public abstract int getTitleResId();

    public void showDetailActivity(Result result){ }

    public ListAdapter getErrorAdapter(){
        return new ErrorAdapter(getBaseContext());
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadFirstPage(false);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadNextPage();
    }

    public class ContentListAdapter extends PTRListAdapter<Result> {
        private LayoutInflater mInflater;
        public ContentListAdapter(Context context, List<Result> objects) {
            super(context, 0, objects);
            mInflater = LayoutInflater.from(context);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final Result itemObject = getItem(position);
            return getListItemView(itemObject, convertView, parent, mInflater);
        }
    }
}