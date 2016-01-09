package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.CommonPaginationAction;
import com.creal.nest.actions.JSONRespObject;
import com.creal.nest.model.Pagination;
import com.creal.nest.views.HeaderView;

public class HelpActivity extends Activity {
    private static final String TAG = "XYK-HelpActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.help_center);
        headerView.setTitleCenter();
        init();
    }

    private void init(){
        CommonPaginationAction commonObjectAction = new CommonPaginationAction(this, 1, Constants.PAGE_SIZE,
                Constants.URL_GET_HELP_LIST, null, JSONRespObject.class);
        commonObjectAction.execute(new AbstractAction.UICallBack<Pagination<JSONRespObject>>() {
            public void onSuccess(Pagination<JSONRespObject> result) {
                Log.d(TAG, "successfully got result");
            }

            public void onFailure(AbstractAction.ActionError error) {
                Log.e(TAG, "Failed to got result: " + error.getMessage());
            }
        });
    }



    public void onHelpClick(View view){
        Intent intent = new Intent(this, WebpageActivity.class);
        intent.putExtra(WebpageActivity.INTENT_EXTRA_TITLE, ((Button)view).getHint());
        intent.putExtra(WebpageActivity.INTENT_EXTRA_URL, "http://www.baidu.com");
        startActivity(intent);
    }
}
