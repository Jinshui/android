package com.creal.nest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.GetShopListAction;
import com.creal.nest.actions.ParallelTask;
import com.creal.nest.model.Pagination;
import com.creal.nest.model.Shop;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.ptr.PTRListAdapter;

import java.util.Collections;
import java.util.List;

public class FragmentTestShopList extends ListFragment implements SwipeRefreshLayout.OnRefreshListener {
    private final static String tag = "TT-FragmentTestShopList";

    private String mCategory;
    private PTRListAdapter<Shop> mShopListAdapter;
    private List<Shop> mShopList;
    private boolean mShopLoadedFromServer = false;
    private GetShopListAction mGetShopListAction;
    private SwipeRefreshLayout mSwipeRefreshWidget;

    private int mAsyncTaskCount = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(tag, mCategory + " onCreate()");
        super.onCreate(savedInstanceState);
        mCategory = getArguments().getString("category");
        if (savedInstanceState != null) {
            mCategory = savedInstanceState.getString("mCategory");
            mShopLoadedFromServer = savedInstanceState.getBoolean("mShopLoaded");
        }
//		mNewsDAO = ((TouTiaoApp)getActivity().getApplication()).getNewsDAO();
//		mHeadNewsDAO = ((TouTiaoApp)getActivity().getApplication()).getHeadNewsDAO();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(tag, mCategory + " onCreateView()");
//        View root = super.onCreateView(inflater, container, savedInstanceState);
//        View listView = root.findViewById(android.R.id.list);
        View v = inflater.inflate(R.layout.test_swipe_refresh_list, container, false);
        mSwipeRefreshWidget = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshWidget.setProgressViewOffset(false, 0, 100);
        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(tag, mCategory + " onActivityCreated()");
        mAsyncTaskCount = 0;
//		setListShown(false);
        if (!mShopLoadedFromServer)
            loadShopFromServer();
        else
            loadShopFromDB();
    }

    private void loadShopFromServer() {
        mSwipeRefreshWidget.setRefreshing(true);
        mAsyncTaskCount++;
        Log.d(tag, mCategory + " loadShopFromServer(): tasks: " + mAsyncTaskCount);
        mGetShopListAction = new GetShopListAction(getActivity(), mCategory, 0, 0, 1, Constants.PAGE_SIZE);
        mGetShopListAction.execute(
                new AbstractAction.BackgroundCallBack<Pagination<Shop>>() {
                    public void onSuccess(Pagination<Shop> newsPage) {
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                        }
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                        }
                    }
                },
                new AbstractAction.UICallBack<Pagination<Shop>>() {
                    public void onSuccess(Pagination<Shop> newsList) {
                        mShopLoadedFromServer = true;
                        if (isDetached() || getActivity() == null) //DO NOT update the view if this fragment is detached from the activity.
                            return;
                        if (mShopListAdapter == null) {
                            mShopListAdapter = new ShopArrayAdapter(getActivity(), R.layout.view_list_item_shop, newsList.getItems());
                            setListAdapter(mShopListAdapter);
                        } else {
                            if (newsList.getItems().isEmpty()) {
                                Toast.makeText(getActivity(), R.string.no_more_to_load, Toast.LENGTH_SHORT).show();
                            } else {
                                mShopListAdapter.clear();
                                mShopListAdapter.addMore(newsList.getItems());
                            }
                        }
                        afterLoadReturned();
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        loadShopFromDB();
                        afterLoadReturned();
                    }
                }
        );
    }

    private void loadShopFromDB() {
        mSwipeRefreshWidget.setRefreshing(true);
        mAsyncTaskCount++;
        Log.d(tag, mCategory + " loadShopFromDB(): tasks: " + mAsyncTaskCount);
        new ParallelTask<List<Shop>>() {
            protected List<Shop> doInBackground(Void... params) {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                }
                return Collections.emptyList();
            }

            public void onPostExecute(List<Shop> shopList) {
                //The pager is viewing another page now.
                if (getActivity() == null)
                    return;

                if (mShopListAdapter == null) {
                    mShopListAdapter = new ShopArrayAdapter(getActivity(), R.layout.view_list_item_shop, shopList);
                    mGetShopListAction = new GetShopListAction(getActivity(), mCategory, 0, 0, shopList.size(), Constants.PAGE_SIZE);
                    setListAdapter(mShopListAdapter);
                } else {
                    mShopListAdapter.clear();
                    mShopListAdapter.addMore(shopList);
                }
                afterLoadReturned();
            }
        }.execute();
    }

    @Override
    public void onRefresh() {
        Log.d(tag, mCategory + " onRefresh");
        onPullUpToRefresh();
    }

    public void onPullUpToRefresh() {
        mSwipeRefreshWidget.setRefreshing(true);
        mAsyncTaskCount++;
        Log.d(tag, mCategory + " onPullUpToRefresh(): tasks: " + mAsyncTaskCount);
        mGetShopListAction = (GetShopListAction) mGetShopListAction.getNextPageAction();
        mGetShopListAction.execute(
                new AbstractAction.BackgroundCallBack<Pagination<Shop>>() {
                    public void onSuccess(Pagination<Shop> newsPage) {
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                        }
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                        }
                    }
                },
                new AbstractAction.UICallBack<Pagination<Shop>>() {
                    public void onSuccess(Pagination<Shop> shopList) {
                        if (isDetached() || getActivity() == null) //DO NOT update the view if this fragment is detached from the activity.
                            return;
                        if (mShopListAdapter == null) {
                            mShopListAdapter = new ShopArrayAdapter(getActivity(), R.layout.view_list_item_shop, shopList.getItems());
                            setListAdapter(mShopListAdapter);
                        } else {
                            if (shopList.getItems().isEmpty()) {
                                Toast.makeText(getActivity(), R.string.no_more_to_load, Toast.LENGTH_SHORT).show();
                            } else {
                                mShopListAdapter.addMore(shopList.getItems());
                            }
                        }
                        afterLoadReturned();
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        if (isDetached() || getActivity() == null) {//DO NOT update the view if this fragment is detached from the activity.
                        } else {
                            Toast.makeText(getActivity(), R.string.load_failed, Toast.LENGTH_SHORT).show();
                        }
                        mGetShopListAction = (GetShopListAction) mGetShopListAction.getPreviousPageAction();
                        afterLoadReturned();
                    }
                }
        );
    }

    private void afterLoadReturned() {
        mAsyncTaskCount--;
        Log.d(tag, mCategory + " afterLoadReturned(): tasks: " + mAsyncTaskCount);
        if (mAsyncTaskCount == 0) {
//			setListShownNoAnimation(true);
            mSwipeRefreshWidget.setRefreshing(false);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(tag, mCategory + " onSaveInstanceState");
        outState.putString("mCategory", mCategory);
        outState.putBoolean("mShopLoaded", mShopLoadedFromServer);
    }

    private static void showShop(Context context, Shop shop) {
        Intent showNewsDetailIntent = new Intent();
        context.startActivity(showNewsDetailIntent);
    }

    public static class ShopArrayAdapter extends PTRListAdapter<Shop> {
        private LayoutInflater mInflater;

        public ShopArrayAdapter(Context context, int res, List<Shop> items) {
            super(context, res, items);
            mInflater = LayoutInflater.from(context);
        }

        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            final Shop shop = getItem(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.view_list_item_shop, parent, false);
                holder = new ViewHolder();
                holder.newsThumbnail = (CustomizeImageView) convertView.findViewById(R.id.id_shop_thumbnail);
                holder.newsTitle = (TextView) convertView.findViewById(R.id.id_shop_title);
//                holder.newsVideoSign = convertView.findViewById(R.id.id_news_video_sign);
//                holder.newsSpecialSign = convertView.findViewById(R.id.id_news_special_sign);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
//            if(!TextUtils.isEmpty(shop.getName())){
//                holder.newsTitle.setText(shop.getName());
//            }
//            if( shop.getThumbnailUrls().size() > 0){
//        		holder.newsThumbnail.loadImage(news.getThumbnailUrls().get(0));
//            }


            convertView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    showShop(getContext(), shop);
                }
            });

            return convertView;
        }

        class ViewHolder {
            CustomizeImageView newsThumbnail;
            TextView newsTitle;
            View newsVideoSign;
            View newsSpecialSign;
        }
    }


    public void onResume() {
        Log.d(tag, mCategory + " onResume()");
        super.onResume();
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        Log.d(tag, mCategory + " onViewStateRestored()");
        super.onViewStateRestored(savedInstanceState);
    }

    public void onStart() {
        Log.d(tag, mCategory + " onStart()");
        super.onStart();
    }

    public void onPause() {
        Log.d(tag, mCategory + " onPause()");
        super.onPause();
    }

    public void onStop() {
        Log.d(tag, mCategory + " onStop()");
        super.onStop();
    }

    public void onDestroyView() {
        Log.d(tag, mCategory + " onDestroyView()");
        super.onDestroyView();
    }

    public void onDestroy() {
        Log.d(tag, mCategory + " onDestroy()");
        super.onDestroy();
    }

    public void onDetach() {
        Log.d(tag, mCategory + " onDetach()");
        super.onDetach();
    }
}