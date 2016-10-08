package com.tk.youfan.adapter.inspiration;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tk.youfan.R;
import com.tk.youfan.activity.InspiraJumpActivity;
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
    RelativeLayout relt_item_inspiration;
    public ItemChildHolder(Context mContext, View itemView) {
        super(mContext, itemView);
        img_item_inspiration_child = (ImageView) itemView.findViewById(R.id.img_item_inspiration_child);
        tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        relt_item_inspiration = (RelativeLayout) itemView.findViewById(R.id.relt_item_inspiration);
    }

    @Override
    public void setData(Module module) {

    }

    public void setInspirationChildData(final ItemChild itemChild){
        Glide.with(mContext)
                .load(itemChild.getImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.default100)
                .error(R.drawable.default100)
                .into(img_item_inspiration_child);
        tv_title.setText(itemChild.getTitle());
        relt_item_inspiration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, InspiraJumpActivity.class);
                intent.putExtra("url",itemChild.getUrl());
                intent.putExtra("title_later",itemChild.getTitle());
                intent.putExtra("title","正在加载");
                mContext.startActivity(intent);
            }
        });
    }

}
