package com.creal.nest.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NearbyHeader extends LinearLayout{

	private ImageButton mLeftBtn;
	private ImageButton mRightBtn;
	private TextView mTitle;
	public NearbyHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setLeftImage(int resid, OnClickListener onClickListener){
	}
	
	public void setRightImage(int resid, OnClickListener onClickListener){
	}
	
	public void setTitle(int resid){
	}
}
