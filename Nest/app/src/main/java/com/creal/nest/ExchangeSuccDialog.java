package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExchangeSuccDialog extends Activity {

    private static final String TAG = "XYK-ExchangeSuccDialog";

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
        from = getIntent().getStringExtra("from");
        String btnText = getIntent().getStringExtra("btnText");
        String viewBtnText = getIntent().getStringExtra("viewBtnText");
        String message = getIntent().getStringExtra("message");
        String title = getIntent().getStringExtra("title");
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
        if("ingots_mall".equals(from)){
            Intent intent = new Intent(this, IngotsExchangeHistoryActivity.class);
            startActivity(intent);
        }else if("points_mall".equals(from)){
            Intent intent = new Intent(this, ExchangePointHistoryActivity.class);
            startActivity(intent);
        }else if("property_pay".equals(from)){
//            Intent intent = new Intent(this, History.class);
//            startActivity(intent);
        }
        finish();
    }

    public void onBackClick(View view){
        finish();
    }



}
