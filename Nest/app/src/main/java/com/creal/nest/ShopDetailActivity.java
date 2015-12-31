package com.creal.nest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.GetShopDetailAction;
import com.creal.nest.model.Shop;
import com.creal.nest.util.MapDistance;
import com.creal.nest.util.UIUtil;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.HeaderView;

public class ShopDetailActivity extends Activity {

    private static final String TAG = "XYK-MyCouponsActivity";
    public static final String INTENT_EXTRA_SHOP_ID = "shop_id";
//    private Button mBtnPhone;CustomizeImageView
    private HeaderView mHeaderView;
    private CustomizeImageView mImage;
    private TextView mImgTitle;
    private TextView mDistance;
    private TextView mTitle;
    private TextView mCategory;
    private TextView mDesc;
    private Shop mShop;
    private LocationManager mLocationManager;
    private Location mLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        mHeaderView = (HeaderView) findViewById(R.id.header);
        mHeaderView.hideRightImage();
        mImage = (CustomizeImageView)findViewById(R.id.id_coupon_thumbnail);
        mImgTitle = (TextView)findViewById(R.id.id_text_shop_img_title);
        mDistance = (TextView)findViewById(R.id.id_text_shop_distance);
        mTitle = (TextView)findViewById(R.id.id_text_shop_title);
        mCategory = (TextView)findViewById(R.id.id_text_shop_category);
        mDesc = (TextView)findViewById(R.id.id_text_shop_desc);

//        mBtnPhone = (Button)findViewById(R.id.id_txt_phone);
        String shopId = getIntent().getStringExtra(INTENT_EXTRA_SHOP_ID);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        init(shopId);
    }

    private void init(final String shopId){
        GetShopDetailAction getShopDetailAction = new GetShopDetailAction(this, shopId);
        final Dialog dialog = UIUtil.showLoadingDialog(this, getString(R.string.loading), true);
        getShopDetailAction.execute(new AbstractAction.UICallBack<Shop>() {
            public void onSuccess(Shop shop) {
                mHeaderView.setTitle(shop.getTitle());
                mImage.loadImage(shop.getImageUrl());
                mImgTitle.setText(shop.getTitle());
                double lat1 = 0, lon1 = 0, lat2 = 0, lon2 = 0;
                if(! TextUtils.isEmpty(shop.getLatitude()))
                    lat1 = Double.valueOf(shop.getLatitude());
                if(! TextUtils.isEmpty(shop.getLongitude()))
                    lon1 = Double.valueOf(shop.getLongitude());
                if(lat1 != 0 && lon1 != 0){
                    mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    String distance = MapDistance.getLongDistance(lon1, lat1, mLocation.getLongitude(), mLocation.getLatitude());
                    mDistance.setText(distance + " M");
                }
                mTitle.setText(shop.getTitle());
                mCategory.setText(shop.getCategory());
                mDesc.setText(shop.getDetail());
                dialog.dismiss();
            }
            public void onFailure(AbstractAction.ActionError error) {
                dialog.dismiss();
                Toast.makeText(ShopDetailActivity.this, R.string.load_failed, Toast.LENGTH_LONG).show();
            }
        });

    }

//    public void onGoToMapClick(View view){
//        String label = "比尔斯烤肉";
//        String uriBegin = "geo:38.864293,115.502779";
//        String query = "38.864293,115.502779(" + label + ")";
//        String encodedQuery = Uri.encode( query  );
//        String uriString = uriBegin + "?q=" + encodedQuery;
//        Uri uri = Uri.parse( uriString );
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity( intent );
//    }

//    public void onDialPhoneClick(View view){
//        Uri uri = Uri.parse("tel:" + mBtnPhone.getText().toString());
//        Intent it = new Intent(Intent.ACTION_DIAL, uri);
//        startActivity(it);
//    }
}
