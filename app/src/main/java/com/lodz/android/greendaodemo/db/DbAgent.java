package com.lodz.android.greendaodemo.db;

import io.reactivex.Observable;

/**
 * 数据库转换代理
 * Created by zhouL on 2018/5/11.
 */
public class DbAgent<T> {

    private Observable<T> mData;

    DbAgent(Observable<T> mData) {
        this.mData = mData;
    }

    public Observable<T> rx() {
        return mData;
    }

    public T sync() {
        return mData.blockingFirst();
    }
}
