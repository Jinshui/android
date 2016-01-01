package com.creal.nest;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.CommonObjectAction;
import com.creal.nest.actions.StringAction;
import com.creal.nest.balance.AccumulationHistoryActivity;
import com.creal.nest.model.CardInfo;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.UIUtil;
import com.creal.nest.views.HeaderView;

import java.util.HashMap;
import java.util.Map;


public class FragmentBalance extends Fragment {
    private static final String TAG = "XYK-FragmentBalance";

    private TextView mAmounts;
    private Button mRechargeHistory;
    private Button mRecharge;
    private TextView mMyPoints;
    private TextView mMyIngots;
    private int mRunningActions = 0;

    public FragmentBalance() {
        Log.d(TAG, "FragmentBalance()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_balance, container, false);

        HeaderView headerView = (HeaderView) view.findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.balance);
        headerView.setTitleCenter();

        mAmounts = (TextView)view.findViewById(R.id.id_text_amount);
        mRechargeHistory = (Button)view.findViewById(R.id.id_btn_recharge_history);
        mRecharge = (Button)view.findViewById(R.id.id_btn_recharge);
        mMyPoints = (TextView)view.findViewById(R.id.id_text_my_points);
        mMyIngots = (TextView)view.findViewById(R.id.id_text_my_ingots);
        addListeners();
        loadData();
        return view;
    }

    private void addListeners(){
        mRechargeHistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RechargeHistoryActivity.class);
                startActivity(intent);
            }
        });
        mRecharge.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RechargeActivity.class);
                startActivity(intent);
            }
        });
        mMyPoints.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AccumulationHistoryActivity.class);
                intent.putExtra(AccumulationHistoryActivity.ACCUMULATION_TYPE, AccumulationHistoryActivity.ACCUMULATION_TYPE_POINTS);
                startActivity(intent);
            }
        });
        mMyIngots.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AccumulationHistoryActivity.class);
                intent.putExtra(AccumulationHistoryActivity.ACCUMULATION_TYPE, AccumulationHistoryActivity.ACCUMULATION_TYPE_INGOTS);
                startActivity(intent);
            }
        });
    }

    private void loadData(){
        mRunningActions = 2;
        final Dialog dialog = UIUtil.showLoadingDialog(getActivity(), getString(R.string.loading), false);
        String cardId = PreferenceUtil.getString(getActivity(), Constants.APP_USER_CARD_ID, null);
        Map paras = new HashMap();
        paras.put(Constants.KEY_CARD_ID, cardId);
        CommonObjectAction<CardInfo> action = new CommonObjectAction<>(getActivity(), Constants.URL_GET_CARD_INFO, paras, CardInfo.class);
        action.execute(new AbstractAction.UICallBack<CardInfo>() {
            public void onSuccess(CardInfo cardInfo) {
                mAmounts.setText(cardInfo.getMoney());
                mMyPoints.setText(cardInfo.getPoints());
                afterAction(dialog);
            }

            public void onFailure(AbstractAction.ActionError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                afterAction(dialog);
            }
        });

        Map parameters = new HashMap<>();
        parameters.put(Constants.KEY_CARD_ID, cardId);
        StringAction getMyIngotsAction = new StringAction(getActivity(), Constants.URL_GET_MY_INGOTS, parameters, "amount");
        getMyIngotsAction.execute(new AbstractAction.UICallBack<String>() {
            public void onSuccess(String response) {
                if (response != null)
                    mMyIngots.setText(response);
                afterAction(dialog);
            }

            public void onFailure(AbstractAction.ActionError error) {
                Toast.makeText(getActivity(), "我的元宝数加载失败，请稍候再试", Toast.LENGTH_LONG).show();
                afterAction(dialog);
            }
        });
    }

    private void afterAction(Dialog dialog){
        mRunningActions --;
        if(mRunningActions == 0){
            dialog.dismiss();
        }
    }

}
