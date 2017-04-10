package com.example.administrator.teacher.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * @author yhy created at 2017/3/31 16:01
 */

public class DialogFactory {


    private static ProgressDialog progressDialog;

    public static void showLoggingDialog(Context context){

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("正在加载");
        progressDialog.setMessage("loggging...");
        progressDialog.setCancelable(true);
        progressDialog.show();

    }
    public static void dissmisLoggingDialog(){
        if (progressDialog!=null){

        progressDialog.dismiss();
        }
    }


}
