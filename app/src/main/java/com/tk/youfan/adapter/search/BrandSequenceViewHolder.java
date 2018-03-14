package com.tk.youfan.adapter.search;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.tk.youfan.R;
import com.tk.youfan.activity.BrandDetailActivity;
import com.tk.youfan.base.BaseHolder;
import com.tk.youfan.domain.home.Module;
import com.tk.youfan.domain.search.brandrequence.BrandInfo;

/**
 * 作者：tpkeeper on 2016/10/7 14:29
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class BrandSequenceViewHolder extends BaseHolder {
    TextView tv_name;
    public BrandSequenceViewHolder(Context mContext, View itemView) {
        super(mContext, itemView);
        tv_name = (TextView) itemView.findViewById(R.id.tv_name);
    }

    @Override
    public void setData(Module module) {
    }
    public void setBrandInfoData(final BrandInfo brandInfo){
        tv_name.setText(brandInfo.getEname()+brandInfo.getCname());
        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,BrandDetailActivity.class);
                String brand_code = brandInfo.getBrand_code();
                intent.putExtra("brand_code",brand_code);
                mContext.startActivity(intent);
            }
        });
    }
}
