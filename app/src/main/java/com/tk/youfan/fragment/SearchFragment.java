package com.tk.youfan.fragment;

import android.view.View;
import android.widget.TextView;

import com.tk.youfan.base.BaseFragment;

/**
 * 作者：tpkeeper on 2016/9/28 00:07
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class SearchFragment extends BaseFragment {
    @Override
    public View initView() {
        TextView textView  = new TextView(mContext);
        textView.setText("search");
        return textView;
    }
}
