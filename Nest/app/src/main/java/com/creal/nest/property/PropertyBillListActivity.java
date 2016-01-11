package com.creal.nest.property;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.creal.nest.Constants;
import com.creal.nest.R;
import com.creal.nest.actions.CommonPaginationAction;
import com.creal.nest.actions.JSONRespObject;
import com.creal.nest.actions.PaginationAction;
import com.creal.nest.model.ArrearageInfo;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.views.ptr.PTRListFragment;

import java.util.HashMap;
import java.util.Map;

public class PropertyBillListActivity extends FragmentActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, new PropertyBaseInfoListFragment()).commit();
        }
    }

    public static class PropertyBaseInfoListFragment extends PTRListFragment<ArrearageInfo> {

        public int getTitleResId() {
            return R.string.property_bills;
        }

        public PaginationAction<ArrearageInfo> getPaginationAction() {
            String cardId = PreferenceUtil.getString(getActivity(), Constants.APP_USER_CARD_ID, null);
            Map<String, String> parameters = new HashMap<>();
            parameters.put(Constants.KEY_CARD_ID, cardId);
            return new CommonPaginationAction(getActivity(), 1, Constants.PAGE_SIZE, Constants.URL_GET_BILL, parameters, ArrearageInfo.class);
        }

        protected boolean initOnResume(){
            return true;
        }

        public View getListItemView(final ArrearageInfo item, View convertView, ViewGroup parent, LayoutInflater inflater) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.view_list_item_property_pay_item, parent, false);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.id_txt_item_name);
                holder.payBtn = (Button) convertView.findViewById(R.id.id_btn_recharge);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name.setText(item.getName());
            holder.payBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PropertyPayActivity.class);
                    intent.putExtra(PropertyPayActivity.INTENT_EXTRA_ARREARAGE_INFO, item);
                    startActivity(intent);
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView name;
            Button payBtn;
        }
    }
}