package com.tk.youfan.adapter.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tk.youfan.R;
import com.tk.youfan.base.BaseHolder;
import com.tk.youfan.domain.home.Data;
import com.tk.youfan.domain.home.Module;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 作者：tpkeeper on 2016/9/28 16:44
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class BannerModuleHolder extends BaseHolder {
    private final BGABanner banner;

    public BannerModuleHolder(Context mContext, View itemView) {
        super(mContext, itemView);
        banner = (BGABanner) itemView.findViewById(R.id.banner_top_home);
    }

    @Override
    public void setData(Module module) {
        List<Data> dataList = module.getData();
        List<View> views = new ArrayList<>();
        ImageView imageView;

        for (int i = 0; i < dataList.size(); i++) {
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            Glide.with(mContext)
                    .load(dataList.get(i).getImg())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);

            views.add(imageView);
        }
        banner.setData(views);
    }

}
