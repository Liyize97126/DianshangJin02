package com.bawei.dianshangjin02.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    //定义
    private ActionBar actionBar;
    private BasePresenter basePresenter;
    public BasePresenter getBasePresenter() {
        return basePresenter;
    }
    //页面创建
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载视图
        setContentView(getLayoutId());
        //页面效果
        actionBar = getSupportActionBar();
        actionBar.hide();
        //获取Presenter
        basePresenter = initPresenter();
        //处理视图数据
        initView();
    }
    //方法封装
    protected abstract int getLayoutId();
    protected abstract BasePresenter initPresenter();
    protected abstract void initView();
    //释放资源
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //判断
        if(basePresenter != null){
            basePresenter.destroy();
            basePresenter = null;
        }
    }
}
