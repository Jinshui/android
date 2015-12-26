package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.creal.nest.views.HeaderView;

import java.io.File;
import java.net.URISyntaxException;

public class ShopDetailActivity extends Activity {

    private static final String TAG = "XYK-MyCouponsActivity";
    private Button mBtnPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle("咖啡之巽-咖啡厅");

        mBtnPhone = (Button)findViewById(R.id.id_txt_phone);
    }


    private boolean isInstallByread(String packageName)
    {
        return new File("/data/data/" + packageName).exists();
    }



    public void onGoToMapClick(View view){
        String label = "比尔斯烤肉";
        String uriBegin = "geo:38.864293,115.502779";
        String query = "38.864293,115.502779(" + label + ")";
        String encodedQuery = Uri.encode( query  );
        String uriString = uriBegin + "?q=" + encodedQuery;
        Uri uri = Uri.parse( uriString );
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity( intent );
    }

    public void onDialPhoneClick(View view){
        Uri uri = Uri.parse("tel:" + mBtnPhone.getText().toString());
        Intent it = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(it);
    }
}
