package com.example.administrator.teacher.bean.bean.requset;

/**
 * @author yhy created at 2017/4/1 10:35
 */

public class StudentTypeBodyBean {
    private int Identity;

    public StudentTypeBodyBean(int identity) {
        Identity = identity;
    }

    public int getIdentity() {
        return Identity;
    }

    public void setIdentity(int identity) {
        Identity = identity;
    }
}
