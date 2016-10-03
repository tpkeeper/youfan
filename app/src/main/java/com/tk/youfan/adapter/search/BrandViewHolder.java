package com.tk.youfan.adapter.search;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tk.youfan.R;
import com.tk.youfan.base.BaseHolder;
import com.tk.youfan.domain.home.Module;
import com.tk.youfan.domain.search.Brand;

/**
 * 作者：tpkeeper on 2016/10/2 22:59
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class BrandViewHolder extends BaseHolder {
    ImageView img_brand;
    public BrandViewHolder(Context mContext, View itemView) {
        super(mContext, itemView);
        img_brand = (ImageView) itemView.findViewById(R.id.img_brand);
    }

    @Override
    public void setData(Module module) {
    }

    public void setBrandData(Brand brandData){
        Glide.with(mContext)
                .load(brandData.getLogo_img())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_brand);
    }

}
