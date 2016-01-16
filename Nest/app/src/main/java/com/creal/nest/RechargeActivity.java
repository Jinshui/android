package com.creal.nest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.creal.nest.util.HttpUtil;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.views.HeaderView;
import com.creal.nest.views.UncheckableRadioButton;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RechargeActivity extends Activity {

    private List<UncheckableRadioButton> mAmountBtns;
    private RadioButton mBtnAliPay;
    private RadioButton mBtnWeichat;
    private EditText mAmount;
    private EditText mCardId;
    private Button mBtnRecharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.recharge);
        headerView.setTitleLeft();

        mCardId = (EditText) findViewById(R.id.id_txt_card_id);
        mCardId.setText(PreferenceUtil.getString(this, Constants.APP_USER_CARD_NUM, null));
        mAmount = (EditText) findViewById(R.id.id_txt_amount);

        mAmountBtns = new ArrayList<>();
        mAmountBtns.add((UncheckableRadioButton) findViewById(R.id.id_btn_amount_100));
        mAmountBtns.add((UncheckableRadioButton) findViewById(R.id.id_btn_amount_200));
        mAmountBtns.add((UncheckableRadioButton)findViewById(R.id.id_btn_amount_300));
        mAmountBtns.add((UncheckableRadioButton)findViewById(R.id.id_btn_amount_500));
        mAmountBtns.add((UncheckableRadioButton)findViewById(R.id.id_btn_amount_1000));
        mAmountBtns.add((UncheckableRadioButton)findViewById(R.id.id_btn_amount_2000));

        mBtnAliPay = (RadioButton) findViewById(R.id.id_btn_recharge_mode_alipay);
        mBtnWeichat = (RadioButton)findViewById(R.id.id_btn_recharge_mode_weichat);

        mBtnRecharge = (Button) findViewById(R.id.id_btn_recharge);
        init();
    }

    private void init(){
        for(UncheckableRadioButton button : mAmountBtns) {
            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(!isChecked)
                        return;
                    mAmount.setText(buttonView.getText().toString().replace("元", ""));
                    for(UncheckableRadioButton button : mAmountBtns) {
                        if(button.getId() != buttonView.getId()){
                            button.setChecked(false);
                        }
                    }
                }
            });
        }
        mBtnAliPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mBtnWeichat.setChecked(false);
                }
            }
        });
        mBtnWeichat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mBtnAliPay.setChecked(false);
                }
            }
        });
        mBtnRecharge.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final CharSequence cardId = mCardId.getText();
                if(TextUtils.isEmpty(cardId)){
                    Animation shake = AnimationUtils.loadAnimation(RechargeActivity.this, R.anim.shake);
                    mCardId.startAnimation(shake);
                    return;
                }
                final CharSequence amount = mAmount.getText();
                if(TextUtils.isEmpty(amount)){
                    Animation shake = AnimationUtils.loadAnimation(RechargeActivity.this, R.anim.shake);
                    mAmount.startAnimation(shake);
                    return;
                }
                if(Integer.valueOf(mAmount.getText().toString().trim()) % 100 != 0 ){
                    //Show Dialog.
                    return;
                }

                if(mBtnWeichat.isChecked()){
                    payWithWX();
                }

            }
        });
    }

    private void payWithWX(){
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        final IWXAPI api = WXAPIFactory.createWXAPI(this, Constants.APP_WX_APPID);
        // 将该app注册到微信
        api.registerApp(Constants.APP_WX_APPID);
        final String url = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
        mBtnRecharge.setEnabled(false);
        Toast.makeText(this, "获取订单中...", Toast.LENGTH_SHORT).show();
        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void... params) {
                try{
                    byte[] buf = HttpUtil.httpGet(url);
                    if (buf != null && buf.length > 0) {
                        String content = new String(buf);
                        Log.e("get server pay params:", content);
                        JSONObject json = new JSONObject(content);
                        if(null != json && !json.has("retcode") ){
                            PayReq req = new PayReq();
                            //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                            req.appId			= json.getString("appid");
                            req.partnerId		= json.getString("partnerid");
                            req.prepayId		= json.getString("prepayid");
                            req.nonceStr		= json.getString("noncestr");
                            req.timeStamp		= json.getString("timestamp");
                            req.packageValue	= json.getString("package");
                            req.sign			= json.getString("sign");
                            req.extData			= "app data"; // optional
                            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                            api.sendReq(req);
                        }else{
                            return "返回错误"+json.getString("retmsg");
                        }
                    }else{
                        return "服务器请求错误";
                    }
                }catch(Exception e){
                    return "异常："+e.getMessage();
                }
                return "正常调起支付";
            }
            public void onPostExecute(String result){
                Toast.makeText(RechargeActivity.this, result, Toast.LENGTH_SHORT).show();
                mBtnRecharge.setEnabled(true);
            }
        }.execute(new Void[]{});
    }
}
