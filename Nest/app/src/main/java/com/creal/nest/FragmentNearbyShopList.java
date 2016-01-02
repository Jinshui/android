package com.creal.nest;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creal.nest.actions.CommonPaginationAction;
import com.creal.nest.actions.PaginationAction;
import com.creal.nest.model.Shop;
import com.creal.nest.model.ShopCategory;
import com.creal.nest.util.MapDistance;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.ptr.PTRListFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentNearbyShopList extends PTRListFragment<Shop> {
    private final static String tag = "TT-FragNearbyShopList";
    public static final String INTENT_EXTRA_CATEGORY = "category";
    private ShopCategory mCategory;
    private View mContentView;
    private Location mLocation;
    private LocationManager mLocationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(tag, mCategory + " onCreate()");
        super.onCreate(savedInstanceState);
        mCategory = getArguments().getParcelable(INTENT_EXTRA_CATEGORY);
        if (savedInstanceState != null) {
            mCategory = savedInstanceState.getParcelable(INTENT_EXTRA_CATEGORY);
        }
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = super.onCreateView(inflater, container, savedInstanceState);
        } else {
            ((ViewGroup) mContentView.getParent()).removeView(mContentView);
        }
        getHeaderView().setVisibility(View.GONE);
        return mContentView;
    }

    public PaginationAction<Shop> getPaginationAction() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("class_id", mCategory.getId());
        mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//        parameters.put("latitude", mLocation.getLatitude());
//        parameters.put("longitude", mLocation.getLongitude());
        return new CommonPaginationAction(getActivity(), 1, 10, Constants.URL_GET_SHOPS, parameters, Shop.class, "cominfo");
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(tag, mCategory + " onSaveInstanceState");
        outState.putParcelable(INTENT_EXTRA_CATEGORY, mCategory);
    }

    public void showDetailActivity(Shop shop) {
        Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
        intent.putExtra(ShopDetailActivity.INTENT_EXTRA_SHOP_ID, shop.getId());
        getActivity().startActivity(intent);
    }

    public View getListItemView(Shop shop, View convertView, ViewGroup parent, LayoutInflater inflater) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_list_item_shop, parent, false);
            holder = new ViewHolder();
            holder.thumbnail = (CustomizeImageView) convertView.findViewById(R.id.id_shop_thumbnail);
            holder.title = (TextView) convertView.findViewById(R.id.id_shop_title);
            holder.discount = (TextView) convertView.findViewById(R.id.id_discount);
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
        if (mLocation != null && shop.getLatitude() != null && shop.getLatitude().length() > 0 && shop.getLongitude() != null && shop.getLongitude().length() > 0) {
            String distance = MapDistance.getLongDistance(mLocation.getLongitude(), mLocation.getLatitude(), Double.valueOf(shop.getLongitude()), Double.valueOf(shop.getLatitude()));
            holder.distance.setText(distance);
        }
        return convertView;
    }

    @Override
    public int getTitleResId() {
        return 0;
    }

    class ViewHolder {
        CustomizeImageView thumbnail;
        TextView title;
        TextView discount;
        TextView address;
        TextView keywords;
        TextView distance;
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