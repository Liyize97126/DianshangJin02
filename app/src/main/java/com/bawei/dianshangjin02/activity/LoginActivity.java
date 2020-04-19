package com.bawei.dianshangjin02.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.bawei.dianshangjin02.R;
import com.bawei.dianshangjin02.base.BaseActivity;
import com.bawei.dianshangjin02.base.BasePresenter;
import com.bawei.dianshangjin02.bean.LoginBean;
import com.bawei.dianshangjin02.contact.IContact;
import com.bawei.dianshangjin02.presenter.LoginPresenter;
import com.bawei.dianshangjin02.util.MyApplication;
import com.bawei.dianshangjin02.util.OkHttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity {
    //定义
    private EditText edit_phone,edit_pwd;
    private Button login;
    private Map<String,String> params;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
    @Override
    protected BasePresenter initPresenter() {
        return new LoginPresenter(new IContact.IView() {
            @Override
            public void requestSuccess(String json) {
                //开始解析
                LoginBean loginBean = MyApplication.getGson().fromJson(json, LoginBean.class);
                Toast.makeText(LoginActivity.this,loginBean.getMessage(),Toast.LENGTH_LONG).show();
                //判断
                if(loginBean.getStatus().equals("0000")){
                    //跳转界面
                    Intent intent = new Intent(LoginActivity.this, SearchActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            @Override
            public void requestError(String error) {
                //弹框提示
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("参数错误");
                builder.setMessage(error);
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create();
                builder.show();
            }
        });
    }
    @Override
    protected void initView() {
        //获取id
        edit_phone = findViewById(R.id.edit_phone);
        edit_pwd = findViewById(R.id.edit_pwd);
        login = findViewById(R.id.login);
        //点击登录事件
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取文本
                String phone = edit_phone.getText().toString();
                String pwd = edit_pwd.getText().toString();
                //判断
                if(TextUtils.isEmpty(phone) || TextUtils.isEmpty(pwd)){
                    //提示
                    Toast.makeText(LoginActivity.this,"请输入合法的手机号和密码",Toast.LENGTH_LONG).show();
                } else {
                    //判断网络
                    if(OkHttpUtil.getOkHttpUtil().hasNet()){
                        //初始化Params，并添加数据
                        params = new HashMap<>();
                        params.put("phone",phone);
                        params.put("pwd",pwd);
                        //发起请求
                        getBasePresenter().request(params);
                    } else {
                        //提示
                        Toast.makeText(LoginActivity.this,"当前手机没有网络",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
