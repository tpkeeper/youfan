package com.tk.youfan.fragment.searchchild;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tk.youfan.R;
import com.tk.youfan.adapter.search.BrandDetailRecyclerViewAdapter;
import com.tk.youfan.base.BaseFragment;
import com.tk.youfan.domain.search.branddetail.BrandDetailResult;
import com.tk.youfan.utils.LogUtil;
import com.tk.youfan.utils.UrlContants;
import com.tk.youfan.utils.loadingandretry.LoadingAndRetryManager;
import com.tk.youfan.utils.loadingandretry.OnLoadingAndRetryListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * 作者：tpkeeper on 2016/10/5 20:28
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class BrandDetailFragment extends BaseFragment {
    private String url ;
    private List<BrandDetailResult> brandDetailResultList;
    private LoadingAndRetryManager mloadingAndRetryManager;

    public BrandDetailFragment(String url) {
        this.url = url;
    }

    RecyclerView recyclerview;
    @Override
    public View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.brand_detail_fragment,null);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        //loadingandretrymanager
        mloadingAndRetryManager = new LoadingAndRetryManager(recyclerview, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                BrandDetailFragment.this.setRetryEvent(retryView);
            }
        });

        if(!TextUtils.isEmpty(url)) {
            mloadingAndRetryManager.showLoading();
            getDataFromNet();
        }
    }
    private void setRetryEvent(View retryView) {
        View view = retryView.findViewById(R.id.id_btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mloadingAndRetryManager.showLoading();
                getDataFromNet();
            }
        });
    }

    @Override
    public void getDataFromNet() {
        super.getDataFromNet();
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new MyStringCallBack());
    }

    private class MyStringCallBack extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            LogUtil.e("okhttp get data err in brandDetailFragment !");
            mloadingAndRetryManager.showRetry();
        }

        @Override
        public void onResponse(String response, int id) {
            if(TextUtils.isEmpty(response)) {
                LogUtil.e("response is null in brandDetailFragment");
                mloadingAndRetryManager.showEmpty();
                return;
            }
            mloadingAndRetryManager.showContent();
            processData(response);
        }
    }

    private void processData(String response) {
        JSONObject jsonObject = JSON.parseObject(response);
        String results = jsonObject.getString("results");
        brandDetailResultList = JSON.parseArray(results, BrandDetailResult.class);
        if(brandDetailResultList!=null) {
            initRecycleView();
        }
    }

    private void initRecycleView() {
        BrandDetailRecyclerViewAdapter adapter = new BrandDetailRecyclerViewAdapter(brandDetailResultList,mContext);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,2);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(gridLayoutManager);
    }
}
