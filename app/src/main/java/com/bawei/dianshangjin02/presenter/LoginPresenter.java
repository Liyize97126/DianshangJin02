package com.bawei.dianshangjin02.presenter;

import com.bawei.dianshangjin02.base.BasePresenter;
import com.bawei.dianshangjin02.contact.IContact;
import com.bawei.dianshangjin02.util.OkHttpUtil;

/**
 * 登录请求Presenter
 */
public class LoginPresenter extends BasePresenter {
    //方法实现
    public LoginPresenter(IContact.IView iView) {
        super(iView);
    }
    @Override
    protected int getMethod() {
        return OkHttpUtil.POST;
    }
    @Override
    protected String getUrl() {
        return "http://mobile.bwstudent.com/small/user/v1/login";
    }
}
