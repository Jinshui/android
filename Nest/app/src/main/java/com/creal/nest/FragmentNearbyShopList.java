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
	private GetShopAction mGetShopAction;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(tag, mCategory + " onCreate()");
		super.onCreate(savedInstanceState);
        mCategory = getArguments().getString("category");
		if (savedInstanceState != null) {
			mCategory = savedInstanceState.getString("mCategory");
		}
		setMode(PullToRefreshBase.Mode.PULL_FROM_END);
	}
	
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		Log.d(tag, mCategory + " onActivityCreated()");
        setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        loadFirstPage();
	}
	
	private void loadFirstPage(){
		Log.d(tag, mCategory + " loadFirstPage()");
        showLoadingView();
        mGetShopAction = new GetShopAction(getActivity(), mCategory, 1, Constants.PAGE_SIZE);
		mGetShopAction.execute(
                new AbstractAction.BackgroundCallBack<Pagination<Shop>>() {
                    public void onSuccess(Pagination<Shop> newsPage) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new AbstractAction.UICallBack<Pagination<Shop>>() {
                    public void onSuccess(Pagination<Shop> shopList) {
                        if (isDetached() || getActivity() == null) //DO NOT update the view if this fragment is detached from the activity.
                            return;
                        testLoadFirstPage();
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        testLoadFirstPage();
                    }
                }
        );
	}
	
	private void testLoadFirstPage(){
		Log.d(tag, mCategory + " testLoadFirstPage(): ");
        ArrayList<Shop> shopList = new ArrayList<Shop>();
        shopList.add(new Shop());
        shopList.add(new Shop());
        shopList.add(new Shop());
        shopList.add(new Shop());
        shopList.add(new Shop());
        shopList.add(new Shop());
        shopList.add(new Shop());
        mShopListAdapter = new ShopArrayAdapter(getActivity(), R.layout.view_list_item_shop, shopList);
        setAdapter(mShopListAdapter);
        showListView();
        refreshComplete();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		Log.d(tag, mCategory + " onPullUpToRefresh()");
		mGetShopAction = mGetShopAction.getNextPageAction();
		mGetShopAction.execute(
                new AbstractAction.BackgroundCallBack<Pagination<Shop>>() {
                    public void onSuccess(Pagination<Shop> newsPage) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new AbstractAction.UICallBack<Pagination<Shop>>() {
                    public void onSuccess(Pagination<Shop> shopList) {
                        if (isDetached() || getActivity() == null) //DO NOT update the view if this fragment is detached from the activity.
                            return;
                        testLoadNextPage();
                    }

                    public void onFailure(AbstractAction.ActionError error) {
//                        Toast.makeText(getActivity(), R.string.load_failed, Toast.LENGTH_SHORT).show();
                        mGetShopAction =  mGetShopAction.getPreviousPageAction();
                        testLoadNextPage();
                    }
                }
        );
	}

    private void testLoadNextPage(){
        Log.d(tag, mCategory + " testLoadNextPage(): ");
        ArrayList<Shop> shopList = new ArrayList<Shop>();
        shopList.add(new Shop());
        shopList.add(new Shop());
        Toast.makeText(getActivity(), "成功加载两条", Toast.LENGTH_SHORT).show();
        mShopListAdapter.addMore(shopList);
        refreshComplete();
    }
	
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
        Log.d(tag, mCategory + " onSaveInstanceState");
        outState.putString("mCategory", mCategory);
	}

	public ViewHolder createHeaderView(LayoutInflater inflater){
		Log.d(tag, mCategory + " createHeaderView");
		return null;
	}

	private static void showShop(Context context, Shop shop){
		Intent showNewsDetailIntent = new Intent(context, ShopDetailActivity.class);
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
                convertView = mInflater.inflate( R.layout.view_list_item_shop, parent, false);
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