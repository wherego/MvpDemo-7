package com.demo.mvpdemo.ui.Ip;

import com.demo.mvpdemo.ui.base.IPresenter;
import com.demo.mvpdemo.ui.base.IView;

/**
 * Create by zf 16/8/16
 */
public interface IpContract {
    interface View extends IView<Presenter> {
        String getIP();
        String getAppKey();
        String getSign();

        void setResult(String result);
    }

    interface Presenter extends IPresenter {
        void queryIp();
    }
}
