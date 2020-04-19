package com.bawei.dianshangjin02.presenter;

import com.bawei.dianshangjin02.base.BasePresenter;
import com.bawei.dianshangjin02.contact.IContact;
import com.bawei.dianshangjin02.util.OkHttpUtil;

/**
 * 搜索Presenter
 */
public class SearchPresenter extends BasePresenter {
    //方法实现
    public SearchPresenter(IContact.IView iView) {
        super(iView);
    }
    @Override
    protected int getMethod() {
        return OkHttpUtil.GET;
    }
    @Override
    protected String getUrl() {
        return "http://mobile.bwstudent.com/small/commodity/v1/findCommodityByKeyword";
    }
}
