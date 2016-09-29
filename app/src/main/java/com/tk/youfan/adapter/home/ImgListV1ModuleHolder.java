package com.tk.youfan.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tk.youfan.R;
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
        Glide.with(mContext)
                .load(dataList.get(0).getImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_big);
        horizontal_scrollview.removeAllViews();

        for(int i = 1;i<dataList.size();i++){
            ImageView img_little;

            View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_img_v1_module, null);
            img_little = (ImageView) view.findViewById(R.id.img_little);

            Glide.with(mContext)
                    .load(dataList.get(i).getImg())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_little);
            horizontal_scrollview.addView(view);
        }
    }
}
