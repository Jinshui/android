package com.creal.nest;

import android.app.Activity;
import android.os.Bundle;

import com.creal.nest.views.HeaderView;

/**
 * Created by dai on 2015/12/16.
 */
public class AboutUsActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_aboutus);

        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.setTitle(R.string.about);
    }
}
