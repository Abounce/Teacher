package com.example.administrator.teacher.utils;

import android.widget.Toast;

/**
 * @author yhy created at 2017/3/30 16:10
 */

public class ToastUtils {
    private static Toast toast;

    public static void show(String data){
        if (toast==null){
            toast=Toast.makeText(BaseApplication.getApplication().getApplicationContext(),data,Toast.LENGTH_SHORT);
        }else {
            toast.setText(data);
        }
        toast.show();
    }
}
