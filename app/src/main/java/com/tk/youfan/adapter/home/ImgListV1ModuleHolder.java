package com.tk.youfan.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tk.youfan.R;
import com.tk.youfan.activity.JumpActivity;
import com.tk.youfan.base.BaseHolder;
import com.tk.youfan.domain.home.Data;
import com.tk.youfan.domain.home.Module;

import java.util.List;

/**
 * 作者：tpkeeper on 2016/9/29 05:10
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class ImgListV1ModuleHolder extends BaseHolder {
    TextView c_title;
    TextView e_title;
    private List<Data> dataList;
    ImageView img_big;
    LinearLayout horizontal_scrollview;

    public ImgListV1ModuleHolder(Context mContext, View itemView) {
        super(mContext, itemView);
        c_title = (TextView) itemView.findViewById(R.id.c_title);
        e_title = (TextView) itemView.findViewById(R.id.e_title);
        img_big = (ImageView) itemView.findViewById(R.id.img_big);
        horizontal_scrollview = (LinearLayout) itemView.findViewById(R.id.horizontal_scrollview);
    }

    @Override
    public void setData(Module module) {
        dataList = module.getData();
        c_title.setText(module.getC_title());
        e_title.setText(module.getE_title());
        img_big.setScaleType(ImageView.ScaleType.CENTER_CROP);
        final Data data = dataList.get(0);
        Glide.with(mContext)
                .load(data.getImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.default100)
                .error(R.drawable.default100)
                .into(img_big);
        horizontal_scrollview.removeAllViews();
        img_big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, JumpActivity.class);
                intent.putExtra("url",data.getJump().getUrl());
                intent.putExtra("title_later",data.getTitle());
                intent.putExtra("title",data.getJump().getName());
                mContext.startActivity(intent);
            }
        });
        for(int i = 1;i<dataList.size();i++){
            ImageView img_little;

            View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_img_v1_module, null);
            img_little = (ImageView) view.findViewById(R.id.img_little);

            final Data data1 = dataList.get(i);
            Glide.with(mContext)
                    .load(data1.getImg())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.default100)
                    .error(R.drawable.default100)
                    .into(img_little);
            img_little.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, JumpActivity.class);
                    intent.putExtra("url",data1.getJump().getUrl());
                    intent.putExtra("title_later",data1.getTitle());
                    intent.putExtra("title",data1.getJump().getName());
                    mContext.startActivity(intent);
                }
            });
            horizontal_scrollview.addView(view);
        }
    }
}
