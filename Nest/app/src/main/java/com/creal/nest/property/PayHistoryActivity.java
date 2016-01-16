package com.creal.nest.property;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creal.nest.Constants;
import com.creal.nest.R;
import com.creal.nest.actions.CommonPaginationAction;
import com.creal.nest.actions.PaginationAction;
import com.creal.nest.model.Payment;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.Utils;
import com.creal.nest.views.ptr.PTRListFragment;

import java.util.HashMap;
import java.util.Map;

public class PayHistoryActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, new PaymentListFragment()).commit();
        }
    }

    public static class PaymentListFragment extends PTRListFragment<Payment> {
        @Override
        public int getTitleResId() {
            return R.string.pay_history;
        }

        @Override
        public PaginationAction<Payment> getPaginationAction() {
            String cardId = PreferenceUtil.getString(getActivity(), Constants.APP_USER_CARD_ID, null);
            Map<String, String> parameters = new HashMap<>();
            parameters.put(Constants.KEY_CARD_ID, cardId);
//            parameters.put("state", "");
//            parameters.put("start_time", "");
//            parameters.put("end_time", "");
            return new CommonPaginationAction(getActivity(), 1, Constants.PAGE_SIZE, Constants.URL_GET_PAY_HISTORY, parameters, Payment.class);
        }

        public View getListItemView(Payment item, View convertView, ViewGroup parent, LayoutInflater inflater) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.view_list_item_recharge_history, parent, false);
                holder = new ViewHolder();
                holder.amount = (TextView) convertView.findViewById(R.id.id_banlance_recharge_amount);
                holder.time = (TextView) convertView.findViewById(R.id.id_banlance_recharge_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.amount.setText(Utils.formatMoney(item.getAmount()));
            holder.time.setText(item.getTime());
            return convertView;
        }

        class ViewHolder {
            TextView amount;
            TextView time;
        }
    }
}
