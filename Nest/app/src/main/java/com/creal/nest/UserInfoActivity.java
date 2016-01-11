package com.creal.nest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.CommonObjectAction;
import com.creal.nest.model.UserInfo;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.UIUtil;
import com.creal.nest.views.HeaderView;

import java.util.HashMap;
import java.util.Map;

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
        init();
    }

    private void init(){
        Map map = new HashMap();
        map.put(Constants.KEY_CARD_ID, PreferenceUtil.getString(this, Constants.APP_USER_CARD_ID, null));
        CommonObjectAction action = new CommonObjectAction(this, Constants.URL_GET_USER_INFO, map, UserInfo.class );
        final Dialog dialog = UIUtil.showLoadingDialog(this, getString(R.string.loading), false);
        action.execute(new AbstractAction.UICallBack<UserInfo>() {
            public void onSuccess(UserInfo userInfo) {
                mCardId.setText(userInfo.getCardNum());
                mUserSex.setText(userInfo.getGender());
                mUserName.setText(userInfo.getName());
                mUserPhone.setText(userInfo.getPhone());
                dialog.dismiss();
            }

            public void onFailure(AbstractAction.ActionError error) {
                Toast.makeText(UserInfoActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
    }

    public void onExitClick(View view){
        PreferenceUtil.removeKey(this, Constants.APP_USER_AUTHORIZED);
        Intent result = new Intent();
        result.putExtra(INTENT_EXTRA_RESULT, true);
        setResult(R.layout.activity_user_info, result);
        finish();
    }

    public void onChangeCard(View view){
        PreferenceUtil.removeKey(this, Constants.APP_BINDING_KEY);
        onExitClick(view);
    }
}
