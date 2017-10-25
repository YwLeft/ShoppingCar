package com.example.asus.cardemo.view.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.cardemo.R;
import com.example.asus.cardemo.mode.bean.CarBean;
import com.example.asus.cardemo.view.AmountView;

import java.util.List;



public class Car extends RecyclerView.Adapter<Car.MyViewHolder> {

    private List<CarBean.DataBean.ListBean> list;

    private Context context;
    private LayoutInflater inflater;

    public Car(List<CarBean.DataBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);

        if (list.size() != 0) {
            for (CarBean.DataBean.ListBean goodsBean : list) {
                goodsBean.setCheck(false);
            }
        }

    }

    public void selectAllItem(boolean isSelectAll) {
        if (isSelectAll) {
            for (CarBean.DataBean.ListBean goodsBean : list) {
                goodsBean.setCheck(true);
            }
        } else {
            for (CarBean.DataBean.ListBean goodsBean : list) {
                goodsBean.setCheck(false);
            }
        }
        notifyDataSetChanged();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHolder holder = new MyViewHolder(inflater.inflate(R.layout.item_shopping_cart_list, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.itemView.setTag(position);

//
        final String cart_id = list.get(position).getPid()+""; //购物车商品标识
        final String goods_id = list.get(position).getPscid()+""; //购物车商品id

        holder.tv_name.setText(list.get(position).getTitle().substring(0,20));
        holder.tv_price.setText(list.get(position).getPrice()+"");


        holder.amountView.setAmount(Integer.parseInt(list.get(position).getNum()+""));
        String[] split = list.get(position).getImages().split("\\|");
        Glide.with(context).load(split[0]).into(holder.goods_img);
        holder.box.setChecked(list.get(position).isCheck());

        holder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int pos = holder.getLayoutPosition();
                list.get(pos).setCheck(isChecked);

                //选中条目数
                int selectCount = 0;

                //总价
                int total = 0;

                //是否全选中
                boolean isSelectAll = false;

                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isCheck()) {
                        selectCount++;
                        //得到此条目总价
                        String price = String.valueOf(list.get(i).getPrice());
                        String[] s = price.split("[.]");
                        total = total + Integer.parseInt(s[0]);
                    }
                }

                if (selectCount == list.size()) {
                    isSelectAll = true;
                } else {
                    isSelectAll = false;
                }

                onItemBoxChangeListener.onChange(isSelectAll, total, selectCount);

            }
        });

        /**
         * 监听计数器变化
         */
        holder.amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(int amount) {

                if (amount == 0) {
                    holder.amountView.setAmount(1);
                }
                if (onGartChangeListener != null) {

                    int goods_num = Integer.parseInt(list.get(position).getNum()+"");
                    //得到此条目总价
                    String prices = String.valueOf(list.get(position).getPrice());
                    String[] sss = prices.split("[.]");
                    int old_goods_total = Integer.parseInt((sss[0]));
                    int new_goods_total = old_goods_total / goods_num * amount;

                    list.get(position).setNum(amount);
                    list.get(position).setPrice(Double.parseDouble(new_goods_total + ".00"));


                    //总价
                    int total = 0;

                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).isCheck()) {
                            String ss = String.valueOf(list.get(i).getPrice());
                            //得到此条目总价
                            String price = String.valueOf(list.get(i).getPrice());
                            String[] s = price.split("[.]");
                            total = total + Integer.parseInt(s[0]);
                        }
                    }

                    onGartChangeListener.onChange(total, cart_id, goods_id);

                }

            }
        });

        holder.del_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("确定要删除吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(position);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();

            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        View itemView;

        TextView tv_name, tv_price;
        ImageView goods_img;
        CheckBox box;

        ImageView del_img;
        AmountView amountView;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tv_name = (TextView) itemView.findViewById(R.id.tv_goods_name);
            tv_price = (TextView) itemView.findViewById(R.id.tv_goods_price);
            box = (CheckBox) itemView.findViewById(R.id.checkBox);
            del_img = (ImageView) itemView.findViewById(R.id.del_img);
            goods_img = (ImageView) itemView.findViewById(R.id.goods_img);

            amountView = (AmountView) itemView.findViewById(R.id.amountView);
        }
    }

    /**
     * 监听条目 checkbox 改变接口
     */

    public interface OnItemBoxChangeListener {
        void onChange(boolean isSelectAll, int total, int count);
    }

    private OnItemBoxChangeListener onItemBoxChangeListener;

    public void setOnItemBoxChangeListener(OnItemBoxChangeListener onItemBoxChangeListener) {
        this.onItemBoxChangeListener = onItemBoxChangeListener;
    }

    /**
     * 监听商品数量改变的接口
     * <p>
     * 回传 cart_id,goods_id,及此商品数量
     */

    public interface OnGartChangeListener {
        void onChange(int amount, String cart_id, String goods_id);
    }

    private OnGartChangeListener onGartChangeListener;

    public void setOnGartChangeListener(OnGartChangeListener onGartChangeListener) {
        this.onGartChangeListener = onGartChangeListener;
    }


}
