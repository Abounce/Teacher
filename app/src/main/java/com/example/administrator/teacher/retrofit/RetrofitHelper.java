package com.example.administrator.teacher.retrofit;

import android.content.Context;

import com.example.administrator.teacher.interfaces.RequestCallBack;
import com.example.administrator.teacher.utils.DialogFactory;
import com.example.net.RetrofitManager;
import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author yhy created at 2017/3/30 15:48
 */

public class RetrofitHelper {

    //http://192.168.199.157:8089/api/
    public static final String BASE_URL="http://192.168.199.157:8089/api/";
    private static Gson gson;

    private static final  HttpApi mHttpApi= RetrofitManager.getInstance().newRetrofit(BASE_URL).create(HttpApi.class);

    public static HttpApi getmHttpApi(){

        return mHttpApi;

    }



   //传入一个bean 构建RequestBody
    public static<T> RequestBody getRequstBody(T object){
       if (gson==null){
        gson=new Gson();
       }
        String data = gson.toJson(object);
       RequestBody resp=RequestBody.create(MediaType.parse("application/json"), data);
        return resp;
    }

    public static<T> void request(final Context context, Observable<T> observable, final RequestCallBack<T> callBack, final Boolean isShow){
        observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (isShow){

                        DialogFactory.showLoggingDialog(context);
                        }
                    }
                })
                   .subscribeOn(AndroidSchedulers.mainThread())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new Observer<T>() {
                       @Override
                       public void onSubscribe(Disposable d) {

                       }

                       @Override
                       public void onNext(T value) {
                           DialogFactory.dissmisLoggingDialog();
                           callBack.successful(value);

                       }

                       @Override
                       public void onError(Throwable e) {
                           DialogFactory.dissmisLoggingDialog();
                           callBack.error();
                       }

                       @Override
                       public void onComplete() {

                       }
                   });
    }



}
