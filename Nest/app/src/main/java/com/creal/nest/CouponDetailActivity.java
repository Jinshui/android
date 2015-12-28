package com.creal.nest;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.GetCouponDetailAction;
import com.creal.nest.actions.JSONConstants;
import com.creal.nest.model.Coupon;
import com.creal.nest.util.UIUtil;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.HeaderView;

public class CouponDetailActivity extends Activity {

    private static final String TAG = "XYK-MyCouponsActivity";
    public static final String INTENT_EXTRA_CARD_ID = JSONConstants.KEY_CARD_ID;
    public static final String INTENT_EXTRA_COUPON_ID = "coupon_id";

    private CustomizeImageView mImageView;
    private TextView mNameView;
    private TextView mDescView;
    private TextView mTimeRangeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_detail);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.coupon_detail);


        mImageView = (CustomizeImageView)findViewById(R.id.id_coupon_thumbnail);
        mNameView = (TextView)findViewById(R.id.id_txt_coupon_name);
        mDescView = (TextView)findViewById(R.id.id_txt_coupon_desc);
        mTimeRangeView = (TextView)findViewById(R.id.id_txt_coupon_time_range);

        init();
    }

    private void init(){
        String cardId = getIntent().getStringExtra(INTENT_EXTRA_CARD_ID);
        String couponId = getIntent().getStringExtra(INTENT_EXTRA_COUPON_ID);
        final Dialog dialog = UIUtil.showLoadingDialog(this, getString(R.string.loading), true);
        GetCouponDetailAction getCouponDetailAction = new GetCouponDetailAction(this, cardId, couponId);
        getCouponDetailAction.execute(new AbstractAction.UICallBack<Coupon>() {
            public void onSuccess(Coupon coupon) {
                mImageView.loadImage(coupon.getImageUrl());
                mNameView.setText(coupon.getName());
                mDescView.setText(coupon.getDesc());
                mTimeRangeView.setText(String.format(getString(R.string.coupon_date_range), coupon.getStartTime(), coupon.getEndTime()));
                dialog.dismiss();
            }

            public void onFailure(AbstractAction.ActionError error) {
                dialog.dismiss();
                Toast.makeText(CouponDetailActivity.this, R.string.load_error, Toast.LENGTH_LONG).show();
            }
        });
    }
}
