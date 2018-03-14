package com.tk.youfan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.tk.youfan.R;
import com.tk.youfan.activity.SearchActivity;
import com.tk.youfan.activity.MainActivity;
import com.tk.youfan.adapter.home.HomeRecyclerViewAdapter;
import com.tk.youfan.base.BaseFragment;
import com.tk.youfan.domain.EventMessage;
import com.tk.youfan.domain.home.HomeData;
import com.tk.youfan.domain.home.Module;
import com.tk.youfan.utils.Constants;
import com.tk.youfan.utils.LogUtil;
import com.tk.youfan.utils.SPUtils;
import com.tk.youfan.utils.UrlContants;
import com.tk.youfan.utils.loadingandretry.LoadingAndRetryManager;
import com.tk.youfan.utils.loadingandretry.OnLoadingAndRetryListener;
import com.tk.youfan.view.DividerItemDecoration;
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
 * 作者：tpkeeper on 2016/9/28 00:07
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：主页
 */
public class HomeFragment extends BaseFragment {
    //    public static final String URL = "url";
    @Bind(R.id.top_cebian_main)
    ImageButton topCebianMain;
    @Bind(R.id.top_search_main)
    ImageButton topSearchMain;
    @Bind(R.id.recyclerview_home)
    RecyclerView recyclerviewHome;
    @Bind(R.id.refreshlayout)
    public MaterialRefreshLayout refreshlayout;
    @Bind(R.id.tv_men_women)
    TextView tv_men_women;
    @Bind(R.id.rla_title)
    RelativeLayout rla_title;
    private List<Module> moduleList;
    private String url;
    private HomeRecyclerViewAdapter homeRecyclerViewAdapter;

    private static final int REFRESH = 1;
    private static final int NORMAL = 0;
    private int state = NORMAL;
    private SPUtils spUtils;
    private int urlType;
    private String title;
    public LoadingAndRetryManager mloadingAndRetryManager;


    @Override
    public View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_fragment,null);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initListener();
        initRefresh();
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        spUtils = new SPUtils(mContext, Constants.SP_NAME);
        super.initData(savedInstanceState);
        //loadingandretrymanager
        mloadingAndRetryManager = new LoadingAndRetryManager(refreshlayout, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {

                HomeFragment.this.setRetryEvent(retryView);
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
        tv_men_women.setOnClickListener(new MyOnClickListener());
//        topCebianMain.setOnClickListener(new MyCeBianClickListener());
        topSearchMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,SearchActivity.class);
                mContext.startActivity(intent);
            }
        });
    }


    /**
     * 从网络获取数据,也用于刷新数据，初始化，刷新都会走此方法
     */
    public void getDataFromNet() {
        if (spUtils.getInt(Constants.GENDER, Constants.URL_TYPE_NO) != Constants.URL_TYPE_NO) {
            //恢复页面url
            urlType = spUtils.getInt(Constants.GENDER, Constants.URL_TYPE_NO);
            //恢复title
            title = "男生";
            switch (urlType) {
                case Constants.URL_TYPE_MAN:
                    title = "男生";
                    url = UrlContants.HOME_MEN;
                    break;
                case Constants.URL_TYPE_WOMAN:
                    title = "女生";
                    url = UrlContants.HOME_WOMEN;
                    break;
                case Constants.URL_TYPE_LIFE:
                    title = "生活";
                    url = UrlContants.HOME_LIFE;
                    break;
            }

        } else {
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

    @OnClick({R.id.top_cebian_main, R.id.top_search_main, R.id.tv_men_women})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_cebian_main:
                ((MainActivity) getActivity()).slidingMenu.toggle();
                break;
            case R.id.top_search_main:
                break;
            case R.id.tv_men_women:
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
        HomeData homeData = JSON.parseObject(data, HomeData.class);
        moduleList = homeData.getModule();
        mloadingAndRetryManager.showContent();
        EventBus.getDefault().post(new EventMessage(EventMessage.MESSAGE_DATA_GETED_HomeData));
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
            case EventMessage.MESSAGE_DATA_GETED_HomeData:
                //判断是刷新还是第一次进入
                switch (state) {
                    case NORMAL:
                        initRecycleView();
                        break;
                    case REFRESH:
                        homeRecyclerViewAdapter.setData(moduleList);
                        refreshlayout.finishRefresh();
                        //还原状态
                        state = NORMAL;
                        break;
                }
                tv_men_women.setText(title);
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
        homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(moduleList, mContext);
        recyclerviewHome.setAdapter(homeRecyclerViewAdapter);
        recyclerviewHome.setLayoutManager(linearLayoutManager);
        recyclerviewHome.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
    }

    /**
     * popwindow
     */
    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            tv_men_women.setSelected(!tv_men_women.isSelected());
            final MyPopupWindow myPopupWindow = new MyPopupWindow(mContext);
            myPopupWindow.setOnItemClickListener(new MyPopupWindow.OnItemClickListener() {

                @Override
                public void onClick(View view, int index) {
                    switch (index) {
                        case 0://男生
                            url = UrlContants.HOME_MEN;
                            break;
                        case 1://女生
                            url = UrlContants.HOME_WOMEN;
                            break;
                        case 2://生活
                            url = UrlContants.HOME_LIFE;
                            break;
                    }
                    //存储url
                    spUtils.putInt(Constants.GENDER, index);
                    //自动刷新
                    refreshlayout.autoRefresh();
                    myPopupWindow.dismiss();
                }
            });
            myPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
//                    消失的时候要改变一下状态
                    tv_men_women.setSelected(false);
                }
            });

            myPopupWindow.showAsDropDown(rla_title);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}
