package com.example.asus.cardemo.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.cardemo.R;
import com.example.asus.cardemo.mode.bean.CarBean;
import com.example.asus.cardemo.presenter.GetCartDataPresenter;
import com.example.asus.cardemo.view.IView.IGetCartDataView;
import com.example.asus.cardemo.view.adapter.Car;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IGetCartDataView {

    private static String AMOUNT = "";
    private static String CART_ID = "";
    private static String GOODS_ID = "";

    private RecyclerView recyclerView;

    private Button okBtn; //结算按钮
    private TextView sum_tv; //总金额显示文本框

    private CheckBox selectAllBox; //用于操作全选的 单选框

    private int mCount = 0; //商品选择数量


    private GetCartDataPresenter getDataPresenter;

    private List<CarBean.DataBean> mlist = new ArrayList<>();
    private List<CarBean.DataBean.ListBean> list = new ArrayList<>();


    private Car adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rec);
        okBtn = (Button) findViewById(R.id.ok_btn);

        sum_tv = (TextView)findViewById(R.id.sum);

        selectAllBox = (CheckBox)findViewById(R.id.select_all_box);
        selectAllBox.setChecked(false);

        getDataPresenter = new GetCartDataPresenter(this);
        getDataPresenter.getData();


    }


    @Override
    public Context context() {
        return this;
    }

    @Override
    public void onGetDataSucceed(CarBean bean) {
        mlist.addAll(bean.getData());
        for (CarBean.DataBean beans : mlist) {
            list.addAll(beans.getList());
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sum_tv.setText("合计：¥" + 0 + ".00");  //设置总价

                adapter = new Car(list,MainActivity.this);

                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(adapter);


                /**
                 * 监听 商品数量变化
                 * 先删除 后添加
                 */
                adapter.setOnGartChangeListener(new Car.OnGartChangeListener() {
                    @Override
                    public void onChange(int total, String cart_id, String goods_id) {
//
                        sum_tv.setText("合计：¥" + total + ".00");
                    }


                });

                /**
                 * 监听 回调的总价 及是否全选
                 */
                adapter.setOnItemBoxChangeListener(new Car.OnItemBoxChangeListener() {
                    @Override
                    public void onChange(boolean isSelectAll, int total, final int count) {
                        selectAllBox.setChecked(isSelectAll);
                        sum_tv.setText("合计：¥" + total + ".00");
                        okBtn.setText("去结算(" + count + ")");

                        mCount = count;

                    }

                });

                //调用全选
                selectAllBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.selectAllItem(selectAllBox.isChecked());
                    }
                });

                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mCount != 0) {
                            Intent intent = new Intent(MainActivity.this, BuyGoodsActivity.class);

                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "请选择商品", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });
    }

    @Override
    public void onGetDataFail(String e) {

    }


}
