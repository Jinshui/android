package com.creal.nest;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.GetShoppingHistoryAction;
import com.creal.nest.actions.PaginationAction;
import com.creal.nest.model.Pagination;
import com.creal.nest.model.Shopping;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.views.HeaderView;
import com.creal.nest.views.ptr.LoadingSupportPTRListView;
import com.creal.nest.views.ptr.PTRListActivity;
import com.creal.nest.views.ptr.PTRListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

public class ShoppingHistoryActivity extends PTRListActivity<Shopping> {
    private static final String TAG = "XYK-ShoppingHistoryActivity";

    @Override
    public PaginationAction<Shopping> getPaginationAction() {
        String cardId = PreferenceUtil.getString(this, Constants.APP_USER_CARD_ID, null);
        return new GetShoppingHistoryAction(this, 1, 10, cardId);
    }

    public View getListItemView(Shopping item, View convertView, ViewGroup parent, LayoutInflater inflater) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_list_item_shopping_history, parent, false);
            holder = new ViewHolder();
            holder.card = (TextView) convertView.findViewById(R.id.id_txt_shopping_card);
            holder.status = (TextView) convertView.findViewById(R.id.id_txt_shopping_status);
            holder.time = (TextView) convertView.findViewById(R.id.id_txt_shopping_time);
            holder.cost = (TextView) convertView.findViewById(R.id.id_txt_shopping_cost);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.card.setText(item.getOrderId());
        holder.status.setText(item.getState().toString());
        holder.time.setText(String.format(getString(R.string.shop_time), item.getOrderTime()));
        holder.cost.setText("" + ((float)item.getAmount() / 100) + "元");
        return convertView;
    }

    @Override
    public int getTitleResId() {
        return R.string.my_shopping_history;
    }

    class ViewHolder {
        TextView card;
        TextView status;
        TextView time;
        TextView cost;
    }
}