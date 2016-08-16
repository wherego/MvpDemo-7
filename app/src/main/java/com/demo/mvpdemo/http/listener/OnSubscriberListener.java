package com.demo.mvpdemo.http.listener;

/**
 * Created by zf on 16/8/16.
 */
public abstract class OnSubscriberListener<T> {
    public void onCompleted() {
    }

    public void onError(Throwable e) {
    }

    public abstract void onNext(T result);
}
