package com.creal.nest.property;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.Constants;
import com.creal.nest.R;
import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.CommonObjectAction;
import com.creal.nest.model.Repair;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.UIUtil;
import com.creal.nest.views.HeaderView;

import java.util.HashMap;
import java.util.Map;

public class RepairDetailActivity extends Activity {

    private static final String TAG = "XYK-LatestActivities";
    public static final String INTENT_EXTRA_REPAIR_ID = "repair_id";

    private TextView mTitle;
    private TextView mState;
    private TextView mTime;
    private TextView mDesc;

    private LinearLayout mStepsPanel;
    private String mRepairId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_repair_detail);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.property_repair_detail);
        mStepsPanel = (LinearLayout)findViewById(R.id.id_panel_repair_steps);
        mTitle = (TextView)findViewById(R.id.id_txt_repair_name);
        mState = (TextView)findViewById(R.id.id_txt_repair_state);
        mTime = (TextView)findViewById(R.id.id_txt_repair_time);
        mDesc = (TextView)findViewById(R.id.id_txt_repair_desc);

        mRepairId = getIntent().getStringExtra(INTENT_EXTRA_REPAIR_ID);

        init();
    }

    private void init(){
        final Dialog dialog = UIUtil.showLoadingDialog(this, getString(R.string.loading), true);
        String cardId = PreferenceUtil.getString(this, Constants.APP_USER_CARD_ID, null);
        Map parameters = new HashMap();
        parameters.put(Constants.KEY_CARD_ID, cardId);
        parameters.put("repair_id", mRepairId);
        CommonObjectAction repairDetailAction = new CommonObjectAction(this, Constants.URL_REPORT_REPAIR_DETAIL, parameters, Repair.class);
        repairDetailAction.execute(new AbstractAction.UICallBack<Repair>() {
            public void onSuccess(Repair repair) {
                initSteps(repair);
                dialog.dismiss();
            }

            public void onFailure(AbstractAction.ActionError error) {
                Toast.makeText(RepairDetailActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
    }

    private void initSteps(Repair repair){

        mTitle.setText(repair.getTitle());
        mState.setText(Repair.State.toString(repair.getState()));
        mTime.setText(String.format(getString(R.string.property_repair_report_time_detail), repair.getTime() == null ? "" : repair.getTime()));
        mDesc.setText(repair.getSummary());

//        repair.getStepList().add(new Repair.Step("维修中...", "2015.6.11 16:20"));
//        repair.getStepList().add(new Repair.Step("维修师傅返回准备所需物料", "2015.6.11 16:10"));
//        repair.getStepList().add(new Repair.Step("已经安排物业维修师傅上门检查损坏情况", "2015.6.11 15:40"));
//        repair.getStepList().add(new Repair.Step("您的报修信息已经开始.", "2015.6.11 15:36"));
//        List<Repair.Step> stepList = repair.getStepList();
//        for(int i=0;  i<stepList.size(); i++){
//            Repair.Step step = stepList.get(i);
//            View item  = LayoutInflater.from(this).inflate(R.layout.view_list_item_repair_flow, null);
//            TextView desc = (TextView)item.findViewById(R.id.id_txt_repair_step_desc);
//            TextView date = (TextView)item.findViewById(R.id.id_txt_repair_step_date);
//            desc.setText(step.desc);
//            date.setText(step.date);
//            if(i == 0){
//                Drawable drawable = getResources().getDrawable(R.drawable.triangle_pink);
//                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                desc.setCompoundDrawables(drawable, null, null, null);
//                desc.setTextColor(Color.parseColor("#FF62B5"));
//                date.setTextColor(Color.parseColor("#FF62B5"));
//            }else{
//                Drawable drawable = getResources().getDrawable(R.drawable.triangle_grey);
//                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                desc.setCompoundDrawables(drawable, null, null, null);
//                desc.setTextColor(Color.parseColor("#808080"));
//                date.setTextColor(Color.parseColor("#808080"));
//            }
//            mStepsPanel.addView(item);
//        }
    }
}
