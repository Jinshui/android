package com.creal.nest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creal.nest.actions.CommonPaginationAction;
import com.creal.nest.actions.PaginationAction;
import com.creal.nest.model.Recharge;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.views.ptr.PTRListActivity;

import java.util.HashMap;
import java.util.Map;

public class RechargeHistoryActivity extends PTRListActivity<Recharge> {

    private static final String TAG = "XYK-RechargeHistoryActivity";

    @Override
    public int getTitleResId() {
        return R.string.recharge_history;
    }

    @Override
    public PaginationAction<Recharge> getPaginationAction() {
        String cardId = PreferenceUtil.getString(this, Constants.APP_USER_CARD_ID, null);
        Map<String, String> parameters = new HashMap<>();
        parameters.put(Constants.KEY_CARD_ID, cardId);
        parameters.put("state", "");
        parameters.put("start_time", "");
        parameters.put("end_time", "");
        return new CommonPaginationAction(this, 1, Constants.PAGE_SIZE, Constants.URL_GET_RECHARGE_HISTORY, parameters, Recharge.class);
    }

    public View getListItemView(Recharge item, View convertView, ViewGroup parent, LayoutInflater inflater) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_list_item_recharge_history, parent, false);
            holder = new ViewHolder();
            holder.amount = (TextView) convertView.findViewById(R.id.id_banlance_recharge_amount);
            holder.time = (TextView) convertView.findViewById(R.id.id_banlance_recharge_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.amount.setText(String.valueOf(item.getAmount()));
        holder.time.setText(item.getOrderTime());
        return convertView;
    }

    class ViewHolder {
        TextView amount;
        TextView time;
    }
}
