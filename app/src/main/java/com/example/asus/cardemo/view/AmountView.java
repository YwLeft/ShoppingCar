package com.example.asus.cardemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.asus.cardemo.R;




public class AmountView extends LinearLayout implements View.OnClickListener {

    private Button btnIncrease;//点击增加的按钮
    private Button btnDecrease;//点击减少的按钮
    private EditText edAmount; //中间数量显示框


    private int amount = 1; //购买数量

    public AmountView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {

        //加载自定义的布局
        View view = inflate(context, R.layout.widget_amount, this);


        btnIncrease = (Button) view.findViewById(R.id.btn_increase);

        btnDecrease = (Button) view.findViewById(R.id.btn_decrease);

        edAmount = (EditText) view.findViewById(R.id.ed_amount);

        //设置默认显示的数值
        edAmount.setText(amount+"");

        btnIncrease.setOnClickListener(this);
        btnDecrease.setOnClickListener(this);

//        默认回调
        if(onAmountChangeListener != null){
            onAmountChangeListener.onAmountChange(amount);

        }


//        editText 文字改变监听
        edAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().isEmpty()){
//                    edAmount.setText(amount+"");

                    if(onAmountChangeListener != null){
                        //如果输入框无文字 回调 0;
                        onAmountChangeListener.onAmountChange(0);

                    }
                    return;
                }
                amount = Integer.parseInt(s.toString());

                if(onAmountChangeListener != null){
                    onAmountChangeListener.onAmountChange(amount);

                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            //点击增加
            case R.id.btn_increase:
                //最小值为99
                if(amount<99){
                    amount++;
                    edAmount.setText(amount+"");
                }

                break;
            //点击减少
            case R.id.btn_decrease:

                //最小值为1
                if(amount>1){
                    amount--;
                    edAmount.setText(amount+"");
                }

                break;
        }

    }

    /**
     * 自定义 监听回调商品数量的接口
     */

    public interface OnAmountChangeListener{
        void onAmountChange(int amount);
    }
    private OnAmountChangeListener onAmountChangeListener;

    public void setOnAmountChangeListener(OnAmountChangeListener onAmountChangeListener){
        this.onAmountChangeListener = onAmountChangeListener;
    }


    /**
     *
     * @param amount
     *
     * 自定义方法 可以通过其他界面的调用 ,设置 显示文本
     */
    public void setAmount(int amount){

        this.amount = amount;
        edAmount.setText(amount+"");
    }
}
