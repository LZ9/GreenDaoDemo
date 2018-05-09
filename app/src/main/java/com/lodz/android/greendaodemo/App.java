package com.lodz.android.greendaodemo;

import com.lodz.android.component.base.application.BaseApplication;
import com.lodz.android.greendaolib.GreenDaoManager;

/**
 * Application
 * Created by zhouL on 2018/5/9.
 */
public class App extends BaseApplication{
    @Override
    protected void afterCreate() {
        initGreenDao();
        configTitleBarLayout();
    }

    /** 初始化数据库 */
    private void initGreenDao() {
        GreenDaoManager.get()
                .init(this)// 初始化数据库
                .setPrintLog(true);//打开数据库日志
    }

    /** 配置标题栏 */
    private void configTitleBarLayout() {
        getBaseLayoutConfig().getTitleBarLayoutConfig().setBackgroundColor(R.color.colorAccent);
        getBaseLayoutConfig().getTitleBarLayoutConfig().setTitleTextColor(R.color.white);
    }

    @Override
    protected void beforeExit() {

    }
}
