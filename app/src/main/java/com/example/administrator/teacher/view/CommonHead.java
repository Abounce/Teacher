package com.example.administrator.teacher.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.teacher.R;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.HashMap;

/**
 * @author yhy created at 2017/3/30 10:33
 */

public class CommonHead extends AutoRelativeLayout {

    private View view;
    public ImageView headerLeft;
    private TextView header_center;
    public TextView header_right_tv;
    public ImageView header_right_img;
    public CommonHead(Context context) {
        this(context,null);
    }

    public CommonHead(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CommonHead(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        view = LayoutInflater.from(context).inflate(R.layout.common_head,this,true);
        initView();
    }

    private void initView() {
        headerLeft = (ImageView) view.findViewById(R.id.header_left);
        header_center = (TextView) view.findViewById(R.id.header_center);
        header_right_tv = (TextView) view.findViewById(R.id.header_right_tv);
        header_right_img = (ImageView) view.findViewById(R.id.header_right_img);
    }

    public void setHeadView(final Activity activity, HashMap<String, Object> map){
        if (map.get("left") instanceof String){
            headerLeft.setImageResource(R.drawable.back);
            headerLeft.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            headerLeft.setBackgroundColor(Color.parseColor("#31a54d"));

                            break;
                        case MotionEvent.ACTION_UP:

                            headerLeft.setBackgroundColor(Color.parseColor("#37c067"));
                            activity.finish();
                            break;
                    }
                    return true;
                }
            });

        }
        if (map.get("center") != null) {
            header_center.setText((String)map.get("center"));
        }

    }
}
