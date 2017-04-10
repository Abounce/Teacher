package com.example.administrator.teacher.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.example.administrator.teacher.Modle.User;
import com.example.administrator.teacher.R;
import com.example.administrator.teacher.utils.ImageUitils;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashActivity extends Activity {

    @BindView(R.id.iv_top_ico)
    ImageView ivTopIco;
    @BindView(R.id.iv_splash_root)
    ImageView ivSplashRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);


        ImageUitils.GlidelocalImage(this,R.drawable.main_png_2,ivTopIco);
        ImageUitils.GlidelocalImage(this,R.drawable.main_png_1,ivSplashRoot);

        final List<User> userList = DataSupport.select("version").find(User.class);
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (userList==null||userList.size()==0){
                    //存储版本号
                    User user = new User();
                    user.setVersion(getVersionName());
                    user.save();
                    //跳转欢迎页面
                    startActivity(new Intent(SplashActivity.this,WelcomeActivity.class));
                    finish();
                }else if (userList.size()==1){
                    if (userList.get(0).getVersion()<getVersionName()){
                        //更新数据库版本号
                        User user = new User();
                        user.setVersion(getVersionName());
                        user.updateAll();
                        //跳转欢迎页面
                        startActivity(new Intent(SplashActivity.this,WelcomeActivity.class));
                        finish();
                    }else {
                        //跳转主页面
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                        finish();
                    }

                }
                return true;
            }
        }).sendEmptyMessageDelayed(0,2000);


    }


    public int getVersionName(){
        // 获取packagemanager的实例
        int version = 0;
        try {
            PackageManager packageManager = getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
            version = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
}
