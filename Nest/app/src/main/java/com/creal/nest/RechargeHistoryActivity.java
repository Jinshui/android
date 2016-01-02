package com.creal.nest;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creal.nest.actions.CommonPaginationAction;
import com.creal.nest.actions.PaginationAction;
import com.creal.nest.model.Coupon;
import com.creal.nest.model.Recharge;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.views.ptr.PTRListActivity;
import com.creal.nest.views.ptr.PTRListFragment;

import java.util.HashMap;
import java.util.Map;

public class RechargeHistoryActivity extends FragmentActivity {

    public static final String INTENT_EXTRA_ACTION_TYPE = "action_type";
    public static final String INTENT_EXTRA_ACTION_TYPE_RECHARGE = "RECHARGE_HISTORY";
    public static final String INTENT_EXTRA_ACTION_TYPE_PROPERTY_PAYMENT = "PROPERTY_PAYMENT_HISTORY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
            RechargeListFragment fragment = new RechargeListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(INTENT_EXTRA_ACTION_TYPE, getIntent().getStringExtra(INTENT_EXTRA_ACTION_TYPE));
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
        }
    }

    public static class RechargeListFragment extends PTRListFragment<Recharge> {
        private String mActionType;
        public void onCreate(Bundle bundle){
            super.onCreate(bundle);
            mActionType = getArguments().getString(INTENT_EXTRA_ACTION_TYPE);
        }

        @Override
        public int getTitleResId() {
            if(INTENT_EXTRA_ACTION_TYPE_RECHARGE.equals(mActionType)){
                return R.string.recharge_history;
            }else {
                return R.string.pay_history;
            }
        }

        @Override
        public PaginationAction<Recharge> getPaginationAction() {
            String cardId = PreferenceUtil.getString(getActivity(), Constants.APP_USER_CARD_ID, null);
            Map<String, String> parameters = new HashMap<>();
            parameters.put(Constants.KEY_CARD_ID, cardId);
            parameters.put("state", "");
            parameters.put("start_time", "");
            parameters.put("end_time", "");
            if(INTENT_EXTRA_ACTION_TYPE_RECHARGE.equals(mActionType)){
                return new CommonPaginationAction(getActivity(), 1, Constants.PAGE_SIZE, Constants.URL_GET_RECHARGE_HISTORY, parameters, Recharge.class);
            }else{
                return new CommonPaginationAction(getActivity(), 1, Constants.PAGE_SIZE, Constants.URL_PAY_HISTORY, parameters, Recharge.class);
            }
        }

        public View getListItemView(Recharge item, View convertView, ViewGroup parent, LayoutInflater inflater) {
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
            holder.amount.setText(String.valueOf(item.getAmount()));
            holder.time.setText(item.getOrderTime());
            return convertView;
        }

        class ViewHolder {
            TextView amount;
            TextView time;
        }
    }
}
