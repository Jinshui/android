package com.yanis.yc_ui_fragment_tabhost.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yanis.yc_ui_fragment_tabhost.FragmentHomePager;
import com.yanis.yc_ui_fragment_tabhost.R;

import java.util.ArrayList;
import java.util.List;

public class PhotoPager extends LinearLayout implements ViewPager.OnPageChangeListener {

	private ViewPager mViewPager;
	private LinearLayout mPagerIndicatorsContainer;
    private List<ImageView> mIndicators;

	public PhotoPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.home_ad_photos, this);
		mViewPager = (ViewPager)findViewById(R.id.id_photos_pager);
        mPagerIndicatorsContainer = (LinearLayout)findViewById(R.id.photo_pager_indicator);
	}

	public void addPhotos(FragmentManager fm, List<String> urls){
        mIndicators = new ArrayList<ImageView>();
        for(int i=0; i<urls.size(); i++){
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

        mViewPager.setAdapter(new PhotoPagerAdapter(fm, urls));
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setCurrentItem(0);
	}

	public ViewPager getPhotoViewPager(){
		return (ViewPager)findViewById(R.id.id_photos_pager);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
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
        public static final String tag = "TT-PhotoPagerAdapter";
        List<String> photoUrls;
        public PhotoPagerAdapter(FragmentManager fm, List<String> photoUrls) {
            super(fm);
            this.photoUrls = photoUrls;
        }

        @Override
        public Fragment getItem(int position) {
            FragmentHomePager fragment = new FragmentHomePager();
            Bundle bundle = new Bundle();
            //TODO: uncomment this line
//            bundle.putString("fileUrl", photoUrls.get(position));
            bundle.putInt("resId", R.drawable.home_ad1);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return photoUrls.size();
        }
    }
}
