package com.example.administrator.teacher.bean.bean.requset;

/**
 * @author yhy created at 2017/3/31 10:45
 */

public class RegistBodyBean {
    private String Phone;
    private String RegCode;

    public RegistBodyBean(String phone, String regCode) {
        Phone = phone;
        RegCode = regCode;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getRegCode() {
        return RegCode;
    }

    public void setRegCode(String regCode) {
        RegCode = regCode;
    }
}
