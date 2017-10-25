package com.example.asus.cardemo.presenter;


import com.example.asus.cardemo.bane.GetCartDataModel;
import com.example.asus.cardemo.bean.CarBean;
import com.example.asus.cardemo.view.IView.IGetCartDataView;



public class GetCartDataPresenter extends BasePresenter<IGetCartDataView> {


    private final GetCartDataModel model;

    public GetCartDataPresenter(IGetCartDataView iView) {
        super(iView);

        model = new GetCartDataModel();
    }

    //    得到购物车数据
    public void getData() {

        model.getData(new GetCartDataModel.DataCallBack() {
            @Override
            public void onGetDataSucceed(CarBean bean) {
                iView.onGetDataSucceed(bean);
            }

            @Override
            public void onGetDataFail(String e) {
                onGetDataFail(e);

            }
        });



    }



}
