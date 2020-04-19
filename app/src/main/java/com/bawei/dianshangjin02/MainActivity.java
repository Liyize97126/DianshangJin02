package com.bawei.dianshangjin02;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bawei.dianshangjin02.contact.IContact;
import com.bawei.dianshangjin02.util.OkHttpUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //GET请求
    public void doGet(View view) {
        Map<String, String> map = new HashMap<>();
        String encode = null;
        try {
            encode = URLEncoder.encode("女鞋", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.put("keyword",encode);
        map.put("page","1");
        map.put("count","5");
        OkHttpUtil.getOkHttpUtil().request(OkHttpUtil.GET, "http://mobile.bwstudent.com/small/commodity/" +
                "v1/findCommodityByKeyword", map, new IContact.IModel() {
            @Override
            public void requestSuccess(String json) {
            }
            @Override
            public void requestError(String error) {
            }
        });
    }
    //POST请求
    public void doPost(View view) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", "17156891458");
        params.put("pwd", "123456aa");
        OkHttpUtil.getOkHttpUtil().request(OkHttpUtil.POST, "http://mobile.bwstudent.com/small/user/v1/login", params, new IContact.IModel() {
            @Override
            public void requestSuccess(String json) {
            }
            @Override
            public void requestError(String error) {
            }
        });
    }
}
