package com.creal.nest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.creal.nest.R;
import com.creal.nest.views.CustomizeImageView;

public class FragmentHomePager extends Fragment {
	private static final String tag = "XYK-FragmentHomePager";
	private CustomizeImageView mImageView;
	private int mResId;//TODO: test resource Id, can be removed after test
	private String mFileUrl;

	public FragmentHomePager(){
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFileUrl = getArguments().getString("fileUrl");
		mResId = getArguments().getInt("resId");
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mImageView = (CustomizeImageView) inflater.inflate(R.layout.view_custom_image, container, false);
		return mImageView;
	}
	
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null) {
			mFileUrl = savedInstanceState.getString("fileUrl");
			mResId = savedInstanceState.getInt("resId");
		}
		updateUI();
	}
	
	private  void updateUI(){
		Log.d(tag, "updateUI() with url " + (mFileUrl != null ? mFileUrl : ""));
		if(mFileUrl == null) {
			if(mResId != 0){
				mImageView.setBackgroundResource(mResId);
			}
			return;
		}else {
			mImageView.loadImage(mFileUrl);
		}
		mImageView.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent showAdDetailIntent = new Intent();
				getActivity().startActivity(showAdDetailIntent);
			}
		});
	}
	
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("fileUrl", mFileUrl);
		outState.putInt("resId", mResId);
	}
}
