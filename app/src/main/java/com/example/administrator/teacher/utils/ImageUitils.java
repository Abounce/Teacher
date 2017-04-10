package com.example.administrator.teacher.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * @author yhy created at 2017/3/29 16:21
 */

public class ImageUitils {
    //加载网络图片
    public static void GlideUrlImage(Context context, String url,int error, ImageView imageView){
        Glide.with(context)
                .load(url)
                .error(error)
                .crossFade()
                .into(imageView);
    }
    //加载本地图片
    public static void GlidelocalImage(Context context,int resourceId,ImageView imageView){
        Glide.with(context)
                .load(resourceId)
                .crossFade()
                .into(imageView);
    }
}
