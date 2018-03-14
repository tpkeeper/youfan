package com.tk.youfan.fragment.searchchild;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tk.youfan.R;
import com.tk.youfan.base.BaseFragment;
import com.tk.youfan.utils.loadingandretry.LoadingAndRetryManager;
import com.tk.youfan.utils.loadingandretry.OnLoadingAndRetryListener;

/**
 * 作者：tpkeeper on 2016/10/1 11:13
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：动态
 */
public class TrendFragment extends BaseFragment {

    private LoadingAndRetryManager mloadingAndRetryManager;
    private TextView textView;

    @Override
    public View initView() {
        LinearLayout linearLayout = new LinearLayout(mContext);
        textView = new TextView(mContext);
        textView.setText("动态");
        linearLayout.addView(textView);
        return linearLayout;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mloadingAndRetryManager = new LoadingAndRetryManager(textView, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {

                TrendFragment.this.setRetryEvent(retryView);
            }
        });
        mloadingAndRetryManager.showRetry();
    }
    private void setRetryEvent(View retryView) {
        View view = retryView.findViewById(R.id.id_btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromNet();
            }
        });
    }

}
