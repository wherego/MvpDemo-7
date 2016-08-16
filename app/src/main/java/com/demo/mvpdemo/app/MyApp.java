package com.demo.mvpdemo.app;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.Logger;

public class MyApp extends Application {

    public static MyApp app;
    public final static String LOG = "mvp";

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Logger.init(LOG);
    }

    public static Context getContext(){
        return app.getApplicationContext();
    }
}
