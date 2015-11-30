package com.yanis.yc_ui_fragment_tabhost.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.yanis.yc_ui_fragment_tabhost.R;

public class HomeHeader extends LinearLayout {
    public HomeHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.home_header_view, this, true);
    }
}
