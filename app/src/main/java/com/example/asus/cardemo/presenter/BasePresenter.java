package com.example.asus.cardemo.presenter;

import android.content.Context;

import com.example.asus.cardemo.view.MyApp;
import com.example.asus.cardemo.view.IView.IView;



public class BasePresenter<T extends IView> {

    protected T iView;


    public BasePresenter(T iView) {
        this.iView = iView;
    }

    Context context(){
        if(iView != null && iView.context() != null){
            return iView.context();
        }
        return MyApp.getAppContext();
    }

}
