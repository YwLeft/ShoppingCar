package com.example.asus.cardemo.mode.net;

/**
 * 创建时间  2017/10/25 19:41
 * 创建人    gaozhijie
 * 类描述
 */
public interface NetDataCallBack<T> {
    void success(T t);
    void faild(int positon,String str);
}
