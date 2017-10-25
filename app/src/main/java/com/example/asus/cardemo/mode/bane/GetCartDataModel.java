package com.example.asus.cardemo.mode.bane;


import com.example.asus.cardemo.mode.bean.CarBean;
import com.example.asus.cardemo.mode.net.NetDataCallBack;
import com.example.asus.cardemo.mode.net.Okhttp;
import com.example.asus.cardemo.view.MyApp;



public class GetCartDataModel {
    String path = "http://120.27.23.105/product/getCarts?uid=149";
    private  Okhttp okhttp;

    public GetCartDataModel() {
        okhttp = MyApp.getOkhttp();

    }


    public void getData(final DataCallBack callBack){

        okhttp.getdata(path, new NetDataCallBack() {
            @Override
            public void success(Object o) {
                CarBean carBean = (CarBean) o;
                callBack.onGetDataSucceed(carBean);
            }

            @Override
            public void faild(int positon, String str) {
                callBack.onGetDataFail(str);
            }
        },CarBean.class);
    }



    public interface DataCallBack{
        void onGetDataSucceed(CarBean bean);
        void onGetDataFail(String e);
    }
}
