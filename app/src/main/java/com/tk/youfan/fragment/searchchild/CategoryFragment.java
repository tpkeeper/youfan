package com.tk.youfan.fragment.searchchild;

import android.view.View;
import android.widget.TextView;

import com.tk.youfan.base.BaseFragment;

/**
 * 作者：tpkeeper on 2016/10/1 11:13
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：品类
 */
public class CategoryFragment extends BaseFragment {
    @Override
    public View initView() {
        TextView textView = new TextView(mContext);
        textView.setText("品类");
        return textView;
    }
}
