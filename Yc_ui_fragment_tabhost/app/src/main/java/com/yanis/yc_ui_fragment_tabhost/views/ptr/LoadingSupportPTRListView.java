package com.yanis.yc_ui_fragment_tabhost.views.ptr;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.yanis.yc_ui_fragment_tabhost.R;

public class LoadingSupportPTRListView extends LinearLayout implements OnRefreshListener2<ListView> {
	private final static String tag = "TT-PTRListFragment";
	private CustomizedPTRListView mListView;
	private View mLoadingView;
	private ListAdapter mListAdapter;
    private Mode mRefreshMode = Mode.BOTH;

	public LoadingSupportPTRListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.view_ptr_list_layout, this, true);
		mListView = (CustomizedPTRListView)findViewById(R.id.id_content);
		mListView.setOnRefreshListener(this);
        mListView.setVisibility(View.GONE);
        mListView.setMode(mRefreshMode);

		mLoadingView = findViewById(R.id.id_loading);
		mLoadingView.setVisibility(View.GONE);
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