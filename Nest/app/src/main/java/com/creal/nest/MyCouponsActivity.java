package com.creal.nest;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creal.nest.actions.CommonPaginationAction;
import com.creal.nest.actions.PaginationAction;
import com.creal.nest.model.Coupon;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.ptr.PTRListActivity;

import java.util.HashMap;
import java.util.Map;

public class MyCouponsActivity extends PTRListActivity<Coupon> {
    public int getTitleResId() {
        return R.string.my_coupons;
    }

    public PaginationAction<Coupon> getPaginationAction() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(Constants.KEY_CARD_ID, PreferenceUtil.getString(this, Constants.APP_USER_CARD_ID, null));
        return new CommonPaginationAction(this, 1, 10, Constants.URL_GET_MY_COUPONS, parameters, Coupon.class, "coupons");
    }

    public void showDetailActivity(Coupon coupon){
        Intent intent = new Intent(this, CouponDetailActivity.class);
        intent.putExtra(CouponDetailActivity.INTENT_EXTRA_CARD_ID, PreferenceUtil.getString(this, Constants.APP_USER_CARD_ID, null));
        intent.putExtra(CouponDetailActivity.INTENT_EXTRA_COUPON_ID, coupon.getId());
        startActivity(intent);
    }

    public View getListItemView(Coupon item, View convertView, ViewGroup parent, LayoutInflater inflater) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate( R.layout.view_list_item_my_coupons, parent, false);
            holder = new ViewHolder();
            holder.couponThumbnail = (CustomizeImageView) convertView.findViewById(R.id.id_coupon_thumbnail);
            holder.expireDate = (TextView) convertView.findViewById(R.id.id_txt_expire_date);
            holder.description = (TextView) convertView.findViewById(R.id.id_txt_coupon_desc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.couponThumbnail.loadImage(item.getImageUrl());
        holder.description.setText(item.getDesc());
        holder.expireDate.setText(String.format(getString(R.string.my_coupons_expire_date), item.getStartTime(), item.getEndTime()));

        if(Build.VERSION.SDK_INT > 10) {
            convertView.findViewById(R.id.id_content).setAlpha(1f);
            if (item.getUseCount() > 0) {
                convertView.findViewById(R.id.id_content).setAlpha(0.3f);
            }
        }

        return convertView;
    }

    class ViewHolder {
        CustomizeImageView couponThumbnail;
        TextView  expireDate;
        TextView  description;
    }
}
