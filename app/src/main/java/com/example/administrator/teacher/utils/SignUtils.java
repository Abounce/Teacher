package com.example.administrator.teacher.utils;

import android.util.Log;

/**
 * @author yhy created at 2017/3/31 9:21
 */

public class SignUtils {
//http://192.168.199.157:8089/api/Message/phoneReg?timestamp=1489556647774
    public static String sign(String url,String...addstring){


        String mSign=null;
        String[] split = url.split("[?]");
        String[] left = split[0].split("[/]");
        String leftdata = left[left.length - 1];
        String[] right = split[1].split("timestamp=");
        String rightdata = right[1];
       if (addstring.length==0){
           mSign=leftdata+rightdata;
       }else {
               mSign=leftdata+rightdata;
           for (int i = 0; i < addstring.length; i++) {
               mSign+=addstring[i];
           }
       }
        mSign= mSign+"ADX_H_C_SENDSADX_H_C_SENDSADX_H_C_SENDS";

        Log.e( "sign: ----------------------", mSign);
        return  Md5Tolls.encrypt(mSign);
    }
}
