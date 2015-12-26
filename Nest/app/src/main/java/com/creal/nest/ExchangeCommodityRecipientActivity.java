package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.creal.nest.views.HeaderView;

public class ExchangeCommodityRecipientActivity extends Activity {

    private static final String TAG = "XYK-MyCouponsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_exchange_commodity_recipient_info);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.exchange_commit_title);
    }

    public void onChangeVerificatoinCodeClick(View view) {

    }

    public void onSubmitClick(View view) {
        Intent intent = new Intent(this, ExchangeSuccDialog.class);
        intent.putExtra("from", "points_mall");
        intent.putExtra("btnText", getString(R.string.back_to_points_mall));
        intent.putExtra("message", getString(R.string.exchange_commodity_succ_desc));
        startActivity(intent);
        finish();
    }

    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
