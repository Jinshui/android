package com.creal.nest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.GetAdAction;
import com.creal.nest.model.Ad;
import com.creal.nest.property.PropertyManagementActivity;
import com.creal.nest.views.PhotoPager;

import java.util.ArrayList;
import java.util.List;


public class FragmentHome extends Fragment{
    private static final String TAG = "XYK-FragmentHome";
	private PhotoPager mViewPager;
    private ArrayList<Ad> mAds;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null)
            mAds = savedInstanceState.getParcelable("ads");
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View mHomeView = inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager = (PhotoPager) mHomeView.findViewById(R.id.home_photos_pager);
        initAdPhotos();
        addListener(mHomeView);
        return mHomeView;
	}

    private void initAdPhotos(){
        if(mAds != null) {
            mViewPager.addPhotos(getChildFragmentManager(), mAds);
            return;
        }
        GetAdAction getAdAction = new GetAdAction(getActivity());
        mViewPager.showLoading();
        getAdAction.execute(new AbstractAction.UICallBack<List<Ad>>() {
            public void onSuccess(List<Ad> result) {
                Log.d(TAG, "onSuccess: " + result.size());
                mAds = (ArrayList)result;
                mViewPager.addPhotos(getChildFragmentManager(), mAds);
            }

            public void onFailure(AbstractAction.ActionError error) {
                mViewPager.hideLoading();
            }
        });
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("ads", mAds);
    }


    private void addListener(View homeView){
        homeView.findViewById(R.id.id_btn_my_coupons).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyCouponsActivity.class);
                startActivity(intent);
            }
        });
        homeView.findViewById(R.id.id_btn_pay).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QRCodeActivity.class);
                startActivity(intent);
            }
        });
        homeView.findViewById(R.id.id_btn_recharge).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RechargeActivity.class);
                startActivity(intent);
            }
        });
        homeView.findViewById(R.id.id_btn_get_coupon).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SnapupCouponsActivity.class);
                startActivity(intent);
            }
        });
        homeView.findViewById(R.id.id_btn_activity).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LatestActivitiesActivity.class);
                startActivity(intent);
            }
        });
        homeView.findViewById(R.id.id_btn_try_lucky).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TryLuckyActivity.class);
                startActivity(intent);
            }
        });
        homeView.findViewById(R.id.id_btn_try_points_mall).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PointsMallActivity.class);
                startActivity(intent);
            }
        });
        homeView.findViewById(R.id.id_btn_ingots).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IngotsMallActivity.class);
                startActivity(intent);
            }
        });
        homeView.findViewById(R.id.id_btn_property).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PropertyManagementActivity.class);
                startActivity(intent);
            }
        });
        homeView.findViewById(R.id.id_btn_my_custom_service).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
            }
        });
    }


    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }
}