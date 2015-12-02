package com.yanis.yc_ui_fragment_tabhost.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanis.yc_ui_fragment_tabhost.R;

public class HeaderView extends LinearLayout {
    private TextView mLeftBtn;
    private TextView mTitle;
    private ImageView mRightBtn;
    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_header, this, true);
        mLeftBtn = (TextView)findViewById(R.id.left_btn);
        mTitle = (TextView)findViewById(R.id.title);
        mRightBtn = (ImageView)findViewById(R.id.right_btn);
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
    }
}
