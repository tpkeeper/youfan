package com.tk.youfan.adapter.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.tk.youfan.domain.home.Module;

import java.util.List;

/**
 * 作者：tpkeeper on 2016/9/28 11:24
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：home的recyclerview的adapter
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Module> moduleList;

    public HomeRecyclerViewAdapter(List<Module> moduleList) {
        this.moduleList = moduleList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        return new TopImgModuleHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }
}

