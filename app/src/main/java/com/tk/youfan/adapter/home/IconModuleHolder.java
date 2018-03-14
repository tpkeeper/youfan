package com.tk.youfan.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tk.youfan.R;
import com.tk.youfan.activity.JumpActivity;
import com.tk.youfan.base.BaseHolder;
import com.tk.youfan.domain.home.Data;
import com.tk.youfan.domain.home.Module;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 作者：tpkeeper on 2016/9/28 15:51
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class IconModuleHolder extends BaseHolder {

    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;
    ImageView img5;
    ImageView img6;
    ImageView img7;
    ImageView img8;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    TextView tv6;
    TextView tv7;
    TextView tv8;
    private final List<ImageView> imageViews;
    private final List<TextView> textViews;

    public IconModuleHolder(Context mContext, View itemView) {
        super(mContext, itemView);
        imageViews = new ArrayList<>();
        img1 = (ImageView) itemView.findViewById(R.id.img1);
        img2 = (ImageView) itemView.findViewById(R.id.img2);
        img3 = (ImageView) itemView.findViewById(R.id.img3);
        img4 = (ImageView) itemView.findViewById(R.id.img4);
        img5 = (ImageView) itemView.findViewById(R.id.img5);
        img6 = (ImageView) itemView.findViewById(R.id.img6);
        img7 = (ImageView) itemView.findViewById(R.id.img7);
        img8 = (ImageView) itemView.findViewById(R.id.img8);
        imageViews.add(img1);
        imageViews.add(img2);
        imageViews.add(img3);
        imageViews.add(img4);
        imageViews.add(img5);
        imageViews.add(img6);
        imageViews.add(img7);
        imageViews.add(img8);
        textViews = new ArrayList<>();
        tv1 = (TextView) itemView.findViewById(R.id.tv1);
        tv2 = (TextView) itemView.findViewById(R.id.tv2);
        tv3 = (TextView) itemView.findViewById(R.id.tv3);
        tv4 = (TextView) itemView.findViewById(R.id.tv4);
        tv5 = (TextView) itemView.findViewById(R.id.tv5);
        tv6 = (TextView) itemView.findViewById(R.id.tv6);
        tv7 = (TextView) itemView.findViewById(R.id.tv7);
        tv8 = (TextView) itemView.findViewById(R.id.tv8);
        textViews.add(tv1);
        textViews.add(tv2);
        textViews.add(tv3);
        textViews.add(tv4);
        textViews.add(tv5);
        textViews.add(tv6);
        textViews.add(tv7);
        textViews.add(tv8);
    }

    @Override
    public void setData(Module module) {
        List<Data> dataList = module.getData();
        for (int i = 0;i<dataList.size();i++){
            final Data data = dataList.get(i);
            Glide.with(mContext)
                    .load(data.getImg())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.default100)
                    .error(R.drawable.default100)
                    .into(imageViews.get(i));
            textViews.get(i).setText(data.getTitle());
            imageViews.get(i).setOnClickListener(new View.OnClickListener() {
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
    }
}
