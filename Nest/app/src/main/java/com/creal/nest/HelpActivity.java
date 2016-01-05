package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.creal.nest.views.HeaderView;

public class HelpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.help_center);
        headerView.setTitleCenter();
    }

    public void onHelpClick(View view){
        Intent intent = new Intent(this, WebpageActivity.class);
        intent.putExtra(WebpageActivity.INTENT_EXTRA_TITLE, ((Button)view).getHint());
        intent.putExtra(WebpageActivity.INTENT_EXTRA_URL, "http://www.baidu.com");
        startActivity(intent);
    }
}
