package com.tk.youfan.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tk.youfan.R;
import com.tk.youfan.activity.JumpActivity;
import com.tk.youfan.base.BaseHolder;
import com.tk.youfan.domain.home.Data;
import com.tk.youfan.domain.home.Module;

import java.util.List;

/**
 * 作者：tpkeeper on 2016/9/29 06:02
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class ImgListV3ModuleHolder extends BaseHolder {

    private final LinearLayout linearLayout;

    public ImgListV3ModuleHolder(Context mContext, View itemView) {
        super(mContext, itemView);
        linearLayout = (LinearLayout)itemView;
    }

    @Override
    public void setData(Module module) {
        List<Data> dataList = module.getData();
        linearLayout.removeAllViews();
        for(int i = 0;i<dataList.size();i++){
            ImageView img_little;

            View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_img_v3_module, null);
            img_little = (ImageView) view.findViewById(R.id.img_little);
            img_little.setScaleType(ImageView.ScaleType.CENTER_CROP);
            final Data data = dataList.get(i);
            Glide.with(mContext)
                    .load(data.getImg())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_little);
            if("1".equals(data.getSort())) {
                img_little.setOnClickListener(new View.OnClickListener() {
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
            linearLayout.addView(view);
        }
    }
}
