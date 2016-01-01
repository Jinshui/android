package com.creal.nest;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.CommonObjectAction;
import com.creal.nest.actions.CommonPaginationAction;
import com.creal.nest.actions.PaginationAction;
import com.creal.nest.model.Coupon;
import com.creal.nest.model.ReceiveCouponResult;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.UIUtil;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.ptr.PTRListActivity;

import java.util.HashMap;
import java.util.Map;

public class TryLuckyActivity extends PTRListActivity<Coupon> {

    public int getTitleResId(){
        return R.string.try_lucky;
    }

    protected boolean initOnResume(){
        return true;
    }

    public PaginationAction<Coupon> getPaginationAction(){
        return new CommonPaginationAction(this, 1, 10, Constants.URL_GET_LUCKY_COUPONS, null, Coupon.class, "coupons");
    }

    public View getListItemView(final Coupon coupon, View convertView, ViewGroup parent, LayoutInflater inflater){
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate( R.layout.view_list_item_try_lucky, parent, false);
            holder = new ViewHolder();
            holder.couponThumbnail = (CustomizeImageView) convertView.findViewById(R.id.id_coupon_thumbnail);
            holder.name = (TextView) convertView.findViewById(R.id.id_txt_lucky_coupons_name);
            holder.desc = (TextView) convertView.findViewById(R.id.id_txt_lucky_coupons_desc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.couponThumbnail.loadImage(coupon.getImageUrl());
        holder.name.setText(coupon.getName());
        holder.desc.setText(coupon.getDesc());

        convertView.findViewById(R.id.id_btn_try_lucky).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String cardId = PreferenceUtil.getString(getBaseContext(), Constants.APP_USER_CARD_ID, null);

                Map parameters = new HashMap();
                parameters.put(Constants.KEY_CARD_ID, cardId);
                parameters.put("coupons_id", coupon.getId());
                CommonObjectAction action = new CommonObjectAction(getBaseContext(), Constants.URL_RECEIVE_COUPONS, parameters, ReceiveCouponResult.class);

                final Dialog progressDialog = UIUtil.showLoadingDialog(TryLuckyActivity.this, getString(R.string.loading), false);
                action.execute(new AbstractAction.UICallBack<ReceiveCouponResult>() {
                    public void onSuccess(ReceiveCouponResult result) {
                        if(result.getReceiveId() > 0) {
                            Intent intent = new Intent(TryLuckyActivity.this, SnapCouponDialogActivity.class);
                            coupon.setValue(result.getValue());
                            intent.putExtra(SnapCouponDialogActivity.INTENT_EXTRA_COUPON, coupon);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(TryLuckyActivity.this, TryLuckyFailDialogActivity.class);
                            startActivity(intent);
                        }
                        progressDialog.dismiss();
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(TryLuckyActivity.this, TryLuckyFailDialogActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

        return convertView;
    }

    class ViewHolder {
        CustomizeImageView couponThumbnail;
        TextView  name;
        TextView  desc;
    }
}
