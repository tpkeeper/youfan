package com.tk.youfan.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tk.youfan.R;
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
    Context mContext;
    public HomeRecyclerViewAdapter(List<Module> moduleList,Context mContext) {
        this.moduleList = moduleList;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 1:
        view = LayoutInflater.from(mContext).inflate(R.layout.top_img_module,parent,false);
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

                break;
            case 7:

                break;
            case 8:

                break;
            case 9:

                break;
            case 10:

                break;
            case 11:

                break;
            case 12:

                break;
            case 13:

                break;
            case 14:

                break;
            case 15:

                break;
            case 16:

                break;
            case 17:

                break;
            case 18:

                break;
            case 19:

                break;
        }
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
        return moduleList.get(position).getId();
    }
}

