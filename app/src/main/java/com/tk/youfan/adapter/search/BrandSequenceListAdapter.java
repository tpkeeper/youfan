package com.tk.youfan.adapter.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.tk.youfan.R;
import com.tk.youfan.base.BaseHolder;
import com.tk.youfan.domain.search.brandrequence.BrandInfo;

import java.util.List;

/**
 * 作者：tpkeeper on 2016/10/7 14:10
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class BrandSequenceListAdapter extends RecyclerView.Adapter<BaseHolder> implements StickyRecyclerHeadersAdapter<BaseHolder> {
    List<BrandInfo> brandInfoList;
    Context mContext;

    public BrandSequenceListAdapter(Context mContext, List<BrandInfo> brandInfoList) {
        this.mContext = mContext;
        this.brandInfoList = brandInfoList;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_brand_sequence,parent,false);
        return new BrandSequenceViewHolder(mContext,view);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        ((BrandSequenceViewHolder)holder).setBrandInfoData(brandInfoList.get(position));
    }

    @Override
    public long getHeaderId(int position) {
        return brandInfoList.get(position).getFirst_letter().charAt(0);
    }

    @Override
    public BaseHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_brand_sequence_letter,parent,false);
        return new BrandSequenceLetterViewHolder(mContext,view);
    }

    @Override
    public void onBindHeaderViewHolder(BaseHolder holder, int position) {
        ((BrandSequenceLetterViewHolder)holder).setBrandInfoData(brandInfoList.get(position));
    }

    @Override
    public int getItemCount() {
        return brandInfoList == null ?0:brandInfoList.size();
    }
}
