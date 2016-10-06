package com.tk.youfan.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tk.youfan.R;
import com.tk.youfan.base.BaseFragment;

/**
 * 作者：tpkeeper on 2016/9/28 00:07
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class PurchaseFragment extends BaseFragment {
    @Override
    public View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.purchase_fragment,null);
        return view;
    }
}
