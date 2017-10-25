package com.example.asus.cardemo.view;

import android.app.Application;
import android.content.Context;

import com.example.asus.cardemo.mode.net.Okhttp;


public class MyApp extends Application {

    private static Context appContext;
    private static Okhttp okhttp;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = this;
        okhttp = new Okhttp();

    }

    public static Context getAppContext(){
        return appContext;
    }

    public static Okhttp getOkhttp() {
        return okhttp;
    }
}
