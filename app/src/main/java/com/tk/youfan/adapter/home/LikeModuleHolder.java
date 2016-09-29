package com.tk.youfan.adapter.home;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tk.youfan.R;
import com.tk.youfan.base.BaseHolder;
import com.tk.youfan.domain.EventMessage;
import com.tk.youfan.domain.home.Data;
import com.tk.youfan.domain.home.HomeData;
import com.tk.youfan.domain.home.LikeModuleProduct;
import com.tk.youfan.domain.home.Module;
import com.tk.youfan.utils.LogUtil;
import com.tk.youfan.utils.UrlContants;
import com.tk.youfan.view.MyRecycleView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 作者：tpkeeper on 2016/9/29 06:52
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class LikeModuleHolder extends BaseHolder {

    TextView c_title;
    TextView e_title;
    MyRecycleView recyclerview_like_module;
    private List<LikeModuleProduct> likeModuleProducts;

    public LikeModuleHolder(Context mContext, View itemView) {
        super(mContext, itemView);
        c_title = (TextView) itemView.findViewById(R.id.c_title);
        e_title = (TextView) itemView.findViewById(R.id.e_title);
        recyclerview_like_module = (MyRecycleView) itemView.findViewById(R.id.recyclerview_like_module);
    }

    @Override
    public void setData(Module module) {
        List<Data> dataList = module.getData();
        c_title.setText(module.getC_title());
        e_title.setText(module.getE_title());

        getDataFromNet();
    }

    /**
     * 从网络获取数据
     */
    private void getDataFromNet() {
        OkHttpUtils.get()
                .url(UrlContants.HOMECASH_MAN_MORE)
                .id(100)
                .build()
                .execute(new MyStringCallBack());
    }

    private class MyStringCallBack extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            LogUtil.e("okhttputils load data err ！");
        }

        @Override
        public void onResponse(String response, int id) {
            processData(response);
        }
    }

    private void processData(String response) {
        if (TextUtils.isEmpty(response)) {
            LogUtil.e("home response is empty !!");
            return;
        }
        JSONObject jsonObject = JSON.parseObject(response);
        String data = jsonObject.getString("data");
        JSONObject jsonData = JSON.parseObject(data);
        String list = jsonData.getString("list");
        likeModuleProducts = JSON.parseArray(list, LikeModuleProduct.class);
        LogUtil.e("tttttttttttttttttttttttttt");
        EventBus.getDefault().post(new EventMessage(EventMessage.MESSAGE_DATA_GETED_HomeData_LIKE_MODULE));
    }

    @Subscribe
    public void onEventMessage(EventMessage eventMessage) {
        switch (eventMessage.getMessage()) {
            case EventMessage.MESSAGE_DATA_GETED_HomeData_LIKE_MODULE:
                LogUtil.e("yyyyyyyyyyyyyyyyyyyyy");
                initRecycleView();
                break;
        }
    }

    private void initRecycleView() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        LikeModuleAdapter likeModuleAdapter = new LikeModuleAdapter();
        recyclerview_like_module.setAdapter(likeModuleAdapter);
        recyclerview_like_module.setLayoutManager(staggeredGridLayoutManager);
        LogUtil.e("3333333333333333333"+likeModuleProducts.size());
    }
    class LikeModuleAdapter extends RecyclerView.Adapter<MyHolder>{


        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_like_module,parent,false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.setData(likeModuleProducts.get(position));
        }

        @Override
        public int getItemCount() {
            return likeModuleProducts.size();
        }
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView img_like;
        public MyHolder(View itemView) {
            super(itemView);
            img_like = (ImageView) itemView.findViewById(R.id.img_like);
        }



        public void setData(LikeModuleProduct likeModuleProduct) {
            Glide.with(mContext)
                    .load(likeModuleProduct.getProduct_url())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_like);
        }
    }


}
