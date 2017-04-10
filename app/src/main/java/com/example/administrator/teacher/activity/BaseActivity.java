package com.example.administrator.teacher.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.teacher.utils.AcitivityUtils;
import com.jaeger.library.StatusBarUtil;

/**
 * @author yhy created at 2017/3/31 17:51
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AcitivityUtils.getInstance().addActivity(this);
        StatusBarUtil.setColor(this, Color.parseColor("#f9b304"),0);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AcitivityUtils.getInstance().removeActrivity(this);
    }

}
