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
import com.creal.nest.actions.JSONRespObject;
import com.creal.nest.actions.PaginationAction;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.views.ptr.PTRListFragment;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PropertyBaseInfoActivity extends FragmentActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, new PropertyBaseInfoListFragment()).commit();
        }
    }

    public static class PropertyBaseInfoListFragment extends PTRListFragment<JSONRespObject> {

        public int getTitleResId() {
            return R.string.property_user_base_info;
        }

        public PaginationAction<JSONRespObject> getPaginationAction() {
            String cardId = PreferenceUtil.getString(getActivity(), Constants.APP_USER_CARD_ID, null);
            Map<String, String> parameters = new HashMap<>();
            parameters.put(Constants.KEY_CARD_ID, cardId);
            return new CommonPaginationAction(getActivity(), 1, Constants.PAGE_SIZE, Constants.URL_REPORT_REPAIR_LIST, parameters, JSONRespObject.class, "repair");
        }

        public View getListItemView(JSONRespObject item, View convertView, ViewGroup parent, LayoutInflater inflater) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.view_list_item_property_basic_info, parent, false);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.id_txt_item_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name.setText(item.getString("title"));
            return convertView;
        }

        class ViewHolder {
            TextView name;
        }
    }
}