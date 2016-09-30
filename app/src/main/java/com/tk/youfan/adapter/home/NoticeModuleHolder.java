package com.tk.youfan.adapter.home;

import android.content.Context;
import android.view.View;

import com.tk.youfan.R;
import com.tk.youfan.base.BaseHolder;
import com.tk.youfan.domain.home.Data;
import com.tk.youfan.domain.home.Module;
import com.tk.youfan.utils.LogUtil;
import com.tk.youfan.view.TextSwitcherView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：tpkeeper on 2016/9/30 21:10
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class NoticeModuleHolder extends BaseHolder {
    TextSwitcherView text_switcher;
    public NoticeModuleHolder(Context mContext, View itemView) {
        super(mContext, itemView);
        text_switcher = (TextSwitcherView) itemView.findViewById(R.id.text_switcher);
    }
    @Override
    public void setData(Module module) {
        if(module==null) {
            LogUtil.e("noticeModule is null!!");
            return;
        }
        List<Data> dataList = module.getData();
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i=0;i<dataList.size();i++){
            if(dataList.get(i)==null) {
                LogUtil.e("data is null in  noticemodule ！！");
                continue;
            }
            arrayList.add(dataList.get(i).getTitle());
        }

        List<Data> data = module.getData();
        text_switcher.setArrayList(arrayList);
    }
}
