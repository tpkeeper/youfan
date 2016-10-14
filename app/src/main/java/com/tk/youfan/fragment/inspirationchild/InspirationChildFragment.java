package com.tk.youfan.fragment.inspirationchild;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.tk.youfan.R;
import com.tk.youfan.activity.MainActivity;
import com.tk.youfan.adapter.inspiration.InspirationChildViewAdapter;
import com.tk.youfan.adapter.search.BrandRecyclerViewAdapter;
import com.tk.youfan.base.BaseFragment;
import com.tk.youfan.common.request.BaseCallBack;
import com.tk.youfan.common.request.TkHttpUtils;
import com.tk.youfan.domain.EventMessage;
import com.tk.youfan.domain.inspiration.ItemChild;
import com.tk.youfan.domain.search.Brand;
import com.tk.youfan.utils.Constants;
import com.tk.youfan.utils.LogUtil;
import com.tk.youfan.utils.SPUtils;
import com.tk.youfan.utils.UrlContants;
import com.tk.youfan.utils.loadingandretry.LoadingAndRetryManager;
import com.tk.youfan.utils.loadingandretry.OnLoadingAndRetryListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：tpkeeper on 2016/10/3 11:12
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class InspirationChildFragment extends BaseFragment {
    MaterialRefreshLayout refreshlayout;
    RecyclerView recyclerView;
    SPUtils spUtils;
    private String url;
    private List<ItemChild> itemChildList;
    private static final int REFRESH = 1;
    private static final int NORMAL = 0;
    private int state = NORMAL;
    private InspirationChildViewAdapter inspirationChildViewAdapter;
    private LoadingAndRetryManager mloadingAndRetryManager;

    public InspirationChildFragment() {
    }

    public InspirationChildFragment(String url) {
        this.url = url;
    }

    //fragment 从内存恢复的时候只走无参构造器，因此需要保存数据
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            url = savedInstanceState.getString("itemChildUrl");
        }
    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.inspiration_child_fragment, null);
        refreshlayout = (MaterialRefreshLayout) view.findViewById(R.id.refreshlayout_inspiration_child);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_inspiration_child);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        spUtils = new SPUtils(mContext, Constants.SP_NAME);
        //loadingandretrymanager
        mloadingAndRetryManager = new LoadingAndRetryManager(refreshlayout, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                InspirationChildFragment.this.setRetryEvent(retryView);
            }
        });
        mloadingAndRetryManager.showLoading();
        getDataFromNet();
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

    /**
     * 从网络获取数据,也用于刷新数据，初始化，刷新都会走此方法
     */
    public void getDataFromNet() {
        if (TextUtils.isEmpty(url)) {
            LogUtil.e("url in inspiration child fragment is null");
            return;
        }

        TkHttpUtils.getInstance().get(url, new BaseCallBack<String>() {


            @Override
            public void onSuccess(Response response, String s) {
                processData(s);
            }

            @Override
            public void onFailure(Call call, Exception e) {
                LogUtil.e("okhttputils load data err ！");
                mloadingAndRetryManager.showRetry();
            }
        });

//        OkHttpUtils.get()
//                .url(url)
//                .id(100)
//                .build()
//                .execute(new MyStringCallBack());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.top_cebian_main, R.id.top_search_main})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_cebian_main:
                ((MainActivity) getActivity()).slidingMenu.toggle();
                break;
            case R.id.top_search_main:
                break;
        }
    }

//    private class MyStringCallBack extends StringCallback {
//        @Override
//        public void onError(Call call, Exception e, int id) {
//            LogUtil.e("okhttputils load data err ！");
//            mloadingAndRetryManager.showRetry();
//        }
//
//        @Override
//        public void onResponse(String response, int id) {
//            processData(response);
//        }
//    }

    private void processData(String response) {
        if (TextUtils.isEmpty(response)) {
            LogUtil.e("home response is empty !!");
            mloadingAndRetryManager.showEmpty();
            return;
        }
        JSONObject jsonObject = JSON.parseObject(response);
        String data = jsonObject.getString("data");
        JSONObject dataObject = JSON.parseObject(data);
        String list = dataObject.getString("list");
        itemChildList = JSON.parseArray(list, ItemChild.class);
        mloadingAndRetryManager.showContent();
        afterDataGeted();

    }

    public void afterDataGeted() {

        switch (state) {
            case NORMAL:
                //初始化recycleView
                initRecycleView();
                //初始化materialRefresh
                initRefresh();
                break;
            case REFRESH:
                inspirationChildViewAdapter.setData(itemChildList);
                refreshlayout.finishRefresh();
                //还原状态
                state = NORMAL;
                break;
        }

    }

    private void initRefresh() {
        refreshlayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                //设置状态
                state = REFRESH;
                getDataFromNet();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            }
        });
    }

    private void initRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        inspirationChildViewAdapter = new InspirationChildViewAdapter(itemChildList, mContext);
        recyclerView.setAdapter(inspirationChildViewAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("itemChildUrl", url);
    }
}
