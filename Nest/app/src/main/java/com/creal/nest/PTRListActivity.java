package com.creal.nest;

import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.creal.nest.actions.PaginationAction;

public class PTRListActivity<T extends PaginationAction> implements PullToRefreshBase.OnRefreshListener2<ListView> {

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//        loadFirstPage(false);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//        loadNextPage();
    }


}
