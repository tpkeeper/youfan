package com.tk.youfan.adapter.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tk.youfan.R;
import com.tk.youfan.base.BaseHolder;
import com.tk.youfan.domain.search.branddetail.BrandDetailResult;

import java.util.List;

/**
 * 作者：tpkeeper on 2016/10/5 21:52
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class BrandDetailRecyclerViewAdapter extends RecyclerView.Adapter<BaseHolder> {
    Context mContext;
    private List<BrandDetailResult> brandDetailResultList;

    public BrandDetailRecyclerViewAdapter(List<BrandDetailResult> brandDetailResultList, Context mContext) {
        this.brandDetailResultList = brandDetailResultList;
        this.mContext = mContext;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_brand_detail, parent, false);
        return new BrandDetailViewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        ((BrandDetailViewHolder) holder).setBrandDetailData(brandDetailResultList.get(position));
    }

    @Override
    public int getItemCount() {
        return brandDetailResultList == null ? 0 : brandDetailResultList.size();
    }
}
