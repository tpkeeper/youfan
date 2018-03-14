package com.tk.youfan.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tk.youfan.R;
import com.tk.youfan.activity.JumpActivity;
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
        final List<Data> dataList = module.getData();
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i=0;i<dataList.size();i++){
            if(dataList.get(i)==null) {
                LogUtil.e("data is null in  noticemodule ！！");
                continue;
            }
            arrayList.add(dataList.get(i).getTitle());
        }
        text_switcher.setArrayList(arrayList);

        text_switcher.setOnTextClickListener(new TextSwitcherView.OnTextClickListener() {
            @Override
            public void onTextClick(TextView textView) {
                for (int i = 0;i<dataList.size();i++){
                    Data data = dataList.get(i);
                    String title = data.getTitle();
                    if(!TextUtils.isEmpty(title)&&title.equals(textView.getText())) {
                        Intent intent = new Intent(mContext, JumpActivity.class);
                        intent.putExtra("url", data.getJump().getUrl());
                        intent.putExtra("title_later",data.getTitle());
                        intent.putExtra("title",data.getJump().getName());
                        mContext.startActivity(intent);
                    }
                }
            }
        });
    }
}
