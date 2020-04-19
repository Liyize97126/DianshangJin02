package com.bawei.dianshangjin02.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawei.dianshangjin02.R;
import com.bawei.dianshangjin02.bean.SearchBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表适配器
 */
public class SearchShowAdapter extends RecyclerView.Adapter<SearchShowAdapter.MyViewHouler> {
    //定义集合
    private List<SearchBean.ResultBean> list = new ArrayList<>();
    public List<SearchBean.ResultBean> getList() {
        return list;
    }
    //方法实现
    @NonNull
    @Override
    public MyViewHouler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.listcontent, parent, false);
        return new MyViewHouler(inflate);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHouler holder, int position) {
        //设置数据
        SearchBean.ResultBean resultBean = list.get(position);
        holder.commodityName.setText(resultBean.getCommodityName());
        holder.price.setText("￥" + resultBean.getPrice());
        //设置图片
        RequestOptions options = new RequestOptions()
                .error(R.drawable.pichead)
                .fallback(R.drawable.pichead)
                .placeholder(R.drawable.pichead)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)));
        Glide.with(holder.masterPic.getContext())
                .applyDefaultRequestOptions(options)
                .load(resultBean.getMasterPic())
                .into(holder.masterPic);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    //寄存器
    public class MyViewHouler extends RecyclerView.ViewHolder {
        protected ImageView masterPic;
        protected TextView commodityName,price;
        public MyViewHouler(@NonNull View itemView) {
            super(itemView);
            masterPic = itemView.findViewById(R.id.masterPic);
            commodityName = itemView.findViewById(R.id.commodityName);
            price = itemView.findViewById(R.id.price);
        }
    }
}
