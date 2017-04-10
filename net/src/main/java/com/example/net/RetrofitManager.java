package com.example.net;

import android.content.Context;

import com.example.net.interceptor.PreHandleNoNetInterceptor;
import com.example.net.net_gson.CstGsonConverterFactory;
import com.example.net.net_util.NetUtils;
import com.example.net.net_util.NetworkConfig;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * @author yhy created at 2017/3/20 15:03
 *
 */


public class RetrofitManager {
    private static Context mContext;

    private OkHttpClient mOkHttpClient;
    private static class InnerHolder {
        private static RetrofitManager INSTACE = new RetrofitManager();
    }

    public static void init(Context context){
        mContext=context.getApplicationContext();
    }

    //单例获取retrofitmanger实例
    public static RetrofitManager getInstance() {
        return InnerHolder.INSTACE;
    }

    private RetrofitManager() {
        //在构造方法里 最终是为了得到一个单例的OkhttpClient实例
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //按照顺序执行拦截器
      builder.addInterceptor(new PreHandleNoNetInterceptor());

        //自动追加url参数
       // builder.addInterceptor(new AppendUrlParamInterceptor());

        //自动追加header
       // builder.addInterceptor(new AppendHeaderParamInterceptor());


        //将URl参数->Body
       // builder.addInterceptor(new AppenBodyParamsInterceptor());






        //缓存设置
        //构建缓存位置
        File cacheFile = new File(mContext.getExternalCacheDir(), "retrofit_cache");
        final Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //如果没有网络
                if (!NetUtils.isNetwork()) {
                    //取  缓存
                    Request newRequest = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                    return chain.proceed(newRequest);
                } else {
                    // 有网 存

                    int maxTime = 60 * 60 * 24;
                    Response response = chain.proceed(request);
                    Response newResponse = response.newBuilder()

                            //套路代码 "http cache "
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxTime)
                            .removeHeader("Progma")

                            .build();
                    return newResponse;
                }
            }
        };
        builder.cache(cache).addInterceptor(cacheInterceptor);


        //okhttp 设置超时
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);


        //错误重连
        builder.retryOnConnectionFailure(true);

        //OkHttp的Log信息拦截器
        if (NetworkConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        mOkHttpClient=builder.build();
    }

        // 拿到Retrofit实例
    public Retrofit newRetrofit(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)

                //引入Gson解析库 ，就可以直接以实体的形式拿到返回值
                //.addConverterFactory(GsonConverterFactory.create())

                //加入我们自定义的Gson解析库，就可以更友好的处理错误
                .addConverterFactory(CstGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                //将我们客制化的OkHttp实例传入
                .client(mOkHttpClient)

                .build();
    }
}
