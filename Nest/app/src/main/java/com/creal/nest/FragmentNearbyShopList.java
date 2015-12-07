package com.creal.nest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.creal.nest.R;
import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.GetShopAction;
import com.creal.nest.actions.ParallelTask;
import com.creal.nest.model.Pagination;
import com.creal.nest.model.Shop;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.ptr.HeaderLoadingSupportPTRListFragment;
import com.creal.nest.views.ptr.PTRListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentNearbyShopList extends HeaderLoadingSupportPTRListFragment {
	private final static String tag = "TT-FragNearbyShopList";

	private String mCategory;
	private PTRListAdapter<Shop> mShopListAdapter;
	private List<Shop> mShopList;
	private boolean mShopLoadedFromServer = false;
	private GetShopAction mGetShopAction;

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
		setMode(PullToRefreshBase.Mode.PULL_FROM_END);
//		mNewsDAO = ((TouTiaoApp)getActivity().getApplication()).getNewsDAO();
//		mHeadNewsDAO = ((TouTiaoApp)getActivity().getApplication()).getHeadNewsDAO();
	}
	
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		Log.d(tag, mCategory + " onActivityCreated()");
		mAsyncTaskCount = 0;
		showLoadingView();
        setMode(PullToRefreshBase.Mode.PULL_FROM_END);
		if( !mShopLoadedFromServer )
			loadShopFromServer();
		else
			loadShopFromDB();
	}
	
	private void loadShopFromServer(){
		mAsyncTaskCount ++;
		Log.d(tag, mCategory +" loadShopFromServer(): tasks: " + mAsyncTaskCount);
		mGetShopAction = new GetShopAction(getActivity(), mCategory, 1, Constants.PAGE_SIZE);
		mGetShopAction.execute(
                new AbstractAction.BackgroundCallBack<Pagination<Shop>>() {
                    public void onSuccess(Pagination<Shop> newsPage) {
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                    }
                },
                new AbstractAction.UICallBack<Pagination<Shop>>() {
                    public void onSuccess(Pagination<Shop> newsList) {
                        mShopLoadedFromServer = true;
                        if (isDetached() || getActivity() == null) //DO NOT update the view if this fragment is detached from the activity.
                            return;
                        if (mShopListAdapter == null) {
                            mShopListAdapter = new ShopArrayAdapter(getActivity(), R.layout.view_shop_list_item, newsList.getItems());
                            setAdapter(mShopListAdapter);
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
	
	private void loadShopFromDB(){
		mAsyncTaskCount ++;
		Log.d(tag, mCategory +" loadShopFromDB(): tasks: " + mAsyncTaskCount);
		new ParallelTask<List<Shop>>() {
			protected List<Shop> doInBackground(Void... params) {
                return Collections.emptyList();
			}
			public void onPostExecute(List<Shop> shopList){
				//The pager is viewing another page now.
				if(getActivity() == null)
					return;
                shopList = new ArrayList<Shop>();
                shopList.add(new Shop());
                shopList.add(new Shop());
                shopList.add(new Shop());
                shopList.add(new Shop());
                shopList.add(new Shop());
                shopList.add(new Shop());
                shopList.add(new Shop());
                shopList.add(new Shop());
                shopList.add(new Shop());
                shopList.add(new Shop());
                shopList.add(new Shop());
				if(mShopListAdapter == null){
					mShopListAdapter = new ShopArrayAdapter(getActivity(), R.layout.view_shop_list_item, shopList);
					mGetShopAction = new GetShopAction(getActivity(), mCategory, shopList.size(), Constants.PAGE_SIZE);
					setAdapter(mShopListAdapter);
				}else{
					mShopListAdapter.clear();
					mShopListAdapter.addMore(shopList);
				}
				afterLoadReturned();
			}
		}.execute();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		mAsyncTaskCount ++;
		Log.d(tag, mCategory +" onPullUpToRefresh(): tasks: " + mAsyncTaskCount);
		mGetShopAction = (GetShopAction)mGetShopAction.getNextPageAction();
		mGetShopAction.execute(
				new AbstractAction.BackgroundCallBack<Pagination<Shop>>() {
					public void onSuccess(Pagination<Shop> newsPage) {
					}

					public void onFailure(AbstractAction.ActionError error) {
					}
				},
				new AbstractAction.UICallBack<Pagination<Shop>>() {
					public void onSuccess(Pagination<Shop> shopList) {
						if (isDetached() || getActivity() == null) //DO NOT update the view if this fragment is detached from the activity.
							return;
						if (mShopListAdapter == null) {
							mShopListAdapter = new ShopArrayAdapter(getActivity(), R.layout.view_shop_list_item, shopList.getItems());
							setAdapter(mShopListAdapter);
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
						Toast.makeText(getActivity(), R.string.load_failed, Toast.LENGTH_SHORT).show();
						mGetShopAction = (GetShopAction) mGetShopAction.getPreviousPageAction();
						afterLoadReturned();
					}
				}
		);
	}
	
	private void afterLoadReturned(){
		mAsyncTaskCount --;
		Log.d(tag, mCategory + " afterLoadReturned(): tasks: " + mAsyncTaskCount);
		if(mAsyncTaskCount == 0){
			showListView();
		}
		refreshComplete();
	}
	
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(tag, mCategory + " onSaveInstanceState");
		outState.putString("mCategory", mCategory);
		outState.putBoolean("mShopLoaded", mShopLoadedFromServer);
	}

	public ViewHolder createHeaderView(LayoutInflater inflater){
		Log.d(tag, mCategory + " createHeaderView");
		return null;
	}

	private static void showShop(Context context, Shop shop){
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
                convertView = mInflater.inflate( R.layout.view_shop_list_item, parent, false);
                holder = new ViewHolder();
                holder.newsThumbnail = (CustomizeImageView) convertView.findViewById(R.id.id_shop_thumbnail);
                holder.newsTitle = (TextView) convertView.findViewById(R.id.id_shop_title);
                holder.newsVideoSign = convertView.findViewById(R.id.id_nearby_item_address);
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
            

            convertView.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					showShop(getContext(), shop);
				}
            });
            
            return convertView;
        }

        class ViewHolder {
        	CustomizeImageView newsThumbnail;
            TextView  newsTitle;
            View newsVideoSign;
            View newsSpecialSign;
        }
    }
	

	public void onResume(){
		Log.d(tag, mCategory +" onResume()");
		super.onResume();
	}
	
	public void onViewStateRestored (Bundle savedInstanceState){
		Log.d(tag, mCategory +" onViewStateRestored()");
		super.onViewStateRestored(savedInstanceState);
	}
	public void onStart (){
		Log.d(tag, mCategory +" onStart()");
		super.onStart();
	}
	
	public void onPause (){
		Log.d(tag, mCategory +" onPause()");
		super.onPause();
	}
	public void onStop (){
		Log.d(tag, mCategory +" onStop()");
		super.onStop();
	}
	public void onDestroyView (){
		Log.d(tag, mCategory +" onDestroyView()");
		super.onDestroyView();
	}
	public void onDestroy (){
		Log.d(tag, mCategory +" onDestroy()");
		super.onDestroy();
	}
	public void onDetach (){
		Log.d(tag, mCategory +" onDetach()");
		super.onDetach();
	}
}