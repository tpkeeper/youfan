package com.tk.youfan.adapter.home;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
 * 作者：tpkeeper on 2016/9/28 21:04
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class ImgModuleHolder extends BaseHolder {
    TextView c_title;
    TextView e_title;
    TextView bottom_title;
    private final BGABanner banner;
    private List<Data> dataList;

    public ImgModuleHolder(Context mContext, View itemView) {
        super(mContext, itemView);
        c_title = (TextView) itemView.findViewById(R.id.c_title);
        e_title = (TextView) itemView.findViewById(R.id.e_title);
        bottom_title = (TextView) itemView.findViewById(R.id.bottom_title);
        banner = (BGABanner) itemView.findViewById(R.id.banner_top_home);
    }
    @Override
    public void setData(Module module) {
        dataList = module.getData();
        List<View> views = new ArrayList<>();
        ImageView imageView;
        bottom_title.setText(dataList.get(0).getTitle());
        c_title.setText(module.getC_title());
        e_title.setText(module.getE_title());
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
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottom_title.setText(dataList.get(position).getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
