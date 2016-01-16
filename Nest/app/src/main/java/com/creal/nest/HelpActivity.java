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
import com.creal.nest.actions.JSONRespObject;
import com.creal.nest.model.Help;
import com.creal.nest.model.Pagination;
import com.creal.nest.views.HeaderView;

import java.util.zip.Inflater;

public class HelpActivity extends Activity {
    private static final String TAG = "XYK-HelpActivity";

    private LinearLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.help_center);
        headerView.setTitleCenter();

        mContainer = (LinearLayout)findViewById(R.id.id_help_container);

        init();
    }

    private void init(){
        CommonPaginationAction commonObjectAction = new CommonPaginationAction(this, 1, Constants.PAGE_SIZE,
                Constants.URL_GET_HELP_LIST, null, Help.class);
        commonObjectAction.execute(new AbstractAction.UICallBack<Pagination<Help>>() {
            public void onSuccess(Pagination<Help> helps) {
                for(Help help : helps.getItems()){
                    LayoutInflater inflater = LayoutInflater.from(HelpActivity.this);
                    LinearLayout groupView = (LinearLayout)inflater.inflate(R.layout.view_list_item_help_group, null);
                    TextView titleView = (TextView)groupView.findViewById(R.id.id_txt_title);
                    titleView.setText(help.getTitle());
                    mContainer.addView(groupView);

                    for(final Help.Item item : help.getItemList()){
                        LinearLayout itemView = (LinearLayout)inflater.inflate(R.layout.view_list_item_help_item, null);
                        Button itemTitleView = (Button)itemView.findViewById(R.id.id_txt_title);
                        itemTitleView.setText(item.getTitle());
                        itemTitleView.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                Intent intent = new Intent(HelpActivity.this, HelpDetailActivity.class);
                                intent.putExtra(HelpDetailActivity.INTENT_EXTRA_TITLE, item.getTitle());
                                intent.putExtra(HelpDetailActivity.INTENT_EXTRA_CONTENT, item.getContent());
                                startActivity(intent);
                            }
                        });
                        groupView.addView(itemView);
                    }
                }
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
