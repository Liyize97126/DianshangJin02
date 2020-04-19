package com.bawei.dianshangjin02.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import com.bawei.dianshangjin02.contact.IContact;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * OkHttp网络工具类
 */
public class OkHttpUtil {
    //定义
    private OkHttpClient okHttpClient;
    public static final int GET = 0;
    public static final int POST = 1;
    //使用Handler把okhttp异步请求结果从子线程发送到主线程
    private Handler handler = new Handler();
    //单例（饿汉式）
    private static final OkHttpUtil OK_HTTP_UTIL = new OkHttpUtil();
    private OkHttpUtil() {
        //okhttp的log拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //日志级别
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        //OKhttp使用建造者模式创建对象
        okHttpClient = new OkHttpClient.Builder()
                //拦截器
                .addInterceptor(httpLoggingInterceptor)
                //设置超时时间（单位不同，都是5秒钟）
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.MILLISECONDS)
                .writeTimeout(5000,TimeUnit.MILLISECONDS)
                //完成建造
                .build();
    }
    public static OkHttpUtil getOkHttpUtil() {
        return OK_HTTP_UTIL;
    }
    //封装网络判断方法
    public boolean hasNet(){
        //判断网络
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo != null && activeNetworkInfo.isAvailable()){
            return true;
        }
        return false;
    }
    //发起请求
    public void request(int method, String url, Map<String,String> params, IContact.IModel iModel){
        //根据method的值选择合适的方法去调用
        switch (method){
            case GET:{doGet(url,params,iModel);}break;
            case POST:{doPost(url,params,iModel);}break;
        }
    }
    //Get请求
    private void doGet(String url, Map<String,String> params, final IContact.IModel iModel){
        //整合参数
        StringBuffer buffer = new StringBuffer();
        buffer.append("?");
        for (String key : params.keySet()) {
            buffer.append(key + "=" + params.get(key) + "&");
        }
        //去截取，并为url添加参数
        String pam = buffer.substring(0, buffer.length() - 1);
        //构建Request请求对象
        Request request = new Request.Builder()
                .url(url + pam)//链接
                .get()//请求方式
                .build();
        //构建一个Call回调对象，进行异步请求
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                e.printStackTrace();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //反馈
                        iModel.requestError(e.getMessage());
                    }
                });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //获取结果
                final String string = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //反馈
                        iModel.requestSuccess(string);
                    }
                });
            }
        });
    }
    //Post请求
    private void doPost(String url, Map<String,String> params, final IContact.IModel iModel){
        //封装参数（通过FormBody.Builder封装）
        FormBody.Builder builder = new FormBody.Builder();
        //遍历params，添加数据
        for (String key : params.keySet()) {
            //添加操作
            builder.add(key,params.get(key));
        }
        //构建Request请求对象
        Request request = new Request.Builder()
                .url(url)//链接
                .post(builder.build())//请求方式
                .build();
        //构建一个Call回调对象，进行异步请求（相当于子线程，其回调结果也处于子线程中）
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                e.printStackTrace();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //反馈
                        iModel.requestError(e.getMessage());
                    }
                });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //获取结果
                final String string = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //反馈
                        iModel.requestSuccess(string);
                    }
                });
            }
        });
    }
}
