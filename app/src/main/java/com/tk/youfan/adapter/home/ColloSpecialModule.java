package com.tk.youfan.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
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
 * 作者：tpkeeper on 2016/9/28 20:41
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class ColloSpecialModule extends BaseHolder {
    LinearLayout horizontal_scrollview;
    TextView c_title;
    TextView e_title;
    public ColloSpecialModule(Context mContext, View itemView) {
        super(mContext, itemView);
        c_title = (TextView) itemView.findViewById(R.id.c_title);
        e_title = (TextView) itemView.findViewById(R.id.e_title);
        horizontal_scrollview = (LinearLayout) itemView.findViewById(R.id.horizontal_scrollview);
    }

    @Override
    public void setData(Module module) {
        c_title.setText(module.getC_title());
        e_title.setText(module.getE_title());

        horizontal_scrollview.removeAllViews();
        List<Data> dataList = module.getData();
        for(int i = 0;i<dataList.size();i++){
            ImageView img_little;
            TextView tv_title;
            TextView tv_price;
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_collo_special_module, null);
            img_little = (ImageView) view.findViewById(R.id.img_little);
            Glide.with(mContext)
                    .load(dataList.get(i).getImg())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_little);
            horizontal_scrollview.addView(view);
        }
    }
}
