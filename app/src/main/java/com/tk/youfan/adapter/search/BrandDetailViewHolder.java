package com.tk.youfan.adapter.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.FileDescriptorBitmapDataLoadProvider;
import com.tk.youfan.R;
import com.tk.youfan.activity.GoodsDetailActivity;
import com.tk.youfan.base.BaseHolder;
import com.tk.youfan.domain.home.Module;
import com.tk.youfan.domain.search.branddetail.BrandDetailResult;
import com.tk.youfan.domain.search.branddetail.ClsInfo;

/**
 * 作者：tpkeeper on 2016/10/5 21:58
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class BrandDetailViewHolder extends BaseHolder {
    ImageView img_goods;
    TextView tv_brand;
    TextView tv_name;
    TextView tv_sale_price;
    TextView tv_price;
    public BrandDetailViewHolder(Context mContext, View itemView) {
        super(mContext, itemView);
        img_goods = (ImageView) itemView.findViewById(R.id.img_goods);
        tv_brand = (TextView) itemView.findViewById(R.id.tv_brand);
        tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        tv_sale_price = (TextView) itemView.findViewById(R.id.tv_sale_price);
        tv_price = (TextView) itemView.findViewById(R.id.tv_price);
    }

    @Override
    public void setData(Module module) {
    }
    public void setBrandDetailData(final BrandDetailResult brandDetailResult){
        ClsInfo clsInfo = brandDetailResult.getClsInfo();
        Glide.with(mContext)
                .load(clsInfo.getMainImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.default100)
                .error(R.drawable.default100)
                .into(img_goods);
        img_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                intent.putExtra("code",brandDetailResult.getClsInfo().getCode());
                mContext.startActivity(intent);
            }
        });
        tv_brand.setText(clsInfo.getBrand());
        tv_name.setText(clsInfo.getName());
        tv_sale_price.setText("￥"+clsInfo.getSale_price());
        tv_price.setText("￥"+clsInfo.getPrice());
        tv_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
    }
}
