package com.example.administrator.teacher.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yhy created at 2017/4/6 9:59
 * 活动管理工具类
 */

public class AcitivityUtils {
    private  List<Activity> activityList=new ArrayList<>();
    private static AcitivityUtils instance;
    private AcitivityUtils() {

    }
    public static AcitivityUtils getInstance(){
        if (instance==null){
            synchronized (AcitivityUtils.class){
                if (instance==null){
                    instance=new AcitivityUtils();
                }
            }
        }
        return instance;
    }


    public   void addActivity(Activity activity){
        activityList.add(activity);
    }
    public  void removeActrivity(Activity activity){

        activityList.remove(activity);
    }
    public  void finishAllAcvity(){
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
        //杀掉当前进程
        //android.os.Process.killProcess(android.os.Process.myPid());
    }

}
