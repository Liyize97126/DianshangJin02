package com.bawei.dianshangjin02;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //GET请求
    public void doGet(View view) {
        //okhttp的log拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //设置日志级别，如果是body就是输出所有日志
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        //OKhttp使用建造者模式创建对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //Log日志拦截器
                .addInterceptor(httpLoggingInterceptor)
                .build();
        //构建Request请求对象
        Request request = new Request.Builder()
                //链接
                .url("http://mobile.bwstudent.com/small/commodity/" +
                        "v1/findCommodityByKeyword?keyword=女鞋&page=1&count=5")
                //请求方式
                .get()
                //完成建造
                .build();
        //构建一个Call回调对象，进行同步和异步请求
        Call call = okHttpClient.newCall(request);
        //同步请求
        /*try {
            Response execute = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //异步请求（相当于子线程，请求结果也是子线程中）
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("Tag","线程名：" + Thread.currentThread().getName());
                Log.d("Tag","请求方式：GET");
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //获取结果，是string()方法，不是tostring()【只能调用一次】
                String result = response.body().string();
                Log.d("Tag","线程名：" + Thread.currentThread().getName());
                Log.d("Tag","请求方式：GET");
            }
        });
    }
    //POST请求
    public void doPost(View view) {
        //okhttp的log拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //设置日志级别，如果是body就是输出所有日志
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        //OKhttp使用建造者模式创建对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //Log日志拦截器
                .addInterceptor(httpLoggingInterceptor)
                .build();
        //封装post参数
        FormBody formBody = new FormBody.Builder()
                .add("phone", "17156891458")
                .add("pwd", "123456aa")
                .build();
        //构建Request请求对象
        Request request = new Request.Builder()
                //链接
                .url("http://mobile.bwstudent.com/small/user/v1/login")
                //请求方式
                .post(formBody)
                //完成建造
                .build();
        //构建一个Call回调对象，进行同步和异步请求
        Call call = okHttpClient.newCall(request);
        //异步请求（相当于子线程，请求结果也是子线程中）
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("Tag","线程名：" + Thread.currentThread().getName());
                Log.d("Tag","请求方式：POST");
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //获取结果，是string()方法，不是tostring()【只能调用一次】
                String result = response.body().string();
                Log.d("Tag","线程名：" + Thread.currentThread().getName());
                Log.d("Tag","请求方式：POST");
            }
        });
    }
}
