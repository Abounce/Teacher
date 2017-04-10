package com.example.net.interceptor;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 介绍：偷天换日 统一追加Header 参数

 */
public class AppendHeaderParamInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        //核心也是通过newBuilder 拿到Builder
        Headers.Builder builder = request.
                headers().
                newBuilder();

        //统一追加header参数
        Headers newHeader = builder.add("content-type", "application/json")
                .add("sign", "7452efed574879812f8bcd1fa39cac17")
                .build();

        Request newRequest = request.newBuilder()
                .headers(newHeader)
                .build();


        return chain.proceed(newRequest);
    }
}
