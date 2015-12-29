package com.creal.nest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creal.nest.actions.CommonPaginationAction;
import com.creal.nest.actions.PaginationAction;
import com.creal.nest.model.Shopping;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.views.ptr.PTRListActivity;

import java.util.HashMap;
import java.util.Map;

public class ShoppingHistoryActivity extends PTRListActivity<Shopping> {
    private static final String TAG = "XYK-ShoppingHistoryActivity";

    @Override
    public PaginationAction<Shopping> getPaginationAction() {
        String cardId = PreferenceUtil.getString(this, Constants.APP_USER_CARD_ID, null);
        Map<String, String> parameters = new HashMap<>();
        parameters.put(Constants.KEY_CARD_ID, cardId);
        parameters.put("state", "");
        parameters.put("start_time", "");
        parameters.put("end_time", "");
        return new CommonPaginationAction(this, 1, Constants.PAGE_SIZE, Constants.URL_GET_SHOP_HISTORY, parameters, Shopping.class);
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