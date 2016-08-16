package com.demo.mvpdemo.bean;

/**
 * Created by zf on 16/8/15.
 */
public class HttpResult<T> {//根据实际项目定义数据结构
    public String msg;
    public String success;//": [0/1]
    public T result;
}