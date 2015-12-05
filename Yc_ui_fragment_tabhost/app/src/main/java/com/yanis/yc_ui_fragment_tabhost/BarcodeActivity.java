package com.yanis.yc_ui_fragment_tabhost;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.yanis.yc_ui_fragment_tabhost.views.HeaderView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
