package com.creal.nest.views.ptr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.creal.nest.R;

public abstract class HeaderLoadingSupportPTRListFragment extends Fragment implements OnRefreshListener2<ListView> {
	private final static String tag = "TT-PTRListFragment";
	public static class ViewHolder{
		public View headerView;
		public int height;
	}
	private View mLayout;
	private CustomizedPTRListView mListView;
	private View mLoadingView;
	private ListAdapter mListAdapter;
    private Mode mRefreshMode = Mode.BOTH;

	public HeaderLoadingSupportPTRListFragment() {
	}

	/**
	 * set the refresh mode: START or END
	 * @param mode
	 */
	public void setMode(Mode mode){
        if(mode != null)
            mRefreshMode = mode;
	}

	public void setAdapter(ListAdapter adapter){
		mListAdapter = adapter;
		mListView.setAdapter(adapter);
	}

	public ListAdapter getAdapter(){
		return mListAdapter;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(tag, "onCreateView");
		mLayout = inflater.inflate(R.layout.view_ptr_list_layout, container, false);
		mListView = (CustomizedPTRListView) mLayout.findViewById(R.id.id_content);
		ViewHolder holder = createHeaderView(inflater);
		if(holder != null && holder.headerView != null){
			mListView.addViewToListHeader(holder.headerView, holder.height);
		}
		mListView.setOnRefreshListener(this);
		mLoadingView = mLayout.findViewById(R.id.id_loading);
		//hide both before any data is available
		mListView.setVisibility(View.GONE);
        mListView.setMode(mRefreshMode);
		mLoadingView.setVisibility(View.GONE);
		return mLayout;
	}
	
	/**
	 * Override this method to create your customized view that you want to add to the header of the pull-to-refresh list view
	 */
	public abstract ViewHolder createHeaderView(LayoutInflater inflater);
	
	protected final void showLoadingView() {
		mLoadingView.setVisibility(View.VISIBLE);
		mListView.setVisibility(View.GONE);
	}

	protected final void showListView() {
		mLoadingView.setVisibility(View.GONE);
		mListView.setVisibility(View.VISIBLE);
	}
	
	protected final void refreshComplete(){
		mListView.onRefreshComplete();
	}


	/**
	 * Sub class should implement this to do tha actual refresh data for view
	 * @param refreshView
	 */
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {}

	/**
	 * Sub class should implement this to do tha actual refresh data for view
	 * @param refreshView
	 */
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {}
}