package com.creal.nest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.GetShopDetailAction;
import com.creal.nest.model.Shop;
import com.creal.nest.util.UIUtil;
import com.creal.nest.views.HeaderView;

public class ShopDetailActivity extends Activity {

    private static final String TAG = "XYK-MyCouponsActivity";
    public static final String INTENT_EXTRA_SHOP_ID = "shop_id";
    private Button mBtnPhone;
    private Shop mShop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle("咖啡之巽-咖啡厅");

        mBtnPhone = (Button)findViewById(R.id.id_txt_phone);
        String shopId = getIntent().getStringExtra(INTENT_EXTRA_SHOP_ID);
        init(shopId);
    }

    private void init(String shopId){
        GetShopDetailAction getShopDetailAction = new GetShopDetailAction(this, shopId);
        final Dialog dialog = UIUtil.showLoadingDialog(this, getString(R.string.loading), true);
        getShopDetailAction.execute(new AbstractAction.UICallBack<Shop>() {
            public void onSuccess(Shop result) {
                dialog.dismiss();
            }
            public void onFailure(AbstractAction.ActionError error) {
                dialog.dismiss();
                Toast.makeText(ShopDetailActivity.this, R.string.load_failed, Toast.LENGTH_LONG).show();
            }
        });

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
