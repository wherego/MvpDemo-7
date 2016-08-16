package com.demo.mvpdemo.http;

import com.demo.mvpdemo.bean.HttpResult;
import com.demo.mvpdemo.bean.IpBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zf on 16/8/15.
 */
public interface Api {
    @GET("/?app=ip.get&format=json")
    Observable<HttpResult<IpBean>> queryIp(@Query("ip") String ip, @Query("appkey") String appkey, @Query("sign") String sign);
}
