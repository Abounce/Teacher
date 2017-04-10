package com.example.administrator.teacher.utils;

import android.app.Application;

import com.example.net.RetrofitManager;
import com.example.net.net_util.NetUtils;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * @author yhy created at 2017/3/30 9:11
 */

public class BaseApplication extends LitePalApplication {
    private static  Application application;
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        RetrofitManager.init(this);
        NetUtils.init(this);
        application=this;
    }

    public static  Application getApplication(){
        return application;
    }
}
