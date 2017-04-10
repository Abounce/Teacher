package com.example.administrator.teacher.Modle;

import org.litepal.crud.DataSupport;

/**
 * @author yhy created at 2017/3/29 17:00
 */

public class User extends DataSupport {
    private String name;
    private String head;
    private int version;
    private String phone;
    private String token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
