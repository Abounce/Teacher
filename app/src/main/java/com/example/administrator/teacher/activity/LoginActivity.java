package com.example.administrator.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.teacher.R;
import com.example.administrator.teacher.utils.Constact;
import com.example.administrator.teacher.view.CommonHead;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.iv_three_login)
    ImageView ivThree;
    @BindView(R.id.login_phone)
    EditText loginPhone;
    @BindView(R.id.login_passwd)
    EditText loginPasswd;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.login_regist)
    TextView loginRegist;
    @BindView(R.id.login_message)
    TextView loginMessage;
    private static Tencent mTencent;
    private boolean isServerSideLogin;
    private CommonHead head;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mTencent = Tencent.createInstance(Constact.APP_ID, this.getApplicationContext());
        initHead();
        setListener();

    }

    private void initHead() {
        head= (CommonHead) findViewById(R.id.common_head);
        HashMap<String, Object> map=new HashMap<>();
        map.put("left","left");
        map.put("center","登陆");
        head.setHeadView(this,map);
    }

    private void setListener() {
        ivThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //QQ第三方登陆
                onClickLogin();
            }
        });
        login.setOnClickListener(this);
        loginRegist.setOnClickListener(this);
        loginMessage.setOnClickListener(this);
    }

    private void onClickLogin() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", loginListener);
            isServerSideLogin = false;
            //Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
        } else {
            if (isServerSideLogin) { // Server-Side 模式的登陆, 先退出，再进行SSO登陆
                mTencent.logout(this);
                mTencent.login(this, "all", loginListener);
                isServerSideLogin = false;
                // Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
                return;
            }
            mTencent.logout(this);

        }
    }

    private IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            //  Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
            initOpenidAndToken(values);

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                //登陆
                break;
            case R.id.login_regist:
                //注册
                startActivity(new Intent(this,RegistActivity.class));
                break;
            case R.id.login_message:
                //短信登陆
                break;
        }
    }


    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                //Util.showResultDialog(LoginActivity.this, "返回为空", "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                //  Util.showResultDialog(LoginActivity.this, "返回为空", "登录失败");
                return;
            }
            //Util.showResultDialog(LoginActivity.this, response.toString(), "登录成功");
            // 有奖分享处理
            // handlePrizeShare();
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {


        }

        @Override
        public void onError(UiError e) {
//            Util.toastMessage(LoginActivity.this, "onError: " + e.errorDetail);
//            Util.dismissDialog();
        }

        @Override
        public void onCancel() {
//            Util.toastMessage(LoginActivity.this, "onCancel: ");
//            Util.dismissDialog();
//            if (isServerSideLogin) {
//                isServerSideLogin = false;
//            }
        }
    }

    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch (Exception e) {
        }
    }




}
