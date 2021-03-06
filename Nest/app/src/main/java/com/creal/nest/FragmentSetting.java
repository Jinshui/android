package com.creal.nest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.views.HeaderView;

public class FragmentSetting extends Fragment {

    TextView mGeneralInfo;
    TextView mChangePwd;
    TextView mPointsHistory;
    TextView mIngotsHistory;
//    TextView mAppSettings;
    TextView mCheckUpdates;
    TextView mHelp;
    TextView mAboutUs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        HeaderView headerView = (HeaderView) view.findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.settings);
        headerView.setTitleCenter();

        mGeneralInfo = (TextView) view.findViewById(R.id.id_text_general_info);
        mChangePwd = (TextView) view.findViewById(R.id.id_text_change_pwd);
        mPointsHistory = (TextView) view.findViewById(R.id.id_text_points_exchange_history);
        mIngotsHistory = (TextView) view.findViewById(R.id.id_text_ingots_exchange_history);
//        mAppSettings = (TextView) view.findViewById(R.id.id_text_app_settings);
        mCheckUpdates = (TextView) view.findViewById(R.id.id_text_check_updates);
        mHelp = (TextView) view.findViewById(R.id.id_text_settings_help);
        mAboutUs = (TextView) view.findViewById(R.id.id_text_about);

        addListeners();
        return view;
    }

    private void addListeners(){
        mGeneralInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                startActivityForResult(intent, UserInfoActivity.REQUEST_CODE);
            }
        });
        mChangePwd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePwdActivity.class);
                startActivity(intent);
            }
        });
        mPointsHistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ExchangePointHistoryActivity.class);
                startActivity(intent);
            }
        });
        mIngotsHistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IngotsExchangeHistoryActivity.class);
                startActivity(intent);
            }
        });
//        mAppSettings.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Not implemented yet!", Toast.LENGTH_SHORT).show();
//            }
//        });
        mCheckUpdates.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Not implemented yet!", Toast.LENGTH_SHORT).show();
            }
        });
        mHelp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
            }
        });
        mAboutUs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null && requestCode == UserInfoActivity.REQUEST_CODE){
            if(data.getBooleanExtra(UserInfoActivity.INTENT_EXTRA_RESULT, false)){
                getActivity().finish();
            }
        }
    }
}
