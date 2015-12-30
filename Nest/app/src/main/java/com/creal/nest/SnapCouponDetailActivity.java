package com.creal.nest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.ReceiveCouponAction;
import com.creal.nest.model.Coupon;
import com.creal.nest.model.ReceiveCouponResult;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.UIUtil;
import com.creal.nest.views.HeaderView;

public class SnapCouponDetailActivity extends Activity {

    private static final String TAG = "XYK-SnapCouponDialog";
    public static final String INTENT_EXTRA_COUPON = "coupon";
    private TextView mAmount;
    private TextView mDesc;
    private TextView mDescBttom;
    private TextView mRemaining;
    private Coupon mCoupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_coupons_detail);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.snapup_coupons);
        mCoupon = getIntent().getParcelableExtra(INTENT_EXTRA_COUPON);

        mAmount = (TextView)findViewById(R.id.id_txt_amount);
        mDesc = (TextView)findViewById(R.id.id_txt_amount_desc);
        mRemaining = (TextView)findViewById(R.id.id_txt_remaining);
        mDescBttom = (TextView)findViewById(R.id.id_txt_desc_bottom);

        mAmount.setText(String.valueOf(mCoupon.getValue()));
        mDesc.setText(String.format(getString(R.string.snapup_coupons_desc),
                mCoupon.getTime(),
                mCoupon.getValue(),
                mCoupon.getTotal() == 0 ? mCoupon.getCount() : mCoupon.getTotal()));
        mDescBttom.setText(String.format(getString(R.string.snapup_coupons_desc_bottom),
                mCoupon.getTotal() == 0 ? mCoupon.getCount() : mCoupon.getTotal()));
        mRemaining.setText(String.valueOf(mCoupon.getRest()));
    }

    public void onSnapClick(View view){
        String cardId = PreferenceUtil.getString(this, Constants.APP_USER_CARD_ID, null);
        final Dialog progressDialog = UIUtil.createLoadingDialog(this, getString(R.string.loading), false);
        progressDialog.show();
        ReceiveCouponAction action = new ReceiveCouponAction(this, cardId, mCoupon.getId());
        action.execute(new AbstractAction.UICallBack<ReceiveCouponResult>() {
            public void onSuccess(ReceiveCouponResult result) {
                progressDialog.dismiss();
                Intent intent = new Intent(SnapCouponDetailActivity.this, SnapCouponDialogActivity.class);
                mCoupon.setValue(result.getValue());
                intent.putExtra(SnapCouponDialogActivity.INTENT_EXTRA_COUPON, mCoupon);
                startActivity(intent);
                finish();
            }

            public void onFailure(AbstractAction.ActionError error) {
                progressDialog.dismiss();
                Toast.makeText(SnapCouponDetailActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
