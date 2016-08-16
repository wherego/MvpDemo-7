package com.demo.mvpdemo.http;

import android.content.Context;
import android.widget.Toast;

import com.demo.mvpdemo.http.listener.OnSubscriberListener;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by zf on 16/8/16.
 */
public class ResultSubscriber<T> extends Subscriber<T> {
    private Context mContext;
    private OnSubscriberListener<T> mListener;

    public ResultSubscriber(Context context, OnSubscriberListener<T> listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    public void onCompleted() {
        if (mListener != null) {
            mListener.onCompleted();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(mContext, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(mContext, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (mListener != null) {
            mListener.onError(e);
        }

    }

    @Override
    public void onNext(T t) {
        if (mListener != null) {
            mListener.onNext(t);
        }
    }
}
