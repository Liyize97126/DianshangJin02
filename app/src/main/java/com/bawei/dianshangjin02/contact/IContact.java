package com.bawei.dianshangjin02.contact;

/**
 * 契约类
 */
public interface IContact {
    //Presenter→View
    interface IView{
        void requestSuccess(String json);
        void requestError(String error);
    }
    //Model→Presenter
    interface IModel{
        void requestSuccess(String json);
        void requestError(String error);
    }
}
