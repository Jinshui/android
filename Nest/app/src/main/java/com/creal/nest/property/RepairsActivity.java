package com.creal.nest.property;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creal.nest.Constants;
import com.creal.nest.R;
import com.creal.nest.actions.CommonPaginationAction;
import com.creal.nest.actions.PaginationAction;
import com.creal.nest.model.Repair;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.Utils;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.HeaderView;
import com.creal.nest.views.ptr.LoadingSupportPTRListView;
import com.creal.nest.views.ptr.PTRListFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RepairsActivity extends FragmentActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, new RepairListFragment()).commit();
        }
    }

    public static class RepairListFragment extends PTRListFragment<Repair> {

        public int getTitleResId() {
            return R.string.property_repair_report_list;
        }

        public PaginationAction<Repair> getPaginationAction() {
            String cardId = PreferenceUtil.getString(getActivity(), Constants.APP_USER_CARD_ID, null);
            Map<String, String> parameters = new HashMap<>();
            parameters.put(Constants.KEY_CARD_ID, cardId);
//            parameters.put("state", "");
//            parameters.put("start_time", "");
//            parameters.put("end_time", "");
            return new CommonPaginationAction(getActivity(), 1, Constants.PAGE_SIZE, Constants.URL_REPORT_REPAIR_LIST, parameters, Repair.class, "repair");
        }

        public void showDetailActivity(Repair repair) {
            Intent intent = new Intent(getActivity(), RepairDetailActivity.class);
            intent.putExtra(RepairDetailActivity.INTENT_EXTRA_REPAIR_ID, repair.getId());
            startActivity(intent);
        }

        public View getListItemView(Repair repair, View convertView, ViewGroup parent, LayoutInflater inflater) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.view_list_item_repair_report, parent, false);
                holder = new ViewHolder();
                holder.repairThumbnail = (CustomizeImageView) convertView.findViewById(R.id.id_report_thumbnail);
                holder.name = (TextView) convertView.findViewById(R.id.id_txt_repair_name);
                holder.finishedTime = (TextView) convertView.findViewById(R.id.id_txt_repair_finish_time);
                holder.reportTime = (TextView) convertView.findViewById(R.id.id_txt_repair_report_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (repair.isDone()) {
                holder.repairThumbnail.setBackgroundResource(R.drawable.ic_property_repair_report2);
                convertView.findViewById(R.id.id_finish_section).setVisibility(View.VISIBLE);
            } else {
                holder.repairThumbnail.setBackgroundResource(R.drawable.ic_property_repair_report);
                convertView.findViewById(R.id.id_finish_section).setVisibility(View.INVISIBLE);
            }

            holder.name.setText(repair.getTitle());
            if(!TextUtils.isEmpty(repair.getTime()) && repair.getTime().matches("[0-9]{4}-[0-9]{2}-[0-9]{2}.*")) {
                Date reportTime = Utils.parseDate("yyyy-MM-dd HH:mm:ss", repair.getTime());
                String strTime = Utils.formatDate("yyyy-MM-dd", reportTime);
                holder.reportTime.setText(strTime);
//                holder.finishedTime.setText(repair.getTime());
            }
            return convertView;
        }

        class ViewHolder {
            CustomizeImageView repairThumbnail;
            TextView name;
            TextView finishedTime;
            TextView reportTime;
        }
    }
}