package com.jkb.supportfragment.demo;

import android.app.Application;
import android.os.Message;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jkb.commonlib.app.AppManager;
import com.jkb.commonlib.config.AppConfig;
import com.jkb.commonlib.db.DbManager;
import com.jkb.support.utils.LogUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;


/**
 * 该demo的Application类
 * Created by yj on 2017/5/3.
 */

public class SupportApplication extends Application {

    /*是否初始化*/
    private boolean isInit = false;

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    /**
     * 初始化App中
     */
    @Subscriber(mode = ThreadMode.ASYNC, tag = AppConfig.EventBusTAG.APP_INIT)
    public void initApp(Message message) {
        if (isInit) return;
        LogUtils.isAllowToPrint = BuildConfig.DEBUG;//是否允许打印Log
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);//初始化ARouter
        DbManager.getInstance().init(this);//初始化数据库
        AppManager.getInstance().init(this);//初始化App管理

        notifyAppInitCompleted();//通知App初始化完成
    }

    /**
     * 通知App初始化完成
     */
    private void notifyAppInitCompleted() {
        isInit = true;
        EventBus.getDefault().post(Message.obtain(), AppConfig.EventBusTAG.APP_INIT_COMPLECTED);
    }
}