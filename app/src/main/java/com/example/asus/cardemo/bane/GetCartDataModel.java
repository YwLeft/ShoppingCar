package com.example.asus.cardemo.bane;


import com.example.asus.cardemo.view.MyApp;
import com.example.asus.cardemo.bean.CarBean;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class GetCartDataModel {
    String path = "http://120.27.23.105/product/getCarts?uid=149";
    private final OkHttpClient okHttpClient;

    public GetCartDataModel() {

        okHttpClient = MyApp.getOkHttpClient();
    }


    public void getData(final DataCallBack callBack){

        Request request = new Request.Builder()
                .url(path)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                callBack.onGetDataFail(e.toString());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json = response.body().string();
                Gson gson = new Gson();

                if(response.isSuccessful()){
                    CarBean bean = gson.fromJson(json,CarBean.class);

                    callBack.onGetDataSucceed(bean);
                }

            }
        });

    }



    public interface DataCallBack{
        void onGetDataSucceed(CarBean bean);
        void onGetDataFail(String e);
    }
}
