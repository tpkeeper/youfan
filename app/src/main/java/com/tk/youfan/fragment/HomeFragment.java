package com.tk.youfan.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.tk.youfan.R;
import com.tk.youfan.adapter.home.HomeRecyclerViewAdapter;
import com.tk.youfan.base.BaseFragment;
import com.tk.youfan.domain.EventMessage;
import com.tk.youfan.domain.home.HomeData;
import com.tk.youfan.domain.home.Module;
import com.tk.youfan.utils.LogUtil;
import com.tk.youfan.utils.UrlContants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：tpkeeper on 2016/9/28 00:07
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：主页
 */
public class HomeFragment extends BaseFragment {
    @Bind(R.id.top_cebian_main)
    ImageButton topCebianMain;
    @Bind(R.id.top_search_main)
    ImageButton topSearchMain;
    @Bind(R.id.recyclerview_home)
    RecyclerView recyclerviewHome;
    @Bind(R.id.refreshlayout)
    MaterialRefreshLayout refreshlayout;
    private List<Module> moduleList;
    private String url;
    private HomeRecyclerViewAdapter homeRecyclerViewAdapter;

    private static final int REFRESH = 1;
    private static final int NORMAL = 0;
    private int state = NORMAL;

    @Override
    public View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_fragment, null);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        url = UrlContants.HOME_MEN;
        getDataFromNet();
        initRefresh();
    }

    /**
     * 从网络获取数据
     */
    private void getDataFromNet() {
        OkHttpUtils.get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallBack());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.top_cebian_main, R.id.top_search_main})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_cebian_main:
                break;
            case R.id.top_search_main:
                break;
        }
    }

    private class MyStringCallBack extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            LogUtil.e("okhttputils load data err ！");
        }

        @Override
        public void onResponse(String response, int id) {
            processData(response);
        }
    }

    private void processData(String response) {
        if (TextUtils.isEmpty(response)) {
            LogUtil.e("home response is empty !!");
            return;
        }
        JSONObject jsonObject = JSON.parseObject(response);
        String data = jsonObject.getString("data");
        HomeData homeData = JSON.parseObject(data, HomeData.class);
        moduleList = homeData.getModule();
        EventBus.getDefault().post(new EventMessage(EventMessage.MESSAGE_DATA_GETED_HomeData));
    }

    @Subscribe
    public void onEventMessage(EventMessage eventMessage) {
        switch (eventMessage.getMessage()) {
            case EventMessage.MESSAGE_DATA_GETED_HomeData:
                //判断是刷新还是第一次进入
                switch (state) {
                    case NORMAL:
                        initRecycleView();
                        state = REFRESH;
                        break;
                    case REFRESH:
                        homeRecyclerViewAdapter.setData(moduleList);
                        refreshlayout.finishRefresh();
                        break;
                }
                break;
        }
    }

    private void initRefresh() {
        refreshlayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getDataFromNet();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            }
        });
    }

    private void initRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(moduleList, mContext);
        recyclerviewHome.setAdapter(homeRecyclerViewAdapter);
        recyclerviewHome.setLayoutManager(linearLayoutManager);
    }

}
