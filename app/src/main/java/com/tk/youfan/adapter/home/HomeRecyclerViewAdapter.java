package com.tk.youfan.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tk.youfan.R;
import com.tk.youfan.base.BaseHolder;
import com.tk.youfan.domain.home.Module;
import com.tk.youfan.utils.LogUtil;

import java.util.List;

/**
 * 作者：tpkeeper on 2016/9/28 11:24
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：home的recyclerview的adapter
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<BaseHolder> {
    List<Module> moduleList;
    Context mContext;

    public HomeRecyclerViewAdapter(List<Module> moduleList, Context mContext) {
        this.moduleList = moduleList;
        this.mContext = mContext;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.default_home_module, parent, false);
        BaseHolder holder = new DefaultHolderHome(mContext,view);
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(mContext).inflate(R.layout.top_img_module, parent, false);
                holder = new TopImgModuleHolder(mContext, view);
                break;
            case 2:
                view = LayoutInflater.from(mContext).inflate(R.layout.icon_module,parent,false);
                holder = new IconModuleHolder(mContext,view);
                break;
            case 3:
                view = LayoutInflater.from(mContext).inflate(R.layout.banner_module,parent,false);
                holder = new BannerModuleHolder(mContext,view);
                break;
            case 4:
                view = LayoutInflater.from(mContext).inflate(R.layout.new_module,parent,false);
                holder = new NewModuleHolder(mContext,view);
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
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        if (moduleList.get(position) == null) {
            LogUtil.e("moduleList.get(position)==null");
            return;
        }
        holder.setData(moduleList.get(position));
    }

    @Override
    public int getItemCount() {
        return moduleList == null ? 0 : moduleList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return moduleList.get(position).getId();
    }
}

