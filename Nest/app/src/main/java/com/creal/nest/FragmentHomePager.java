package com.creal.nest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.creal.nest.model.Ad;
import com.creal.nest.views.CustomizeImageView;

public class FragmentHomePager extends Fragment {
	private static final String tag = "XYK-FragmentHomePager";
	private CustomizeImageView mImageView;
	private int mResId;//TODO: test resource Id, can be removed after test
	private Ad ad;

	public FragmentHomePager(){
		Log.d(tag, "FragmentHomePager");
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        ad = getArguments().getParcelable("ad");
		mResId = getArguments().getInt("resId");
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(tag, "onCreateView");
		mImageView = (CustomizeImageView) inflater.inflate(R.layout.view_custom_image, container, false);
		mImageView.setBackgroundResource(R.drawable.icon_list_default_bg);
		return mImageView;
	}
	
	public void onActivityCreated(Bundle savedInstanceState){
        Log.d(tag, "onActivityCreated");
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null) {
            ad = savedInstanceState.getParcelable("ad");
			mResId = savedInstanceState.getInt("resId");
		}
		updateUI();
	}
	
	private  void updateUI(){
		Log.d(tag, "updateUI() with url " + (ad != null ? ad.getImageUrl() : ""));
		if(ad == null) {
			if(mResId != 0){
				mImageView.setBackgroundResource(mResId);
			}
			return;
		}else {
			mImageView.loadImage(ad.getImageUrl());
		}
		mImageView.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent showAdDetailIntent = new Intent(getActivity(), WebpageActivity.class);
                showAdDetailIntent.putExtra("url", ad.getUrl());
				getActivity().startActivity(showAdDetailIntent);
			}
		});
	}
	
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable("ad", ad);
		outState.putInt("resId", mResId);
	}
}
