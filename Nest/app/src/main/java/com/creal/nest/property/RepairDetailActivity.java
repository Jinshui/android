package com.creal.nest.property;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.LatestActivityDetailActivity;
import com.creal.nest.R;
import com.creal.nest.model.Repair;
import com.creal.nest.model.SalesActivity;
import com.creal.nest.views.HeaderView;

import java.util.List;

public class RepairDetailActivity extends Activity {


    private static final String TAG = "XYK-LatestActivities";
    private LinearLayout mStepsPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_repair_detail);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.property_repair_detail);
        mStepsPanel = (LinearLayout)findViewById(R.id.id_panel_repair_steps);

        Repair repair = getIntent().getParcelableExtra("repair");
        List<Repair.Step> stepList = repair.getStepList();
        for(int i=0;  i<stepList.size(); i++){
            Repair.Step step = stepList.get(i);
            View item  = LayoutInflater.from(this).inflate(R.layout.view_list_item_repair_flow, null);
            TextView desc = (TextView)item.findViewById(R.id.id_txt_repair_step_desc);
            TextView date = (TextView)item.findViewById(R.id.id_txt_repair_step_date);
            desc.setText(step.desc);
            date.setText(step.date);
            if(i == 0){
                Drawable drawable = getResources().getDrawable(R.drawable.triangle_pink);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                desc.setCompoundDrawables(drawable, null, null, null);
                desc.setTextColor(Color.parseColor("#FF62B5"));
                date.setTextColor(Color.parseColor("#FF62B5"));
            }else{
                Drawable drawable = getResources().getDrawable(R.drawable.triangle_grey);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                desc.setCompoundDrawables(drawable, null, null, null);
                desc.setTextColor(Color.parseColor("#808080"));
                date.setTextColor(Color.parseColor("#808080"));
            }
            mStepsPanel.addView(item);
        }
    }

    protected void onListItemClick(ListView l, View v, int position, long id){
        Toast.makeText(getBaseContext(), "clicked: " + position + ", enabled: " + v.isEnabled(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LatestActivityDetailActivity.class);
        SalesActivity salesActivity = new SalesActivity();
//        salesActivity.setName("潮牌运动风");
        intent.putExtra("salesActivity", salesActivity);
        startActivity(intent);
    }

}
