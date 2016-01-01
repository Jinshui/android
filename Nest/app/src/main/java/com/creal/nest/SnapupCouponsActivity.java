package com.creal.nest;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
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

public class SnapupCouponsActivity extends PTRListActivity<Coupon> {


    public int getTitleResId(){
        return R.string.snapup_coupons_title;
    }

    protected boolean initOnResume(){
        return true;
    }

    public PaginationAction<Coupon> getPaginationAction(){
        return new CommonPaginationAction(this, 1, 10, Constants.URL_GET_COUPONS, null, Coupon.class, "coupons");
    }

    public void showDetailActivity(Coupon coupon){
        if(!coupon.isActive())
            return;
        final Dialog progressDialog = UIUtil.showLoadingDialog(this, getString(R.string.loading), false);
        Map parameters = new HashMap();
        parameters.put("coupons_id", coupon.getId());
        CommonObjectAction getCouponDetailAction = new CommonObjectAction(this, Constants.URL_GET_COUPON_DETAIL, parameters, Coupon.class);
        getCouponDetailAction.execute(new AbstractAction.UICallBack<Coupon>() {
            public void onSuccess(Coupon result) {
                Intent intent = new Intent(SnapupCouponsActivity.this, SnapCouponDetailActivity.class);
                intent.putExtra(SnapCouponDetailActivity.INTENT_EXTRA_COUPON, result);
                startActivity(intent);
                progressDialog.dismiss();
            }

            public void onFailure(AbstractAction.ActionError error) {
                Toast.makeText(SnapupCouponsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

    public View getListItemView(final Coupon coupon, View convertView, ViewGroup parent, LayoutInflater inflater){
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate( R.layout.view_list_item_snap_coupons, parent, false);
            holder = new ViewHolder();
            holder.couponThumbnail = (CustomizeImageView) convertView.findViewById(R.id.id_coupon_thumbnail);
            holder.total = (TextView) convertView.findViewById(R.id.id_txt_snapup_coupons_total);
            holder.remaining = (TextView) convertView.findViewById(R.id.id_txt_snapup_coupons_remaining);
            holder.name = (TextView) convertView.findViewById(R.id.id_txt_snapup_coupons_name);
            holder.snapNowBtn = (TextView) convertView.findViewById(R.id.id_coupon_snap_now);
            holder.time = (TextView) convertView.findViewById(R.id.id_coupon_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.couponThumbnail.loadImage(coupon.getImageUrl());
        holder.total.setText(String.format(getString(R.string.snapup_coupons_total), coupon.getTotal()));
        holder.remaining.setText(String.format(getString(R.string.snapup_coupons_remaining), coupon.getRest()));
        holder.name.setText(coupon.getName());
        holder.time.setText(coupon.getTime());

        if(coupon.isActive()) {
            holder.snapNowBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String cardId = PreferenceUtil.getString(SnapupCouponsActivity.this, Constants.APP_USER_CARD_ID, null);
                    final Dialog progressDialog = UIUtil.showLoadingDialog(SnapupCouponsActivity.this, getString(R.string.loading), false);
                    Map parameters = new HashMap();
                    parameters.put(Constants.KEY_CARD_ID, cardId);
                    parameters.put("coupons_id", coupon.getId());
                    CommonObjectAction action = new CommonObjectAction(getBaseContext(), Constants.URL_RECEIVE_COUPONS, parameters, ReceiveCouponResult.class);
                    action.execute(new AbstractAction.UICallBack<ReceiveCouponResult>() {
                        public void onSuccess(ReceiveCouponResult result) {
                            Intent intent = new Intent(SnapupCouponsActivity.this, SnapCouponDialogActivity.class);
                            coupon.setValue(result.getValue());
                            intent.putExtra(SnapCouponDialogActivity.INTENT_EXTRA_COUPON, coupon);
                            startActivity(intent);
                            progressDialog.dismiss();
                        }
                        public void onFailure(AbstractAction.ActionError error) {
                            progressDialog.dismiss();
                            Toast.makeText(SnapupCouponsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }

        if(Build.VERSION.SDK_INT > 10) {
            convertView.findViewById(R.id.id_header).setAlpha(1f);
            convertView.findViewById(R.id.id_body).setAlpha(1f);
            if (!coupon.isActive()) {
                convertView.findViewById(R.id.id_header).setAlpha(0.3f);
                convertView.findViewById(R.id.id_body).setAlpha(0.3f);
            }
        }

        return convertView;
    }

    class ViewHolder {
        CustomizeImageView couponThumbnail;
        TextView  total;
        TextView  remaining;
        TextView  name;
        TextView  snapNowBtn;
        TextView  time;
    }
}
