package com.yanis.yc_ui_fragment_tabhost;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yanis.yc_ui_fragment_tabhost.views.PhotoPager;

import java.util.Arrays;
import java.util.List;


public class FragmentHome extends Fragment{
    private static final String TAG = "XYK-FragmentHome";
	private PhotoPager mViewPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View homeView =  inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager = (PhotoPager)homeView.findViewById(R.id.home_photos_pager);
        initAdPhotos();
        return homeView;
	}

    private void initAdPhotos(){
        mViewPager.addPhotos(getChildFragmentManager(), Arrays.asList(new String[]{"", "", "", ""}));
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "onAttach(Activity)");
    }
}