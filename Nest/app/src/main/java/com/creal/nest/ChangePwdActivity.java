package com.creal.nest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.creal.nest.views.HeaderView;

public class ChangePwdActivity extends Activity {

    private EditText mOldPwd;
    private EditText mNewPwd;
    private EditText mNewPwdAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.change_pwd);
        headerView.setTitleLeft();

        mOldPwd = (EditText)findViewById(R.id.id_txt_old_pwd);
        mNewPwd = (EditText)findViewById(R.id.id_txt_new_pwd);
        mNewPwdAgain = (EditText)findViewById(R.id.id_txt_new_pwd_again);
    }

    public void onSaveChangesClick(View view){
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
        showToast("成功修改密码");
        finish();
    }

    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
