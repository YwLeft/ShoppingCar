package com.example.asus.cardemo.view.IView;


import com.example.asus.cardemo.mode.bean.CarBean;



public interface IGetCartDataView extends IView {

    //    获取数据成功
    void onGetDataSucceed(CarBean bean);
    //    获取数据失败
    void onGetDataFail(String e);

}
