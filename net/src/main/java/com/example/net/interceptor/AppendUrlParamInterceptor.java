package com.example.net.interceptor;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 介绍：偷天换日 url统一追加参数
 */
public class AppendUrlParamInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //▲偷天换日
        Request request = chain.request();

        //拿到拥有以前的request 里的 url 的 那些信息的buidler
        HttpUrl.Builder builder = request.
                url().
                //▲
                newBuilder();

        //得到新的Url（已经追加好同一的参数）      注意---------------需要自己根据需要定义
        //For wwww.baidu.com?name="lisi'&age=19
        HttpUrl newUrl = builder.addQueryParameter("deviceId", "1234456")
                .addQueryParameter("token", "iamtoken")
                .addQueryParameter("appver", "1.0.0-beta")
                .build();

        //利用新的Url 构建新的Request ，并发送给服务器
        Request newRequest = request.newBuilder()
                .url(newUrl)
                .build();

        //▲
        return chain.proceed(newRequest);
    }
}
