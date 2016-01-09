package com.creal.nest;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creal.nest.actions.CommonPaginationAction;
import com.creal.nest.actions.PaginationAction;
import com.creal.nest.model.RechargeCard;
import com.creal.nest.model.Shopping;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.views.ptr.PTRListActivity;
import com.creal.nest.views.ptr.PTRListFragment;

import java.util.HashMap;
import java.util.Map;

public class ShoppingHistoryActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, new ShoppingListFragment()).commit();
        }
    }

    public static class ShoppingListFragment extends PTRListFragment<Shopping> {
        @Override
        public PaginationAction<Shopping> getPaginationAction() {
            String cardId = PreferenceUtil.getString(getActivity(), Constants.APP_USER_CARD_ID, null);
            Map<String, String> parameters = new HashMap<>();
            parameters.put(Constants.KEY_CARD_ID, cardId);
            parameters.put("state", "");
            parameters.put("start_time", "");
            parameters.put("end_time", "");
            return new CommonPaginationAction(getActivity(), 1, Constants.PAGE_SIZE, Constants.URL_GET_SHOP_HISTORY, parameters, Shopping.class);
        }

        public View getListItemView(Shopping item, View convertView, ViewGroup parent, LayoutInflater inflater) {
            ViewHolder holder;
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
            holder.card.setText(item.getSellerName());
            holder.status.setText(item.getState().toString());
            holder.time.setText(String.format(getString(R.string.shop_time), item.getOrderTime()));
            holder.cost.setText("" + ((float) item.getAmount() / 100) + "元");
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
}