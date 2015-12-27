package com.creal.nest;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.model.ShopCategory;
import com.creal.nest.util.ErrorAdapter;
import com.creal.nest.util.MapDistance;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.GetShopListAction;
import com.creal.nest.model.Pagination;
import com.creal.nest.model.Shop;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.ptr.HeaderLoadingSupportPTRListFragment;
import com.creal.nest.views.ptr.PTRListAdapter;

import java.util.List;

public class FragmentNearbyShopList extends HeaderLoadingSupportPTRListFragment {
	private final static String tag = "TT-FragNearbyShopList";
    public static final String INTENT_EXTRA_CATEGORY = "category";
	private ShopCategory mCategory;
	private PTRListAdapter<Shop> mShopListAdapter;
	private GetShopListAction mGetShopListAction;
    private View mContentView;
    private Location mLocation;
    private LocationManager mLocationManager;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(tag, mCategory + " onCreate()");
		super.onCreate(savedInstanceState);
        mCategory = getArguments().getParcelable(INTENT_EXTRA_CATEGORY);
		if (savedInstanceState != null) {
			mCategory = savedInstanceState.getParcelable("mCategory");
		}
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        setMode(PullToRefreshBase.Mode.BOTH);
	}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mContentView == null){
            mContentView = super.onCreateView(inflater, container, savedInstanceState);
            loadFirstPage(true);
        }else{
            ((ViewGroup)mContentView.getParent()).removeView(mContentView);
        }
        return mContentView;
    }

	private void loadFirstPage(boolean isFirst){
		Log.d(tag, mCategory + " loadFirstPage()");
        if(isFirst)
            showLoadingView();
        mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        mGetShopListAction = new GetShopListAction(getActivity(), mCategory.getId(), mLocation.getLatitude() , mLocation.getLongitude(), 1, 10);
		mGetShopListAction.execute(
                new AbstractAction.UICallBack<Pagination<Shop>>() {
                    public void onSuccess(Pagination<Shop> shopList) {
                        if (isDetached() || getActivity() == null) //DO NOT update the view if this fragment is detached from the activity.
                            return;
                        mShopListAdapter = new ShopArrayAdapter(getActivity(), R.layout.view_list_item_shop, shopList.getItems());
                        setAdapter(mShopListAdapter);
                        showListView();
                        refreshComplete();
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        mGetShopListAction = mGetShopListAction.getPreviousPageAction();
                        Toast.makeText(getActivity(), R.string.load_failed, Toast.LENGTH_SHORT).show();
                        setAdapter(new ErrorAdapter(getActivity(), 0));
                        showListView();
                        refreshComplete();
                    }
                }
        );
	}

    private void loadNextPage(){
        Log.d(tag, mCategory + " loadNextPage()");
        mGetShopListAction = mGetShopListAction.getNextPageAction();
        mGetShopListAction.execute(
                new AbstractAction.UICallBack<Pagination<Shop>>() {
                    public void onSuccess(Pagination<Shop> shopList) {
                        if (isDetached() || getActivity() == null) //DO NOT update the view if this fragment is detached from the activity.
                            return;
                        if(shopList.getItems().isEmpty()){
                            Toast.makeText(getActivity(), R.string.no_more_to_load, Toast.LENGTH_SHORT).show();
                            mGetShopListAction = mGetShopListAction.getPreviousPageAction();
                        }
                        mShopListAdapter.addMore(shopList.getItems());
                        refreshComplete();
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        mGetShopListAction =  mGetShopListAction.getPreviousPageAction();
                        Toast.makeText(getActivity(), R.string.load_failed, Toast.LENGTH_SHORT).show();
                        refreshComplete();
                    }
                }
        );
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadFirstPage(false);
    }
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadNextPage();
	}

	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
        Log.d(tag, mCategory + " onSaveInstanceState");
        outState.putParcelable("mCategory", mCategory);
	}

	public ViewHolder createHeaderView(LayoutInflater inflater){
		Log.d(tag, mCategory + " createHeaderView");
		return null;
	}

	private static void showShop(Context context, Shop shop){
		Intent intent = new Intent(context, ShopDetailActivity.class);
        intent.putExtra(ShopDetailActivity.INTENT_EXTRA_SHOP_ID, shop.getId());
		context.startActivity(intent);
    }

    public class ShopArrayAdapter extends PTRListAdapter<Shop> {
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
                holder.thumbnail = (CustomizeImageView) convertView.findViewById(R.id.id_shop_thumbnail);
                holder.title = (TextView) convertView.findViewById(R.id.id_shop_title);
                holder.discount = (TextView)convertView.findViewById(R.id.id_discount);
                holder.address = (TextView) convertView.findViewById(R.id.id_nearby_item_address);
                holder.keywords = (TextView) convertView.findViewById(R.id.id_nearby_item_keywords);
                holder.distance = (TextView) convertView.findViewById(R.id.id_nearby_item_distance);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.thumbnail.loadImage(shop.getLogo());
            holder.title.setText(shop.getTitle());
            holder.discount.setVisibility(shop.getRecommend() ? View.VISIBLE : View.GONE);
            holder.address.setText(shop.getAddress());
            holder.keywords.setText(shop.getKeyword());
            if(mLocation != null && shop.getLatitude() != null && shop.getLatitude().length()> 0 && shop.getLongitude() != null && shop.getLongitude().length()> 0 ) {
                String distance = MapDistance.getLongDistance(mLocation.getLongitude(), mLocation.getLatitude(), Double.valueOf(shop.getLongitude()), Double.valueOf(shop.getLatitude()));
                holder.distance.setText(distance);
            }
            convertView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    showShop(getContext(), shop);
                }
            });
            
            return convertView;
        }

        class ViewHolder {
        	CustomizeImageView thumbnail;
            TextView  title;
            TextView  discount;
            TextView  address;
            TextView  keywords;
            TextView  distance;
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