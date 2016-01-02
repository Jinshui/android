package com.creal.nest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creal.nest.actions.CommonPaginationAction;
import com.creal.nest.actions.PaginationAction;
import com.creal.nest.model.SalesActivity;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.ptr.PTRListActivity;
import com.creal.nest.views.ptr.PTRListFragment;

public class LatestActivitiesActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
            SalesActivityListFragment list = new SalesActivityListFragment();
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, list).commit();
        }
    }

    public static class SalesActivityListFragment extends PTRListFragment<SalesActivity> {

        public int getTitleResId() {
            return R.string.latest_activities;
        }

        public PaginationAction<SalesActivity> getPaginationAction() {
            return new CommonPaginationAction(getActivity(), 1, 10, Constants.URL_GET_LATEST_ACTIVITIES, null, SalesActivity.class, "article");
        }

        public void showDetailActivity(SalesActivity salesActivity) {
            Intent intent = new Intent(getActivity(), LatestActivityDetailActivity.class);
            intent.putExtra(LatestActivityDetailActivity.INTENT_EXTRA_SALES_ACTIVITY, salesActivity);
            startActivity(intent);
        }

        public View getListItemView(final SalesActivity salesActivity, View convertView, ViewGroup parent, LayoutInflater inflater) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.view_list_item_latest_activities, parent, false);
                holder = new ViewHolder();
                holder.couponThumbnail = (CustomizeImageView) convertView.findViewById(R.id.id_activity_thumbnail);
                holder.name = (TextView) convertView.findViewById(R.id.id_txt_activity_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.couponThumbnail.loadImage(salesActivity.getImageUrl());
            holder.name.setText(salesActivity.getTitle());

            return convertView;
        }

        class ViewHolder {
            CustomizeImageView couponThumbnail;
            TextView name;
        }
    }
}
