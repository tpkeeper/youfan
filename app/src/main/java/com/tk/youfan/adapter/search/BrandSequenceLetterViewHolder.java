package com.tk.youfan.adapter.search;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.tk.youfan.R;
import com.tk.youfan.base.BaseHolder;
import com.tk.youfan.domain.home.Module;
import com.tk.youfan.domain.search.brandrequence.BrandInfo;

/**
 * 作者：tpkeeper on 2016/10/7 14:29
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class BrandSequenceLetterViewHolder extends BaseHolder {
    TextView tv_name;
    public BrandSequenceLetterViewHolder(Context mContext, View itemView) {
        super(mContext, itemView);
        tv_name = (TextView) itemView.findViewById(R.id.tv_name);
    }

    @Override
    public void setData(Module module) {
    }
    public void setBrandInfoData(BrandInfo brandInfo){
        tv_name.setText(brandInfo.getFirst_letter());
    }
}
