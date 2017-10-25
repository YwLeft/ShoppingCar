package com.example.asus.cardemo.view;

import android.app.Application;
import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public class MyApp extends Application {

    private static Context appContext;
    private static OkHttpClient okHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = this;

        okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();


    }

    public static Context getAppContext(){
        return appContext;
    }

    public static OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }

}
