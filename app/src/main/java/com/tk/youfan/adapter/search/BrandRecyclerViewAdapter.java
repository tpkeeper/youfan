package com.tk.youfan.adapter.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tk.youfan.R;
import com.tk.youfan.base.BaseHolder;
import com.tk.youfan.domain.search.Brand;

import java.util.List;
import java.util.zip.Inflater;

/**
 * 作者：tpkeeper on 2016/10/2 10:31
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class BrandRecyclerViewAdapter extends RecyclerView.Adapter<BaseHolder> {
    List<Brand> brandList;
    Context mContext;

    public BrandRecyclerViewAdapter(List<Brand> brandList, Context mContext) {
        this.brandList = brandList;
        this.mContext = mContext;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_brand, parent, false);
        return new BrandViewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        ((BrandViewHolder) holder).setBrandData(brandList.get(position));
    }

    @Override
    public int getItemCount() {
        return brandList == null ? 0 : brandList.size();
    }

    public void setData(List<Brand> brandList) {
        this.brandList = brandList;
        notifyDataSetChanged();
    }
}
