package com.yanis.yc_ui_fragment_tabhost;

import android.app.Application;
import android.util.Log;

public class XYKApp extends Application {
    private static final String tag = "TT-XYKApp";
    
    private String mCachePath;
    
    public void onCreate() {
        Log.i(tag, "影视头条App is initializing");
        super.onCreate();
        mCachePath = getCacheDir().getAbsolutePath() + Constants.CACHE_DIR;
    }
    
    public String getCachePath(){
    	return mCachePath;
    }
}
