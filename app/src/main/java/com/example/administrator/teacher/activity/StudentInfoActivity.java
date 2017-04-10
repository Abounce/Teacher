package com.example.administrator.teacher.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.teacher.Modle.User;
import com.example.administrator.teacher.R;
import com.example.administrator.teacher.bean.bean.requset.StudentInfoBean;
import com.example.administrator.teacher.interfaces.RequestCallBack;
import com.example.administrator.teacher.retrofit.RetrofitHelper;
import com.example.administrator.teacher.utils.Constact;
import com.example.administrator.teacher.utils.SignUtils;
import com.example.administrator.teacher.utils.ToastUtils;
import com.example.administrator.teacher.utils.Validator;
import com.example.administrator.teacher.view.ActionSheetDialog;
import com.example.administrator.teacher.view.CommonHead;
import com.example.net.net_gson.NormalBean;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import okhttp3.RequestBody;

public class StudentInfoActivity extends TakePhotoActivity {


    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.et_studentName)
    EditText etStudentName;
    @BindView(R.id.radioMale)
    RadioButton radioMale;
    @BindView(R.id.radioFemale)
    RadioButton radioFemale;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.et_studentPassward)
    EditText etStudentPassward;
    @BindView(R.id.et_studentAge)
    EditText etStudentAge;
    @BindView(R.id.et_studentSchool)
    EditText etStudentSchool;

    @BindView(R.id.et_studentClass)
    EditText etStudentClass;
    @BindView(R.id.et_studentMajor)
    EditText etStudentMajor;
    @BindView(R.id.et_studentLevel)
    EditText etStudentLevel;
    private TakePhoto takePhoto;
    private String studnetName;
    private String studentPassward;
    private String studentAge;
    private String studentSchool;
    private String sudentClass;
    private String studentMajor;
    private String studentLevel;
    private CommonHead head;
    private int Sex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        ButterKnife.bind(this);
        //判断用户信息是否填写正确
        initHead();

    }

    private void initHead() {
        head = ((CommonHead) findViewById(R.id.common_head));
        HashMap<String, Object> map=new HashMap<>();
        map.put("center","学生信息");
        head.setHeadView(this,map);
        final TextView rightview = (TextView) head.findViewById(R.id.header_right_tv);
        rightview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightview.setText("保存");
                initData();

                postdata();

            }


        });
    }

    private void postdata() {
        StudentInfoBean studentInfoBean = new StudentInfoBean(studnetName, Sex, "wwww.baidu.com/img", studentPassward,
                studentAge, studentSchool, studentMajor, sudentClass, studentLevel);

        RequestBody requstBody = RetrofitHelper.getRequstBody(studentInfoBean);
        Map<String, String> map=new HashMap<>();
        //从数据库中取出token
        User user = DataSupport.findFirst(User.class);
        if (user!=null){
            String token = user.getToken();
            map.put("ULoginToken",token);
        }
        //sign
        //http://192.168.199.157:8089/api/student/updatestudent?timestamp=1490950151000
        String time = Constact.GetCurrueTime();

        String url="http://192.168.199.157:8089/api/student/updatestudent?timestamp="+ Constact.GetCurrueTime();
        map.put("sign", SignUtils.sign(url,studnetName,studentPassward));
        Observable<NormalBean> normalBeanObservable = RetrofitHelper.getmHttpApi().postStudentInfo(map, requstBody, time);
        RetrofitHelper.request(StudentInfoActivity.this, normalBeanObservable, new RequestCallBack<NormalBean>() {
            @Override
            public void successful(NormalBean value) {
                ToastUtils.show("保存个人信息成功");
            }

            @Override
            public void error() {

            }
        },true);


    }

    @OnClick(R.id.civ_head)
    public void head() {

        takePhoto = getTakePhoto();
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        final Uri imageUri = Uri.fromFile(file);

        new ActionSheetDialog(StudentInfoActivity.this).Builder()

                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.BULE, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int witch) {
                        takePhoto.onPickFromCapture(imageUri);
                    }
                }).addSheetItem("打开相册", ActionSheetDialog.SheetItemColor.BULE, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int witch) {
                takePhoto.onPickFromGallery();

            }
        }).show();

    }


    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        TImage image = result.getImage();
        Glide.with(this).load(image.getOriginalPath()).into(civHead);


    }

    private void initData() {

      studnetName = etStudentName.getText().toString().trim();
        if (TextUtils.isEmpty(studnetName)){
            ToastUtils.show("用户名不能为空");
            return;
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId==R.id.radioMale){
                 Sex=1;
                }
                if (checkedId==R.id.radioFemale){
                 Sex=0;
                }



            }
        });
        studentPassward = etStudentPassward.getText().toString().trim();
        boolean password = Validator.isPassword(studentPassward);
        if (!password){
            ToastUtils.show("秘密格式错误");
            return;
        }

        studentAge = etStudentAge.getText().toString().trim();
        boolean age = Validator.isAge(studentAge);
        if (!age){
            ToastUtils.show("请输入正确的年龄");
            return;

        }
        studentSchool = etStudentSchool.getText().toString().trim();

       if (TextUtils.isEmpty(studentSchool)){
           ToastUtils.show("学校不能为空");
           return;
        }
        sudentClass = etStudentClass.getText().toString().trim();

        if (TextUtils.isEmpty(sudentClass)){
            ToastUtils.show("年级不能为空");
            return;
        }
        studentMajor = etStudentMajor.getText().toString().trim();

        if (TextUtils.isEmpty(studentMajor)){
            ToastUtils.show("专业不能为空");
            return;
        }
        studentLevel = etStudentLevel.getText().toString().trim();

        if (TextUtils.isEmpty(studentLevel)){
            ToastUtils.show("等级不能为空");
            return;
        }


    }
}
