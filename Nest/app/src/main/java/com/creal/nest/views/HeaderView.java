package com.creal.nest.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creal.nest.R;

public class HeaderView extends LinearLayout {
    private TextView mLeftBtn;
    private TextView mTitle;
    private TextView mRightTxt;
    private ImageView mRightBtn;
    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_header, this, true);
        mLeftBtn = (TextView)findViewById(R.id.left_btn);
        mLeftBtn.setText(" ");
        mTitle = (TextView)findViewById(R.id.title);
        mRightTxt = (TextView)findViewById(R.id.id_text_help);
        mRightBtn = (ImageView)findViewById(R.id.right_btn);
        setLeftBtnListener(new OnClickListener() {
            public void onClick(View v) {
                ((Activity)getContext()).onBackPressed();
            }
        });
    }

    public void hideLeftBtn(){
//        mLeftBtn.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.logo_small), null);
        mLeftBtn.setEnabled(false);
    }

    public void showLeftBtn(){
//        mLeftBtn.setCompoundDrawables(getResources().getDrawable(R.drawable.icon_left_arrow), null, getResources().getDrawable(R.drawable.logo_small), null);
        mLeftBtn.setEnabled(true);
    }

    public void setTitle(int resId){
        mTitle.setText(resId);
    }

    public void setTitle(String title){
        mTitle.setText(title);
    }

    public void setTitleCenter(){
        mTitle.setGravity(Gravity.CENTER);
    }

    public void setTitleLeft(){
        mTitle.setGravity(Gravity.LEFT);
    }

    public void setRightImage(int resId){
        mRightBtn.setImageResource(resId);
        mRightBtn.setVisibility(VISIBLE);
        mRightTxt.setVisibility(GONE);
    }

    public void setRightText(int resId){
        mRightTxt.setText(resId);
        mRightBtn.setVisibility(GONE);
        mRightTxt.setVisibility(VISIBLE);
    }

    public void hideRightImage(){
        mRightBtn.setVisibility(INVISIBLE);
    }

    public void hideRightText(){
        mRightTxt.setVisibility(GONE);
    }

    public void setLeftBtnListener(View.OnClickListener listener){
        mLeftBtn.setOnClickListener(listener);
    }

    public void setRightBtnListener(View.OnClickListener listener){
        mRightBtn.setOnClickListener(listener);
    }

    public void setRightTextListener(View.OnClickListener listener){
        mRightTxt.setOnClickListener(listener);
    }
}
