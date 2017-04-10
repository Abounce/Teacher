package com.example.administrator.teacher.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.teacher.Modle.User;
import com.example.administrator.teacher.R;
import com.example.administrator.teacher.bean.bean.requset.StudentTypeBodyBean;
import com.example.administrator.teacher.interfaces.RequestCallBack;
import com.example.administrator.teacher.retrofit.RetrofitHelper;
import com.example.administrator.teacher.utils.Constact;
import com.example.administrator.teacher.utils.SignUtils;
import com.example.administrator.teacher.utils.ToastUtils;
import com.example.net.net_gson.NormalBean;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.RequestBody;

public class ChooseTorS extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_tor_s);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.bt_choose_student)
     void chooseStudent(){
        choose(1);
        startActivity(new Intent(this,StudentInfoActivity.class));


    }

    @OnClick(R.id.bt_choose_teacher)
    void chooseTeacher(){
        choose(2);
        startActivity(new Intent(this,TeatherInfoActivity.class));
    }

    private void choose(int type) {
        String time= Constact.GetCurrueTime();
        RequestBody requstBody = RetrofitHelper.getRequstBody(new StudentTypeBodyBean(type));
        Map<String, String> map=new HashMap<>();
        String url="http://192.168.199.157:8089/api/user/chooseIdentity?timestamp="+time;
        map.put("sign", SignUtils.sign(url,String.valueOf(type)));
        //从数据库中取出token
        User first = DataSupport.findFirst(User.class);
        String token = first.getToken();
        if (token!=null){
            map.put("ULoginToken",token);
        }
        Observable<NormalBean> studentType = RetrofitHelper.getmHttpApi().postStudentType(map, requstBody, time);
        RetrofitHelper.request(ChooseTorS.this, studentType, new RequestCallBack<NormalBean>() {
            @Override
            public void successful(NormalBean value) {

                ToastUtils.show("请完善个人信息");
            }

            @Override
            public void error() {
                ToastUtils.show("网络错误");
            }
        },false);
    }

}
