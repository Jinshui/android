package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.CommonPaginationAction;
import com.creal.nest.model.Help;
import com.creal.nest.model.Pagination;
import com.creal.nest.views.HeaderView;

public class HelpDetailActivity extends Activity {
    private static final String TAG = "XYK-HelpActivity";
    public static final String INTENT_EXTRA_TITLE = "title";
    public static final String INTENT_EXTRA_CONTENT = "content";

    private TextView mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_detail);

        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitleCenter();
        headerView.setTitle(getIntent().getStringExtra(INTENT_EXTRA_TITLE));

        mContent = (TextView)findViewById(R.id.id_txt_help);
        mContent.setText(getIntent().getStringExtra(INTENT_EXTRA_CONTENT));
    }
}
