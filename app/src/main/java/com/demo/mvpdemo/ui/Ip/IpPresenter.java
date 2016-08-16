package com.demo.mvpdemo.ui.Ip;

import com.demo.mvpdemo.bean.IpBean;
import com.demo.mvpdemo.http.Network;
import com.demo.mvpdemo.http.listener.OnSubscriberListener;

/**
 * Create by zf 16/8/16
 */
public class IpPresenter implements IpContract.Presenter {
    private IpContract.View view;

    public IpPresenter(IpContract.View view) {
        this.view = view;
    }

    @Override
    public void queryIp() {
        Network.getInstance().queryIp(view.getIP(), view.getAppKey(), view.getSign(), new OnSubscriberListener<IpBean>() {
            @Override
            public void onNext(IpBean bean) {
                view.setResult(bean.toString());
            }
        });
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
}
