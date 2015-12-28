package com.creal.nest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.creal.nest.views.HeaderView;

public class WebpageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Let's display the progress in the activity title bar, like the
        // browser app does.
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_detail);

        final HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        final String title = getIntent().getStringExtra("title");
        headerView.setTitle(title);
        WebView webview = (WebView) findViewById(R.id.id_help_webview);
        webview.clearCache(true);
        webview.clearHistory();

        webview.getSettings().setJavaScriptEnabled(true);
        final Activity activity = this;
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                activity.setProgress(progress * 1000);
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
            public void onPageFinished(WebView view, String url) {
                if(title == null || title.isEmpty()) {
                    headerView.setTitle(view.getTitle());
                }
            }
        });

        String url = getIntent().getStringExtra("url");
        if(url == null){
            url = "http://www.baidu.com";
        }
        webview.loadUrl(url);
    }

    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
