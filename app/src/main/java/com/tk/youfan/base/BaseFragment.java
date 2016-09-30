package com.tk.youfan.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：tpkeeper on 2016/9/27 23:51
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public abstract class BaseFragment extends Fragment {
    private static final String STATE_IS_HIDDEN = "state_is_hidden";
    public Context mContext;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        //被系统杀死，在重生时的处理，防止重影
        if (savedInstanceState != null) {
            boolean isHidden = savedInstanceState.getBoolean(STATE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initView();
        return view;
    }



    /**
     * 初始化布局
     * @return
     */
    public abstract View initView();


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(savedInstanceState);
    }

    /**
     * 初始化数据,Bundle savedInstanceState用于恢复数据
     */
    public void initData(Bundle savedInstanceState){

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存隐藏状态
        outState.putBoolean(STATE_IS_HIDDEN, isHidden());
    }

    /**
     * 用于刷新呢数据
     */
    public void getDataFromNet(){

    }
}
