package com.tk.youfan.adapter.inspiration;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tk.youfan.R;
import com.tk.youfan.adapter.search.BrandViewHolder;
import com.tk.youfan.base.BaseHolder;
import com.tk.youfan.domain.inspiration.ItemChild;
import com.tk.youfan.domain.search.Brand;

import java.util.List;

/**
 * 作者：tpkeeper on 2016/10/3 13:54
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class InspirationChildViewAdapter extends RecyclerView.Adapter<BaseHolder> {
    List<ItemChild> itemChildList;
    Context mContext;

    public InspirationChildViewAdapter(List<ItemChild> itemChildList, Context mContext) {
        this.itemChildList = itemChildList;
        this.mContext = mContext;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_inspiration_child, parent, false);
        return new ItemChildHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        ((ItemChildHolder)holder).setInspirationChildData(itemChildList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemChildList == null ? 0 : itemChildList.size();
    }

    public void setData(List<ItemChild> itemChildList) {
        this.itemChildList = itemChildList;
        notifyDataSetChanged();
    }
}
