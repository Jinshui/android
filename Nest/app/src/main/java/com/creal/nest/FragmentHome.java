package com.creal.nest;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.CommonObjectAction;
import com.creal.nest.actions.GetAdAction;
import com.creal.nest.model.Ad;
import com.creal.nest.model.HouseInfo;
import com.creal.nest.property.PropertyManagementActivity;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.UIUtil;
import com.creal.nest.views.PhotoPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
                mAds = (ArrayList) result;
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
            }
        });
        homeView.findViewById(R.id.id_btn_my_custom_service).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showHouseInfo(){
        String cardId = PreferenceUtil.getString(getActivity(), Constants.APP_USER_CARD_ID, null);
        Map parameters = new HashMap<>();
        parameters.put(Constants.KEY_CARD_ID, cardId);
        final Dialog dialog = UIUtil.showLoadingDialog(getActivity(), getString(R.string.loading), true);
        CommonObjectAction action = new CommonObjectAction(getActivity(), Constants.URL_BIND_PROPERTY, parameters, HouseInfo.class);
        action.execute(new AbstractAction.UICallBack<HouseInfo>() {
            public void onSuccess(HouseInfo info) {
                dialog.dismiss();
                if(info != null) {
                    Intent intent = new Intent(getActivity(), PropertyManagementActivity.class);
                    intent.putExtra(PropertyManagementActivity.INTENT_EXTRA_HOUSE_INFO, info);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), R.string.property_management_disabled, Toast.LENGTH_LONG).show();
                }
            }

            public void onFailure(AbstractAction.ActionError error) {
                dialog.dismiss();
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}