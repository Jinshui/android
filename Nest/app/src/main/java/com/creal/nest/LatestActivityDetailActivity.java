package com.creal.nest;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.model.SalesActivity;
import com.creal.nest.views.CustomizeImageView;
import com.creal.nest.views.HeaderView;

public class LatestActivityDetailActivity extends Activity {

    private static final String TAG = "XYK-LatestActivity";

    public static final String INTENT_EXTRA_SALES_ACTIVITY = "salesActivity";
    private TextView mContent;
    private TextView mTitle;
    private CustomizeImageView mImgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_activity_detail);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();

        SalesActivity salesActivity = getIntent().getParcelableExtra(INTENT_EXTRA_SALES_ACTIVITY);
        headerView.setTitle(salesActivity.getTitle());

        mImgView = (CustomizeImageView) findViewById(R.id.id_coupon_thumbnail);
        mTitle = (TextView) findViewById(R.id.id_txt_activity_title);
        mContent = (TextView) findViewById(R.id.id_txt_activity_content);

        mImgView.loadImage(salesActivity.getImageUrl());
        mTitle.setText(salesActivity.getTitle());
        if(!TextUtils.isEmpty(salesActivity.getContent()))
            mContent.setText(salesActivity.getContent());
    }
}