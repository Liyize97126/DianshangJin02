package com.bawei.dianshangjin02.util;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;

/**
 * 获取公共Context和Gson
 * 李易泽
 * 20200414
 */
public class MyApplication extends Application {
    //定义
    private static Context context;
    private static final Gson GSON = new Gson();
    //封装
    public static Gson getGson() {
        return GSON;
    }
    public static Context getContext() {
        return context;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        //设置
        context = this;
    }
}
