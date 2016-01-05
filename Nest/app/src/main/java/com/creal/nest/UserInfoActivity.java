package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.views.HeaderView;

public class UserInfoActivity extends Activity {
    public static final int REQUEST_CODE = 401;
    public static final String INTENT_EXTRA_RESULT = "user_exited";
    private TextView mCardId;
    private TextView mUserName;
    private TextView mUserSex;
    private TextView mUserPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.user_info);
        headerView.setTitleLeft();

        mCardId = (TextView)findViewById(R.id.id_txt_user_card);
        mUserName = (TextView)findViewById(R.id.id_txt_user_name);
        mUserSex = (TextView)findViewById(R.id.id_txt_user_sex);
        mUserPhone = (TextView)findViewById(R.id.id_txt_user_phone);
    }

    public void onExitClick(View view){
        PreferenceUtil.removeKey(this, Constants.APP_USER_AUTHORIZED);
        Intent result = new Intent();
        result.putExtra(INTENT_EXTRA_RESULT, true);
        setResult(R.layout.activity_user_info, result);
        finish();
    }

    public void onChangeCard(View view){
        PreferenceUtil.removeKey(this, Constants.APP_USER_AUTHORIZED);
        onExitClick(view);
    }
}
