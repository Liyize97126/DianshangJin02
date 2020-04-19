package com.bawei.dianshangjin02.base;

import android.util.Log;

import com.bawei.dianshangjin02.contact.IContact;
import com.bawei.dianshangjin02.util.OkHttpUtil;

import java.util.Map;

/**
 * Presenter基类
 */
public abstract class BasePresenter {
    //定义
    private IContact.IView iView;
    //构造
    public BasePresenter(IContact.IView iView) {
        this.iView = iView;
    }
    //调用网络请求方法
    public void request(Map<String,String> params){
        if(getMethod() == OkHttpUtil.GET){
            Log.i("Tag","发起了GET请求！");
        } else {
            Log.i("Tag","发起了POST请求！");
        }
        OkHttpUtil.getOkHttpUtil().request(getMethod(), getUrl(), params, new IContact.IModel() {
            @Override
            public void requestSuccess(String json) {
                //反馈
                iView.requestSuccess(json);
            }
            @Override
            public void requestError(String error) {
                //反馈
                iView.requestError(error);
            }
        });
    }
    //方法封装
    protected abstract int getMethod();
    protected abstract String getUrl();
    //释放资源方法
    public void destroy(){
        if(iView != null){
            iView = null;
        }
    }
}
