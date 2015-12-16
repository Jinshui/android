package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.LoginAction;
import com.creal.nest.views.HeaderView;

public class UserInfoActivity extends Activity {
    private final static String tag = "N-LoginActivity";
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
}