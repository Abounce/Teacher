package com.example.administrator.teacher.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.teacher.Modle.User;
import com.example.administrator.teacher.R;
import com.example.administrator.teacher.bean.bean.requset.RegistBodyBean;
import com.example.administrator.teacher.bean.bean.response.CodeBean;
import com.example.administrator.teacher.bean.bean.response.RegsitBean;
import com.example.administrator.teacher.interfaces.RequestCallBack;
import com.example.administrator.teacher.retrofit.RetrofitHelper;
import com.example.administrator.teacher.utils.Constact;
import com.example.administrator.teacher.utils.SignUtils;
import com.example.administrator.teacher.utils.ToastUtils;
import com.example.administrator.teacher.utils.Validator;
import com.example.administrator.teacher.view.CommonHead;
import com.jaeger.library.StatusBarUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import okhttp3.RequestBody;

import static com.example.administrator.teacher.R.id.get_code_button;

public class RegistActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.regist_phone)
    EditText registPhone;
    @BindView(R.id.regist_code)
    EditText registCode;
    @BindView(get_code_button)
    Button getCodeButton;
//    @BindView(R.id.regist_invited)
//    EditText registInvited;
    @BindView(R.id.next_step)
    Button nextStep;
    @BindView(R.id.about_us_http)
    TextView aboutUsHttp;
    private CommonHead head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);

        StatusBarUtil.setColor(RegistActivity.this,Color.parseColor("#f9b304"),0);

        initHead();
        getCodeButton.setOnClickListener(this);
        nextStep.setOnClickListener(this);

    }

    private void initHead() {
        head= (CommonHead) findViewById(R.id.common_head);
        HashMap<String, Object> map=new HashMap<>();
        map.put("left","left");
        map.put("center","注册");
        head.setHeadView(this,map);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case  R.id.next_step:
                //注册
                if (TextUtils.isEmpty(registPhone.getText().toString().trim())){
                    ToastUtils.show("手机号不能为空");

                    return;
                }

               if (!Validator.isMobile(registPhone.getText().toString().trim())){
                   ToastUtils.show("请输入正确的手机号");
                   return;
               }
               if (registPhone.getText().toString().trim().length()!=11){
                   ToastUtils.show("请输入正确的11位手机号");
                   return;
               }
                if (TextUtils.isEmpty(registCode.getText().toString().trim())){
                    ToastUtils.show("验证不能为空");
                    return;
                }
                if (registCode.getText().toString().trim().length()!=6){
                    ToastUtils.show("请输入正确的6位验证码");
                    return;
                }
                String time = Constact.GetCurrueTime();
                String url="http://192.168.199.157:8089/api/Message/phoneReg?timestamp="+time;
                RegistBodyBean bodyBean = new RegistBodyBean(registPhone.getText().toString().trim(), registCode.getText().toString().trim());
                RequestBody resp = RetrofitHelper.getRequstBody(bodyBean);

                Observable<RegsitBean> postRegistt = RetrofitHelper.getmHttpApi()
               .postRegist(SignUtils.sign(url, registPhone.getText().toString().trim(),registCode.getText().toString().trim()),resp,time);

               RetrofitHelper.request(RegistActivity.this,postRegistt, new RequestCallBack<RegsitBean>() {
                   @Override
                   public void successful(RegsitBean value) {
                       User user = new User();
                       user.setToken(value.getULoginToken());
                       user.updateAll();
                       ToastUtils.show("注册成功token是"+value.getULoginToken());
                       startActivity(new Intent(RegistActivity.this,ChooseTorS.class));
                       finish();

                   }

                   @Override
                   public void error() {
                       ToastUtils.show("注册失败");
                   }
               },true);



                   break;
            case  R.id.get_code_button:
                //获取验证码
                if (TextUtils.isEmpty(registPhone.getText().toString().trim())){
                    ToastUtils.show("手机号不能为空");

                    return;
                }

                if (!Validator.isMobile(registPhone.getText().toString().trim())){
                    ToastUtils.show("请输入正确的手机号");
                    return;
                }
                if (registPhone.getText().toString().trim().length()!=11){
                    ToastUtils.show("请输入正确的11位手机号");
                    return;
                }
                String phone = registPhone.getText().toString().trim();
                Observable<CodeBean> registBean = RetrofitHelper.getmHttpApi().getRegistBean(phone, Constact.GetCurrueTime());

                 RetrofitHelper.request(RegistActivity.this,registBean, new RequestCallBack<CodeBean>() {
                     @Override
                     public void successful(CodeBean value) {
                         CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(getCodeButton, 60000, 1000);
                         mCountDownTimerUtils.start();
                         Toast.makeText(RegistActivity.this, value.getResCode(), Toast.LENGTH_LONG).show();
                     }

                     @Override
                     public void error() {
                         ToastUtils.show("获取验证码失败");
                     }
                 },false);
                break;

        }
    }
    public class CountDownTimerUtils extends CountDownTimer {
        private Button mTextView;

        /**
         * @param textView          The TextView
         *
         *
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receiver
         *                          {@link #onTick(long)} callbacks.
         */
        public CountDownTimerUtils(Button textView, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.mTextView = textView;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTextView.setClickable(false); //设置不可点击
            mTextView.setText(millisUntilFinished / 1000 + "秒后可重新发送");  //设置倒计时时间
            mTextView.setBackgroundResource(R.drawable.bg_identify_code_press); //设置按钮为灰色，这时是不能点击的

            /**
             * 超链接 URLSpan
             * 文字背景颜色 BackgroundColorSpan
             * 文字颜色 ForegroundColorSpan
             * 字体大小 AbsoluteSizeSpan
             * 粗体、斜体 StyleSpan
             * 删除线 StrikethroughSpan
             * 下划线 UnderlineSpan
             * 图片 ImageSpan
             * http://blog.csdn.net/ah200614435/article/details/7914459
             */
            SpannableString spannableString = new SpannableString(mTextView.getText().toString());  //获取按钮上的文字
            ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
            /**
             * public void setSpan(Object what, int start, int end, int flags) {
             * 主要是start跟end，start是起始位置,无论中英文，都算一个。
             * 从0开始计算起。end是结束位置，所以处理的文字，包含开始位置，但不包含结束位置。
             */
            spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
            mTextView.setText(spannableString);
        }

        @Override
        public void onFinish() {
            mTextView.setText("重新获取验证码");
            mTextView.setClickable(true);//重新获得点击
            mTextView.setBackgroundResource(R.drawable.bg_identify_code_normal);  //还原背景色
        }
    }
}
