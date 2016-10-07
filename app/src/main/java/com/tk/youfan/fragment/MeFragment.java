package com.tk.youfan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tk.youfan.R;
import com.tk.youfan.base.BaseFragment;
import com.tk.youfan.utils.zxing.CommonScanActivity;
import com.tk.youfan.utils.zxing.utils.Constant;

/**
 * 作者：tpkeeper on 2016/9/28 00:07
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class MeFragment extends BaseFragment {
    LinearLayout scan;
    @Override
    public View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.me_fragment,null);
        scan = (LinearLayout) view.findViewById(R.id.scan);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        initListener();
    }

    private void initListener() {
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,CommonScanActivity.class);
                intent.putExtra(Constant.REQUEST_SCAN_MODE,Constant.REQUEST_SCAN_MODE_ALL_MODE);
                startActivity(intent);
            }
        });
    }
}
