package com.creal.nest.views;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.creal.nest.R;
import com.creal.nest.SearchActivity;

public class HomeHeader extends LinearLayout {
    private EditText btnSearch;
    public HomeHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.home_header_view, this, true);
        btnSearch = (EditText)findViewById(R.id.id_txt_search);
        btnSearch.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                getContext().startActivity(intent);
            }
        });
    }
}
