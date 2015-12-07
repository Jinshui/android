package com.creal.nest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.creal.nest.R;
import com.creal.nest.views.HeaderView;

public class BarcodeActivity extends Activity {

    private static final String TAG = "XYK-MAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.pay);
        headerView.setRightText(R.string.pay_description);
        headerView.setRightTextListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        findViewById(R.id.id_btn_cancel).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}
