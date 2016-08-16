package com.demo.mvpdemo.http;

import android.content.Context;
import android.text.TextUtils;

import com.demo.mvpdemo.BuildConfig;
import com.demo.mvpdemo.app.MyApp;
import com.demo.mvpdemo.bean.HttpResult;
import com.demo.mvpdemo.http.exception.ApiException;
import com.demo.mvpdemo.http.listener.OnSubscriberListener;
import com.demo.mvpdemo.util.OkHttpLogger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zf on 16/8/15.
 */
public class Network {
    private static Network sInstance;

    private Context context;
    private Api api;

    public synchronized static Network getInstance() {
        if (sInstance == null) {
            sInstance = new Network(MyApp.getContext());
        }
        return sInstance;
    }

    private Network(Context context) {
        this.context = context;
        initApi();
    }

    private void initApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(getDefaultClient())
                .baseUrl("http://api.k780.com:88")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        api = retrofit.create(Api.class);
    }

    private OkHttpClient getDefaultClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request());
            }
        });
        if(BuildConfig.DEBUG){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new OkHttpLogger());
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

        return builder.build();
    }

    public <T> Subscription queryIp(String ip, String appkey, String sign, OnSubscriberListener<T> listener) {
        Observable observable = api.queryIp(ip, appkey, sign);
        return subscribeData(observable, listener);
    }

    /**
     * 直接处理HttpResult中result对象
     */
    private  <T> Subscription subscribeData(Observable<? extends HttpResult<T>> observable, OnSubscriberListener<T> listener) {
        return observable.map(new HttpDataFunc<T>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultSubscriber<>(context, listener));
    }

    /**
     * 直接处理HttpResult对象
     */
    private <T> Subscription subscribe(Observable<? extends HttpResult<T>> observable, OnSubscriberListener<HttpResult<T>> listener) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultSubscriber<>(context, listener));
    }

    private class HttpDataFunc<T> implements Func1<HttpResult<T>, T> {
        @Override
        public T call(HttpResult<T> result) {
            if (!TextUtils.isEmpty(result.msg)) {//error：根据条件自己判断
                throw new ApiException(result.msg);
            }
            return result.result;
        }
    }
}
