package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.creal.nest.actions.ParallelTask;
import com.creal.nest.util.PreferenceUtil;

public class WelcomeActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		initialize();
	}
	

	private void initialize(){
		new ParallelTask<Void>() {
			protected Void doInBackground(Void... params) {
				try {
//					PreferenceUtil.saveString(WelcomeActivity.this, Constants.APP_USER_AUTHORIZED, Boolean.FALSE.toString());
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
				return null;
			}
			
			protected void onPostExecute(Void result){
				startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
//				showNext();
			}
		}.execute();
	}

	private void showNext(){
		String key = PreferenceUtil.getString(WelcomeActivity.this, Constants.APP_BINDING_KEY, null);
		if(key == null){
			startActivity(new Intent(WelcomeActivity.this, PhoneBinderActivity.class));
		}else{
			String authorized = PreferenceUtil.getString(WelcomeActivity.this, Constants.APP_USER_AUTHORIZED, null);
			if(Boolean.TRUE.toString().equalsIgnoreCase(authorized)){
				String gesturePwd = PreferenceUtil.getString(WelcomeActivity.this, Constants.APP_USER_GESTURE_PWD, null);
				if(gesturePwd != null){
					Intent intent = new Intent(WelcomeActivity.this, GesturePwdActivity.class);
					intent.putExtra(GesturePwdActivity.INTENT_EXTRA_ACTION_TYPE, GesturePwdActivity.State.VERIFY_PWD.name());
					startActivity(intent);
				}else{
					startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
				}
			}else{
				startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
			}
		}
		finish();
	}
}
