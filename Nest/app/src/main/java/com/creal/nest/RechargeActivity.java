package com.creal.nest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.creal.nest.views.HeaderView;
import com.creal.nest.views.UncheckableRadioButton;

import java.util.ArrayList;
import java.util.List;

public class RechargeActivity extends Activity {

    private List<UncheckableRadioButton> mAmountBtns;
    private RadioButton mBtnAliPay;
    private RadioButton mBtnWeichat;
    private EditText mAmount;
    private EditText mCardId;
    private Button mBtnRecharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.recharge);
        headerView.setTitleLeft();

        mCardId = (EditText) findViewById(R.id.id_txt_card_id);
        mAmount = (EditText) findViewById(R.id.id_txt_amount);

        mAmountBtns = new ArrayList<>();
        mAmountBtns.add((UncheckableRadioButton) findViewById(R.id.id_btn_amount_100));
        mAmountBtns.add((UncheckableRadioButton) findViewById(R.id.id_btn_amount_200));
        mAmountBtns.add((UncheckableRadioButton)findViewById(R.id.id_btn_amount_300));
        mAmountBtns.add((UncheckableRadioButton)findViewById(R.id.id_btn_amount_500));
        mAmountBtns.add((UncheckableRadioButton)findViewById(R.id.id_btn_amount_1000));
        mAmountBtns.add((UncheckableRadioButton)findViewById(R.id.id_btn_amount_2000));

        mBtnAliPay = (RadioButton) findViewById(R.id.id_btn_recharge_mode_alipay);
        mBtnWeichat = (RadioButton)findViewById(R.id.id_btn_recharge_mode_weichat);

        mBtnRecharge = (Button) findViewById(R.id.id_btn_recharge);
        init();
    }

    private void init(){
        for(UncheckableRadioButton button : mAmountBtns) {
            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(!isChecked)
                        return;
                    mAmount.setText(buttonView.getText().toString().replace("元", ""));
                    for(UncheckableRadioButton button : mAmountBtns) {
                        if(button.getId() != buttonView.getId()){
                            button.setChecked(false);
                        }
                    }
                }
            });
        }
        mBtnAliPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mBtnWeichat.setChecked(false);
                }
            }
        });
        mBtnWeichat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mBtnAliPay.setChecked(false);
                }
            }
        });
        mBtnRecharge.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCardId.getText() == null || mCardId.getText().toString().trim().isEmpty()){
                    //Show Dialog
                    return;
                }
                if(mAmount.getText() == null || mAmount.getText().toString().trim().isEmpty()){
                    //Show Dialog
                    return;
                }
                if(Integer.valueOf(mAmount.getText().toString().trim()) % 100 != 0 ){
                    //Show Dialog.
                    return;
                }
            }
        });
    }
}
