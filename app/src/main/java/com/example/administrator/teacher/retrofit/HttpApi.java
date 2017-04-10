package com.example.administrator.teacher.retrofit;

import com.example.administrator.teacher.bean.bean.response.CodeBean;
import com.example.net.net_gson.NormalBean;
import com.example.administrator.teacher.bean.bean.response.RegsitBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author yhy created at 2017/3/30 15:48
 */

public interface HttpApi {
    //Message/getRegMsg?phonenum=18398609020&timestamp=1489456647774
   @GET("Message/getRegMsg")
    Observable<CodeBean> getRegistBean(@Query("phonenum") String phone, @Query("timestamp") String time);

   @POST("Message/phoneReg")
    Observable<RegsitBean>  postRegist(@Header("sign") String sign , @Body RequestBody body , @Query("timestamp") String time);

   @POST("user/chooseIdentity")
    Observable<NormalBean>   postStudentType(@HeaderMap Map<String,String> parms, @Body RequestBody body, @Query("timestamp") String time);

//student/updatestudent?timestamp=1490950151000
    @POST("student/updatestudent")
    Observable<NormalBean> postStudentInfo(@HeaderMap Map<String,String> parms,@Body RequestBody body,@Query("timestamp") String time);

}
