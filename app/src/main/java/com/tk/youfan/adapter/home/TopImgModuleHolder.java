package com.tk.youfan.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tk.youfan.R;
import com.tk.youfan.activity.JumpActivity;
import com.tk.youfan.base.BaseHolder;
import com.tk.youfan.domain.home.Data;
import com.tk.youfan.domain.home.Module;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 作者：tpkeeper on 2016/9/28 11:34
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：TopImgModule 即顶栏轮播图的holder
 */
public class TopImgModuleHolder extends BaseHolder {

    private final BGABanner banner;


    public TopImgModuleHolder(Context mContext, View itemView) {
        super(mContext,itemView);
        banner = (BGABanner) itemView.findViewById(R.id.banner_top_home);
    }

    @Override
    public void setData(Module module) {
        final List<Data> dataList = module.getData();
        List<View> views = new ArrayList<>();
        ImageView imageView;

        for (int i = 0; i < dataList.size(); i++) {
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            final Data data = dataList.get(i);
            Glide.with(mContext)
                    .load(data.getImg())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);

            views.add(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, JumpActivity.class);
                    intent.putExtra("url",data.getJump().getUrl());
                    intent.putExtra("title_later",data.getTitle());
                    intent.putExtra("title",data.getJump().getName());
                    mContext.startActivity(intent);
                }
            });
        }
        banner.setData(views);
    }

}
