package com.creal.nest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creal.nest.actions.CommonPaginationAction;
import com.creal.nest.actions.PaginationAction;
import com.creal.nest.model.ExchangeIngot;
import com.creal.nest.views.ptr.PTRListActivity;

public class IngotsExchangeHistoryActivity extends PTRListActivity<ExchangeIngot> {

    public int getTitleResId() {
        return R.string.ingots_exchange_history;
    }

    public PaginationAction<ExchangeIngot> getPaginationAction() {
        return new CommonPaginationAction(this, 1, 10, "", null, ExchangeIngot.class, "coupons");
    }

    public View getListItemView(final ExchangeIngot exchangeIngot, View convertView, ViewGroup parent, LayoutInflater inflater) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_list_item_ingots_exchange_history, parent, false);
            holder = new ViewHolder();
            holder.amount = (TextView) convertView.findViewById(R.id.id_ingot_recharge_amount);
            holder.desc = (TextView) convertView.findViewById(R.id.id_ingot_exchange_desc);
            holder.time = (TextView) convertView.findViewById(R.id.id_ingot_recharge_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    class ViewHolder {
        TextView amount;
        TextView desc;
        TextView time;
    }
}