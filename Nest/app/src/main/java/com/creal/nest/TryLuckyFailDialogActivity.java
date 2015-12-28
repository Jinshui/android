package com.creal.nest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class TryLuckyFailDialogActivity extends Activity {

    private static final String TAG = "XYK-SnapCouponDialog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_try_lucky_fail);
    }

    public void onTryAgainClick(View view){
        finish();
    }
}
