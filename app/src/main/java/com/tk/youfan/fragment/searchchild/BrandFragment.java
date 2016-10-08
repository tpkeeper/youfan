package com.tk.youfan.fragment.searchchild;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.tk.youfan.R;
import com.tk.youfan.activity.MainActivity;
import com.tk.youfan.adapter.home.HomeRecyclerViewAdapter;
import com.tk.youfan.adapter.search.BrandRecyclerViewAdapter;
import com.tk.youfan.base.BaseFragment;
import com.tk.youfan.domain.EventMessage;
import com.tk.youfan.domain.home.HomeData;
import com.tk.youfan.domain.search.Brand;
import com.tk.youfan.utils.Constants;
import com.tk.youfan.utils.LogUtil;
import com.tk.youfan.utils.SPUtils;
import com.tk.youfan.utils.UrlContants;
import com.tk.youfan.utils.loadingandretry.LoadingAndRetryLayout;
import com.tk.youfan.utils.loadingandretry.LoadingAndRetryManager;
import com.tk.youfan.utils.loadingandretry.OnLoadingAndRetryListener;
import com.tk.youfan.view.DividerItemDecoration;
import com.tk.youfan.view.MyBrandPopupWindow;
import com.tk.youfan.view.MyPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 作者：tpkeeper on 2016/10/1 11:13
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：品牌
 */
public class BrandFragment extends BaseFragment {
    RecyclerView recyclerview_brand;
    private SPUtils spUtils;
    private int urlType;
    private String url;
    private List<Brand> brandList;
    private static final int REFRESH = 1;
    private static final int NORMAL = 0;
    private int state = NORMAL;
    MaterialRefreshLayout refreshlayout;
    private BrandRecyclerViewAdapter brandRecyclerViewAdapter;
    ImageView img_sequence;
    RelativeLayout rla_content;
    private LoadingAndRetryManager mloadingAndRetryManager;

    @Override
    public View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.brand_fragment,null);
        refreshlayout = (MaterialRefreshLayout) view.findViewById(R.id.refreshlayout);
        img_sequence = (ImageView) view.findViewById(R.id.img_sequence);
        EventBus.getDefault().register(this);
        recyclerview_brand = (RecyclerView) view.findViewById(R.id.recyclerview_brand);
        rla_content = (RelativeLayout) view.findViewById(R.id.rla_content);
        initListener();
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
                BrandFragment.this.setRetryEvent(retryView);
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

    private void initListener() {
        img_sequence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBrandPopupWindow myBrandPopupWindow = new MyBrandPopupWindow(mContext, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                img_sequence.setVisibility(View.GONE);
                //设置高度和位置
                myBrandPopupWindow.setHeight(rla_content.getBottom()-rla_content.getTop());
                myBrandPopupWindow.showAsDropDown(img_sequence,0,-img_sequence.getBottom());
                myBrandPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        img_sequence.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }

    /**
     * 从网络获取数据,也用于刷新数据，初始化，刷新都会走此方法
     */
    public void getDataFromNet() {
        if (spUtils.getInt(Constants.GENDER, Constants.URL_TYPE_NO) != Constants.URL_TYPE_NO){
            //恢复页面url
            urlType = spUtils.getInt(Constants.GENDER,Constants.URL_TYPE_NO);
            //恢复title
            url = UrlContants.KIND_PINPAI_MAN;
            switch (urlType) {
                case Constants.URL_TYPE_MAN:
                    url = UrlContants.KIND_PINPAI_MAN;
                    break;
                case Constants.URL_TYPE_WOMAN:
                    url = UrlContants.KIND_PINPAI_WOMAN;
                    break;
                case Constants.URL_TYPE_LIFE:
                    url = UrlContants.KIND_PINPAI_LIFE;
                    break;
            }
        }else{
            //默认加载男生
            url = UrlContants.HOME_MEN;
            spUtils.putInt(Constants.GENDER, Constants.URL_TYPE_MAN);
        }

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
        EventBus.getDefault().unregister(this);
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

    private class MyStringCallBack extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            LogUtil.e("okhttputils load data err ！");
            mloadingAndRetryManager.showRetry();
        }

        @Override
        public void onResponse(String response, int id) {
            processData(response);
        }
    }

    private void processData(String response) {
        if (TextUtils.isEmpty(response)) {
            LogUtil.e("home response is empty !!");
            mloadingAndRetryManager.showEmpty();
            return;
        }
        JSONObject jsonObject = JSON.parseObject(response);
        String data = jsonObject.getString("data");
        brandList = JSON.parseArray(data, Brand.class);
        mloadingAndRetryManager.showContent();
        EventBus.getDefault().post(new EventMessage(EventMessage.MESSAGE_DATA_GETED_SEARCH_BRAND_DATA));
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if(spUtils.getInt(Constants.GENDER,Constants.URL_TYPE_NO)!=urlType) {
//            refreshlayout.autoRefresh();
//        }
//    }

    @Subscribe
    public void onEventMessage(EventMessage eventMessage) {
        switch (eventMessage.getMessage()) {
            case EventMessage.MESSAGE_DATA_GETED_SEARCH_BRAND_DATA:
                //判断是刷新还是第一次进入
                switch (state) {
                    case NORMAL:
                        //初始化recycleView
                        initRecycleView();
                        //初始化materialRefresh

                        initRefresh();
                        break;
                    case REFRESH:
                        brandRecyclerViewAdapter.setData(brandList);
                        refreshlayout.finishRefresh();
                        //还原状态
                        state = NORMAL;
                        break;
                }
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,3);
        brandRecyclerViewAdapter = new BrandRecyclerViewAdapter(brandList,mContext);
        recyclerview_brand.setAdapter(brandRecyclerViewAdapter);
        recyclerview_brand.setLayoutManager(gridLayoutManager);
    }


}
