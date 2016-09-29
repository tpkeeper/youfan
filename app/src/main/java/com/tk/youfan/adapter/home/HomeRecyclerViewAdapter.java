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
import com.tk.youfan.utils.TypeConstants;

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
        BaseHolder holder = new DefaultHolderHome(mContext, view);
        switch (viewType) {
            case TypeConstants.NO://没有此类型，加载默认
                view = LayoutInflater.from(mContext).inflate(R.layout.default_home_module, parent, false);
                holder = new DefaultHolderHome(mContext, view);
                break;
            case TypeConstants.top_Img_Module:
                view = LayoutInflater.from(mContext).inflate(R.layout.top_img_module, parent, false);
                holder = new TopImgModuleHolder(mContext, view);
                break;
            case TypeConstants.ICON_MODULE:
                view = LayoutInflater.from(mContext).inflate(R.layout.icon_module, parent, false);
                holder = new IconModuleHolder(mContext, view);
                break;
            case TypeConstants.BANNER_MODULE:
                view = LayoutInflater.from(mContext).inflate(R.layout.banner_module, parent, false);
                holder = new BannerModuleHolder(mContext, view);
                break;
            case TypeConstants.NEW_MODULE:
                view = LayoutInflater.from(mContext).inflate(R.layout.new_module, parent, false);
                holder = new NewModuleHolder(mContext, view);
                break;
            case TypeConstants.HOT_CATE_MODULE:
                view = LayoutInflater.from(mContext).inflate(R.layout.hotcate_module, parent, false);
                holder = new HotCateModuleHolder(mContext, view);
                break;
            case TypeConstants.HOT_BRAND_MODULE:
                view = LayoutInflater.from(mContext).inflate(R.layout.hot_brand_module, parent, false);
                holder = new HotBrandModule(mContext, view);
                break;
            case TypeConstants.COLLO_SPECIAL_MODULE:
                view = LayoutInflater.from(mContext).inflate(R.layout.collo_special_module, parent, false);
                holder = new ColloSpecialModule(mContext, view);
                break;

            case TypeConstants.IMG_MODULE:
                view = LayoutInflater.from(mContext).inflate(R.layout.img_module, parent, false);
                holder = new ImgModuleHolder(mContext, view);
                break;
            case TypeConstants.IMG_LIST_V1_MODULE:
                view = LayoutInflater.from(mContext).inflate(R.layout.img_list_v1_module, parent, false);
                holder = new ImgListV1ModuleHolder(mContext, view);
                break;
            case TypeConstants.IMG_LIST_V3_MODULE:
                view = LayoutInflater.from(mContext).inflate(R.layout.img_list_v3_module, parent, false);
                holder = new ImgListV3ModuleHolder(mContext, view);
                break;
            case TypeConstants.IMG_LIST_V4_MODULE:
                view = LayoutInflater.from(mContext).inflate(R.layout.img_list_v4_module, parent, false);
                holder = new ImgListV4ModuleHolder(mContext, view);
                break;
            case TypeConstants.LIKE_MODULE:
                view = LayoutInflater.from(mContext).inflate(R.layout.like_module,parent,false);
                holder = new LikeModuleHolder(mContext,view);
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
        String module_key = moduleList.get(position).getModule_key();
        int key = TypeConstants.NO;
        switch (module_key) {
            case "topImgModule":
                key = TypeConstants.top_Img_Module;
                break;
            case "iconModule":
                key = TypeConstants.ICON_MODULE;
                break;
            case "bannerModule":
                key = TypeConstants.BANNER_MODULE;
                break;
            case "newModule":
                key = TypeConstants.NEW_MODULE;
                break;
            case "hotCateModule":
                key = TypeConstants.HOT_CATE_MODULE;
                break;
            case "hotBrandModule":
                key = TypeConstants.HOT_BRAND_MODULE;
                break;
            case "colloSpecialModule":
                key = TypeConstants.COLLO_SPECIAL_MODULE;
                break;
            case "imgModule":
                key = TypeConstants.IMG_MODULE;
                break;
            case "imgListV1Module":
                key = TypeConstants.IMG_LIST_V1_MODULE;
                break;
            case "imgListV3Module":
                key = TypeConstants.IMG_LIST_V3_MODULE;
                break;
            case "imgListV4Module":
                key = TypeConstants.IMG_LIST_V4_MODULE;
                break;
            case "likeModule":
                key = TypeConstants.LIKE_MODULE;
                break;
        }
        return key;
    }

    public void setData(List<Module> moduleList) {
        this.moduleList = moduleList;
        notifyDataSetChanged();
    }
}

