package com.creal.nest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.CommonPaginationAction;
import com.creal.nest.actions.PaginationAction;
import com.creal.nest.actions.StringAction;
import com.creal.nest.model.RechargeCard;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.views.ptr.PTRListFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.HashMap;
import java.util.Map;

public class IngotsMallActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, new RechargeCardListFragment()).commit();
        }
    }

    public void onShoppingHistoryClick(View view) {
        Intent intent = new Intent(this, ShoppingHistoryActivity.class);
        startActivity(intent);
    }

    public static class RechargeCardListFragment extends PTRListFragment<RechargeCard> {

        private TextView mIngotsTxt;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = super.onCreateView(inflater, container, savedInstanceState);
            getHeaderView().setRightText(R.string.ingots_mall_help);
            getHeaderView().setRightTextListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //TODO: what is ingots mall
                }
            });

            View listHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.view_list_header_ingots_mall, null, false);
            mIngotsTxt = (TextView) listHeaderView.findViewById(R.id.id_txt_my_points);
            getLoadingSupportPTRListView().addViewToListHeader(listHeaderView, LinearLayout.LayoutParams.WRAP_CONTENT);
            return view;
        }

        protected void onPostLoadFirstPage() {
            String cardId = PreferenceUtil.getString(getActivity(), Constants.APP_USER_CARD_ID, null);
            Map parameters = new HashMap<>();
            parameters.put(Constants.KEY_CARD_ID, cardId);
            StringAction getMyIngotsAction = new StringAction(getActivity(), Constants.URL_GET_MY_INGOTS, parameters, "amount");
            getMyIngotsAction.execute(new AbstractAction.UICallBack<String>() {
                public void onSuccess(String response) {
                    if (response != null)
                        mIngotsTxt.setText(response);
                }

                public void onFailure(AbstractAction.ActionError error) {
                    Toast.makeText(getActivity(), "我的元宝数加载失败，请下拉刷新", Toast.LENGTH_LONG).show();
                }
            });
        }

        public PullToRefreshBase.Mode getPTRMode() {
            return PullToRefreshBase.Mode.PULL_FROM_START;
        }

        @Override
        public PaginationAction<RechargeCard> getPaginationAction() {
            return new CommonPaginationAction(getActivity(), 1, 10, Constants.URL_GET_RECHARGE_CARD, null, RechargeCard.class, "rechargecard");
        }

        @Override
        public View getListItemView(final RechargeCard rechargeCard, View convertView, ViewGroup parent, LayoutInflater inflater) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.view_list_item_ingots_mall, parent, false);
                holder = new ViewHolder();
                holder.img = (TextView) convertView.findViewById(R.id.id_ingot_thumbnail);
                holder.name = (TextView) convertView.findViewById(R.id.id_ingot_title);
                holder.amount = (TextView) convertView.findViewById(R.id.id_ingot_amount);
                holder.info = (TextView) convertView.findViewById(R.id.id_ingot_info);
                holder.exchange = (TextView) convertView.findViewById(R.id.id_ingot_exchange);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.exchange.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ExchangeIngotsConfirmDialog.class);
                    intent.putExtra(ExchangeIngotsConfirmDialog.INTENT_EXTRA_RECHARGE_CARD, rechargeCard);
                    startActivity(intent);
                }
            });
            holder.img.setText(String.valueOf(rechargeCard.getAmount()));
            holder.name.setText(rechargeCard.getName());
            holder.amount.setText(String.format(getString(R.string.ingots_num), rechargeCard.getNum()));
            holder.info.setText(rechargeCard.getDesc());
            return convertView;
        }

        @Override
        public int getTitleResId() {
            return R.string.ingots_mall_title;
        }

        class ViewHolder {
            TextView img;
            TextView name;
            TextView amount;
            TextView info;
            TextView exchange;
        }
    }
}
