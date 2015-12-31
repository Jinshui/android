package com.creal.nest.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.creal.nest.FragmentHomePager;
import com.creal.nest.R;
import com.creal.nest.model.Ad;

import java.util.ArrayList;
import java.util.List;

public class PhotoPager extends LinearLayout implements ViewPager.OnPageChangeListener {
    public static final String tag = "TXK-PhotoPager";

	private ViewPager mViewPager;
	private LinearLayout mPagerIndicatorsContainer;
    private List<ImageView> mIndicators;
    private ProgressBar mPrgressBar;
	public PhotoPager(Context context, AttributeSet attrs) {
		super(context, attrs);
        Log.d(tag, "PhotoPager");
		LayoutInflater.from(context).inflate(R.layout.home_ad_photos, this);
        mPrgressBar = (ProgressBar)findViewById(R.id.id_pager_loading);
		mViewPager = (ViewPager)findViewById(R.id.id_photos_pager);
        mPagerIndicatorsContainer = (LinearLayout)findViewById(R.id.photo_pager_indicator);
	}

    public void showLoading(){
        mPrgressBar.setVisibility(VISIBLE);
    }
    public void hideLoading(){
        mPrgressBar.setVisibility(GONE);
    }

	public void addPhotos(FragmentManager fm, List<Ad> ads){
        Log.d(tag, "addPhotos: " + ads.size());
        mIndicators = new ArrayList<ImageView>();
        for(int i=0; i<ads.size(); i++){
            ImageView imageView = new ImageView(getContext());
            LayoutParams layoutParams = new LayoutParams(20, 20);
            layoutParams.setMargins(7,0,7,0);
            imageView.setLayoutParams(layoutParams);
            if(i == 0){
                imageView.setBackgroundResource(R.drawable.home_pager_indicator_focused);
            }else{
                imageView.setBackgroundResource(R.drawable.home_pager_indicator_unfocused);
            }
            mIndicators.add(imageView);
            mPagerIndicatorsContainer.addView(imageView);
        }

        mViewPager.setAdapter(new PhotoPagerAdapter(fm, ads));
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setCurrentItem(0);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	}

	@Override
	public void onPageSelected(int position) {
        Log.d(tag, "onPageSelected");
        int index = position % mIndicators.size();
        for(int i=0; i<mIndicators.size(); i++){
            if(i == index){
                mIndicators.get(i).setBackgroundResource(R.drawable.home_pager_indicator_focused);
            }else{
                mIndicators.get(i).setBackgroundResource(R.drawable.home_pager_indicator_unfocused);
            }
        }
    }

	@Override
	public void onPageScrollStateChanged(int state) {
	}

    private class PhotoPagerAdapter extends FragmentStatePagerAdapter {
        public static final String tag = "TXK-PhotoPagerAdapter";
        List<Ad> ads;
        public PhotoPagerAdapter(FragmentManager fm, List<Ad> ads) {
            super(fm);
            Log.d(tag, "PhotoPagerAdapter");
            this.ads = ads;
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(tag, "getItem");
            FragmentHomePager fragment = new FragmentHomePager();
            Bundle bundle = new Bundle();
            bundle.putInt("resId", R.drawable.test_home_ad1);
            bundle.putParcelable("ad", ads.get(position));
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return ads.size();
        }
    }
}
