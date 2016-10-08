package com.tk.youfan.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tk.youfan.R;
import com.tk.youfan.base.BaseHolder;
import com.tk.youfan.domain.home.Data;
import com.tk.youfan.domain.home.Module;

import java.util.List;

/**
 * 作者：tpkeeper on 2016/9/29 06:22
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class ImgListV4ModuleHolder extends BaseHolder {
    ImageView img_big;
    private List<Data> dataList;
    GridView gridview;

    public ImgListV4ModuleHolder(Context mContext, View itemView) {
        super(mContext, itemView);
        img_big = (ImageView) itemView.findViewById(R.id.img_big);
        gridview = (GridView) itemView.findViewById(R.id.gridview);
    }

    @Override
    public void setData(Module module) {
        dataList = module.getData();
        img_big.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(mContext)
                .load(dataList.get(0).getImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.default100)
                .error(R.drawable.default100)
                .into(img_big);
        gridview.setAdapter(new MyBaseAdapter());
    }

    private class MyBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return (dataList.size() <= 1) ? 0 : dataList.size() - 1;
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position+1);
        }

        @Override
        public long getItemId(int position) {
            return position+1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_img_list_v4_module, parent, false);
            ImageView img_grid = (ImageView) view.findViewById(R.id.img_grid);
            Glide.with(mContext)
                    .load(dataList.get(position+1).getImg())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_grid);
            return view;
        }
    }
}
