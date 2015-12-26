package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExchangeSuccDialog extends Activity {

    private static final String TAG = "XYK-ExchangeSuccDialog";

    public static final String INTENT_EXTRA_TITLE = "title";
    public static final String INTENT_EXTRA_FROM = "from";
    public static final String INTENT_EXTRA_FROM_INGOT_MALL = "from_ingots_mall";
    public static final String INTENT_EXTRA_FROM_POINTS_MALL = "from_points_mall";
    public static final String INTENT_EXTRA_FROM_PROPERTY_PAY = "from_property_pay";
    public static final String INTENT_EXTRA_MSG = "message";
    public static final String INTENT_EXTRA_BTN1_TXT = "btnText";
    public static final String INTENT_EXTRA_BTN2_TXT = "viewBtnText";


    private TextView mTitleView;
    private TextView mMsgView;
    private Button mBtnViewHistory;
    private Button mButton;
    private String from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_echange_succ);
        mTitleView = (TextView)findViewById(R.id.id_text_title);
        mMsgView = (TextView)findViewById(R.id.id_text_message);
        mButton = (Button)findViewById(R.id.id_btn_back_to_mall);
        mBtnViewHistory = (Button)findViewById(R.id.id_btn_view_history);
        from = getIntent().getStringExtra(INTENT_EXTRA_FROM);
        String btnText = getIntent().getStringExtra(INTENT_EXTRA_BTN1_TXT);
        String viewBtnText = getIntent().getStringExtra(INTENT_EXTRA_BTN2_TXT);
        String message = getIntent().getStringExtra(INTENT_EXTRA_MSG);
        String title = getIntent().getStringExtra(INTENT_EXTRA_TITLE);
        if(btnText != null)
            mButton.setText(btnText);
        if(viewBtnText != null)
            mBtnViewHistory.setText(viewBtnText);
        if(message != null)
            mMsgView.setText(message);
        if(title != null)
            mTitleView.setText(title);
    }

    public void onViewHistoryClick(View view){
        if(INTENT_EXTRA_FROM_INGOT_MALL.equals(from)){
            Intent intent = new Intent(this, IngotsExchangeHistoryActivity.class);
            startActivity(intent);
        }else if(INTENT_EXTRA_FROM_POINTS_MALL.equals(from)){
            Intent intent = new Intent(this, ExchangePointHistoryActivity.class);
            startActivity(intent);
        }else if(INTENT_EXTRA_FROM_PROPERTY_PAY.equals(from)){
//            Intent intent = new Intent(this, History.class);
//            startActivity(intent);
        }
        finish();
    }

    public void onBackClick(View view){
        finish();
    }



}
