package com.creal.nest;

import android.app.Application;
import android.util.Log;

public class XYKApp extends Application {
    private static final String tag = "TT-XYKApp";
    
    private String mCachePath;
    
    public void onCreate() {
        Log.i(tag, "App is initializing");
        super.onCreate();
        mCachePath = getCacheDir().getAbsolutePath() + Constants.CACHE_DIR;
    }
    
    public String getCachePath(){
    	return mCachePath;
    }
}
