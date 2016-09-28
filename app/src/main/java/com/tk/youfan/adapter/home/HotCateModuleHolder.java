package com.tk.youfan.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tk.youfan.R;
import com.tk.youfan.base.BaseHolder;
import com.tk.youfan.domain.home.Data;
import com.tk.youfan.domain.home.Module;
import com.tk.youfan.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：tpkeeper on 2016/9/28 18:14
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：热门分类
 */
public class HotCateModuleHolder extends BaseHolder {
    TextView c_title;
    TextView e_title;
    GridView gridview;
    private List<Data> dataList;

    public HotCateModuleHolder(Context mContext, View itemView) {
        super(mContext, itemView);
        c_title = (TextView) itemView.findViewById(R.id.c_title);
        e_title = (TextView) itemView.findViewById(R.id.e_title);
        gridview = (GridView) itemView.findViewById(R.id.gridview);
    }

    @Override
    public void setData(Module module) {
        dataList = module.getData();
        c_title.setText(module.getC_title());
        e_title.setText(module.getE_title());

        gridview.setAdapter(new MyBaseAdapter());
    }

    private class MyBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_hotcate_module, parent, false);
            ImageView img_grid = (ImageView) view.findViewById(R.id.img_grid);
            Glide.with(mContext)
                    .load(dataList.get(position).getImg())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_grid);
            return view;
        }
    }
}
