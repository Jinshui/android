package com.creal.nest;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.StringAction;
import com.creal.nest.util.BitmapUtil;
import com.creal.nest.util.ImageUtil;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.UIUtil;
import com.creal.nest.views.HeaderView;
import com.google.zxing.WriterException;

import java.util.HashMap;
import java.util.Map;

public class QRCodeActivity extends Activity {

    private static final String TAG = "XYK-QRCodeActivity";
    private ImageView mImageQRCodeView;
    private ImageView mImageBarcodeView;
    private Dialog mProgressDialog;
    private Handler mHandler;
    private boolean mFirstLoad = true;
    private Runnable mLoadCodeTask = new Runnable() {
        public void run() {
            loadQRCode();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.pay);
        headerView.setRightText(R.string.pay_description);
        headerView.setRightTextListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        mImageQRCodeView = (ImageView)findViewById(R.id.id_img_qr_code);
        mImageBarcodeView = (ImageView)findViewById(R.id.id_img_bar_code);
        mHandler = new Handler();
        mHandler.post(mLoadCodeTask);
        mProgressDialog = UIUtil.createLoadingDialog(this, getString(R.string.dlg_msg_loading_qr_code), false);
    }

    public void onPause() {
        super.onPause();
        mFirstLoad = true;
        mHandler.removeCallbacks(mLoadCodeTask);
    }

    private void loadQRCode() {
        if(mFirstLoad) {
            mProgressDialog.show();
            mFirstLoad = false;
        }
        String cardId = PreferenceUtil.getString(this, Constants.APP_USER_CARD_ID, null);
        Map parameters = new HashMap<>();
        parameters.put(Constants.KEY_CARD_ID, cardId);
        StringAction qrCodeAction = new StringAction(this, Constants.URL_GET_QR_CODE, parameters, Constants.KEY_QR_CODE);
        qrCodeAction.execute(
                new AbstractAction.UICallBack<String>() {
                    public void onSuccess(String result) {
                        updateImageView(result);
                        mProgressDialog.dismiss();
                        mHandler.postDelayed(mLoadCodeTask, 60000);
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        mProgressDialog.dismiss();
                        mHandler.post(mLoadCodeTask);
                        Toast.makeText(QRCodeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateImageView(String code){
        new AsyncTask<String, Void, Drawable[]>(){
            protected Drawable[] doInBackground(String... code) {
                try {
                    float density = getResources().getDisplayMetrics().density;
                    Log.d(TAG, "density: " + density);
                    Drawable[] result = new Drawable[2];
//                    Bitmap logo = ImageUtil.drawable2Bitmap(getResources().getDrawable(R.drawable.logo_small));
//                    result[0] = ImageUtil.bitmap2Drawable(BitmapUtil.createBarcode(QRCodeActivity.this, code[0], 2000, 350, true, 200));
//                    result[1] = ImageUtil.bitmap2Drawable(BitmapUtil.createQRCode(code[0], 1600, null));
                    result[0] = ImageUtil.bitmap2Drawable(BitmapUtil.createBarcode(QRCodeActivity.this, code[0], (int)(666.6 * density + 0.5f), (int)(110 * density + 0.5f), true, 200));
                    result[1] = ImageUtil.bitmap2Drawable(BitmapUtil.createQRCode(code[0], (int)(540 * density + 0.5f), null));
                    return result;
                } catch (WriterException e) {
                    Log.e(TAG, "Failed to create qr code for code " + code[0], e);
                    return null;
                }
            }
            protected void onPostExecute(Drawable[] qrCodeImg) {
                if(Build.VERSION.SDK_INT >= 16) {
                    mImageBarcodeView.setBackground(qrCodeImg[0]);
                    mImageQRCodeView.setBackground(qrCodeImg[1]);
                }else {
                    //noinspection deprecation
                    mImageBarcodeView.setBackgroundDrawable(qrCodeImg[0]);
                    mImageQRCodeView.setBackgroundDrawable(qrCodeImg[1]);
                }
            }
        }.execute(code);
    }

    public void onCancelClick(View view) {
        finish();
    }
}
