package com.example.net.net_util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 介绍：Net工具类
 */
public class NetUtils {
    private static Context context;


    //初始化
    public static void init(Context c) {
        context = c.getApplicationContext();
    }

    /**
     * 是否联网
     *
     * @return
     */
    public static boolean isNetwork() {
        if (null != context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null == cm || (null != cm && null == cm.getActiveNetworkInfo())) {
                return false;
            }
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null) {
                return false;
            }
            return info.isAvailable();
        }
        return false;
    }
}
