package com.tk.youfan.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tk.youfan.domain.home.Data;

import java.util.List;

/**
 * 作者：tpkeeper on 2016/9/28 15:17
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public abstract class BaseHolder extends RecyclerView.ViewHolder {
    public BaseHolder(View itemView) {
        super(itemView);
    }
    public abstract void setData(List<Data> dataList);
}
