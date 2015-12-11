package com.creal.nest.property;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.creal.nest.R;
import com.creal.nest.views.HeaderView;

public class PropertyRepairActivity extends Activity {

    private static final String TAG = "XYK-LatestActivities";

    private RadioButton mBtnCommon;
    private RadioButton mBtnPersonal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_repair);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.property_repair);

        mBtnCommon = (RadioButton)findViewById(R.id.id_btn_common);
        mBtnPersonal = (RadioButton)findViewById(R.id.id_btn_personal);
        mBtnCommon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mBtnPersonal.setChecked(false);
                }
            }
        });
        mBtnPersonal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mBtnCommon.setChecked(false);
                }
            }
        });
    }


    public void onChangeVerificatoinCodeClick(View view) {

    }

    public void onSubmitClick(View view) {

    }
}
