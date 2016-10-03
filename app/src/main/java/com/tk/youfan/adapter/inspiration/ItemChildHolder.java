package com.tk.youfan.adapter.inspiration;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tk.youfan.R;
import com.tk.youfan.base.BaseHolder;
import com.tk.youfan.domain.home.Module;
import com.tk.youfan.domain.inspiration.ItemChild;

/**
 * 作者：tpkeeper on 2016/10/3 14:00
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class ItemChildHolder extends BaseHolder {
    ImageView img_item_inspiration_child;
    TextView tv_title;
    public ItemChildHolder(Context mContext, View itemView) {
        super(mContext, itemView);
        img_item_inspiration_child = (ImageView) itemView.findViewById(R.id.img_item_inspiration_child);
        tv_title = (TextView) itemView.findViewById(R.id.tv_title);
    }

    @Override
    public void setData(Module module) {

    }

    public void setInspirationChildData(ItemChild itemChild){
        Glide.with(mContext)
                .load(itemChild.getImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_item_inspiration_child);
        tv_title.setText(itemChild.getTitle());
    }

}
