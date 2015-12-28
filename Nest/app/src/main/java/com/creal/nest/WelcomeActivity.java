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
					Thread.sleep(3000);
				} catch (InterruptedException e) {
				}
				return null;
			}
			
			protected void onPostExecute(Void result){
				String key = PreferenceUtil.getString(WelcomeActivity.this, Constants.APP_BINDING_KEY, null);
				if(key == null){
					startActivity(new Intent(WelcomeActivity.this, PhoneBinderActivity.class));
				}else{
					String authorized = PreferenceUtil.getString(WelcomeActivity.this, Constants.APP_USER_AUTHORIZED, null);
					if(Boolean.TRUE.toString().equalsIgnoreCase(authorized)){
						startActivity(new Intent(WelcomeActivity.this, GesturePwdActivity.class));
					}else{
						startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
					}
				}
				finish();
			}
		}.execute();
	}
}
