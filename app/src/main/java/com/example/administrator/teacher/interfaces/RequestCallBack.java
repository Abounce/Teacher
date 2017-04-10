package com.example.administrator.teacher.interfaces;

/**
 * @author yhy created at 2017/3/31 15:20
 */

public interface RequestCallBack<T> {
    void successful(T value);
    void error();
}
