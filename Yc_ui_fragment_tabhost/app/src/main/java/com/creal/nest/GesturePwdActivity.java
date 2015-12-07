package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.R;
import com.creal.nest.views.GestureLockViewGroup;


public class GesturePwdActivity extends Activity {

    private GestureLockViewGroup mGestureLockViewGroup;
    private ImageView mUserImage;
    private TextView mUserName;
    private TextView mSkipPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_pwd);
        mUserImage = (ImageView) findViewById(R.id.id_img_user_photo);
        mUserName = (TextView) findViewById(R.id.id_text_user_name);
        mSkipPwd = (TextView) findViewById(R.id.id_btn_skip_gesture_pwd);

        mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.id_gestureLockViewGroup);
        mGestureLockViewGroup.setAnswer(new int[]{1, 2, 3, 4, 5});
        mGestureLockViewGroup.setOnGestureLockViewListener(new GestureLockViewGroup.OnGestureLockViewListener() {

            @Override
            public void onUnmatchedExceedBoundary() {
                Toast.makeText(GesturePwdActivity.this, "错误5次...", Toast.LENGTH_SHORT).show();
                mGestureLockViewGroup.setUnMatchExceedBoundary(5);
            }

            @Override
            public void onGestureEvent(boolean matched) {
                Toast.makeText(GesturePwdActivity.this, matched + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBlockSelected(int cId) {
            }
        });

        mSkipPwd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GesturePwdActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
