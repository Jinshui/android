package com.creal.nest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creal.nest.actions.GetRechargeAction;
import com.creal.nest.actions.PaginationAction;
import com.creal.nest.model.Recharge;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.views.ptr.PTRListActivity;

public class RechargeHistoryActivity extends PTRListActivity<Recharge> {

    private static final String TAG = "XYK-RechargeHistoryActivity";

    @Override
    public int getTitleResId() {
        return R.string.recharge_history;
    }

    @Override
    public PaginationAction<Recharge> getPaginationAction() {
        String cardId = PreferenceUtil.getString(this, Constants.APP_USER_CARD_ID, null);
        return new GetRechargeAction(this, 1, 10, cardId);
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
