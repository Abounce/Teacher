package com.example.net.net_gson;

/**
 * 介绍：与服务器约定好的 统一返回格式

 */
public class BaseBean<T> {
    private T Data;
    private int errcode;
    private String errormsg;
    private int result;

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
