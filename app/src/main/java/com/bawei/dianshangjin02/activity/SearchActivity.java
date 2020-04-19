package com.bawei.dianshangjin02.activity;

import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bawei.dianshangjin02.R;
import com.bawei.dianshangjin02.adapter.SearchShowAdapter;
import com.bawei.dianshangjin02.base.BaseActivity;
import com.bawei.dianshangjin02.base.BasePresenter;
import com.bawei.dianshangjin02.bean.SearchBean;
import com.bawei.dianshangjin02.contact.IContact;
import com.bawei.dianshangjin02.presenter.SearchPresenter;
import com.bawei.dianshangjin02.util.MyApplication;
import com.bawei.dianshangjin02.util.OkHttpUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 搜索页面
 */
public class SearchActivity extends BaseActivity {
    //定义
    private EditText search_text;
    private Button submit_search;
    private RecyclerView search_result;
    private SearchShowAdapter searchShowAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }
    @Override
    protected BasePresenter initPresenter() {
        return new SearchPresenter(new IContact.IView() {
            @Override
            public void requestSuccess(String json) {
                //解析数据
                SearchBean searchBean = MyApplication.getGson().fromJson(json, SearchBean.class);
                //判断
                if(searchBean.getStatus().equals("0000")){
                    int size = searchBean.getResult().size();
                    //判断
                    if(size != 0){
                        Toast.makeText(SearchActivity.this, searchBean.getMessage() + "，找到" + size + "件商品", Toast.LENGTH_LONG).show();
                        //添加数据并刷新
                        searchShowAdapter.getList().addAll(searchBean.getResult());
                        searchShowAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(SearchActivity.this, searchBean.getMessage() + "，未找到任何商品", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SearchActivity.this,searchBean.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void requestError(String error) {
                //弹框提示
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
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
        search_text = findViewById(R.id.search_text);
        submit_search = findViewById(R.id.submit_search);
        search_result = findViewById(R.id.search_result);
        //设置适配器
        search_result.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        searchShowAdapter = new SearchShowAdapter();
        search_result.setAdapter(searchShowAdapter);
        //点击事件
        submit_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OkHttpUtil.getOkHttpUtil().hasNet()){
                    //获取搜索数据
                    String searchText = search_text.getText().toString();
                    Map<String,String> map = new HashMap<>();
                    try {
                        //汉字处理
                        String encode = URLEncoder.encode(searchText, "UTF-8");
                        map.put("keyword",encode);
                        map.put("page","1");
                        map.put("count","50");
                        getBasePresenter().request(map);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    //提示
                    Toast.makeText(SearchActivity.this,"当前手机没有网络",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
