package com.creal.nest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creal.nest.actions.CommonPaginationAction;
import com.creal.nest.actions.PaginationAction;
import com.creal.nest.model.ExchangeIngot;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.views.ptr.PTRListActivity;

import java.util.HashMap;
import java.util.Map;

public class IngotsExchangeHistoryActivity extends PTRListActivity<ExchangeIngot> {

    public int getTitleResId() {
        return R.string.ingots_exchange_history;
    }

    public PaginationAction<ExchangeIngot> getPaginationAction() {
        String cardId = PreferenceUtil.getString(this, Constants.APP_USER_CARD_ID, null);
        Map paras = new HashMap();
        paras.put(Constants.KEY_CARD_ID, cardId);
        return new CommonPaginationAction(this, 1, Constants.PAGE_SIZE, Constants.URL_GET_EXCHANGE_LIST, paras, ExchangeIngot.class);
    }

    public View getListItemView(final ExchangeIngot exchangeIngot, View convertView, ViewGroup parent, LayoutInflater inflater) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_list_item_ingots_exchange_history, parent, false);
            holder = new ViewHolder();
            holder.money = (TextView) convertView.findViewById(R.id.id_ingot_recharge_amount);
            holder.desc = (TextView) convertView.findViewById(R.id.id_ingot_exchange_desc);
            holder.time = (TextView) convertView.findViewById(R.id.id_ingot_recharge_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.desc.setText(String.format(getString(R.string.ingot_exchange_ingots), exchangeIngot.getAmount()));
        holder.money.setText(exchangeIngot.getMoney());
        holder.time.setText(exchangeIngot.getDateTime());
        return convertView;
    }

    class ViewHolder {
        TextView money;
        TextView desc;
        TextView time;
    }
}