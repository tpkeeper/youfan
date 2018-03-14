package com.tk.youfan.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.tk.youfan.R;
import com.tk.youfan.base.BaseFragment;
import com.tk.youfan.common.request.BaseCallBack;
import com.tk.youfan.common.request.TkHttpUtils;
import com.tk.youfan.domain.EventMessage;
import com.tk.youfan.domain.inspiration.Title;
import com.tk.youfan.fragment.inspirationchild.InspirationChildFragment;
import com.tk.youfan.utils.LogUtil;
import com.tk.youfan.utils.UrlContants;
import com.tk.youfan.utils.loadingandretry.LoadingAndRetryManager;
import com.tk.youfan.utils.loadingandretry.OnLoadingAndRetryListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：tpkeeper on 2016/9/28 00:07
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class InspirationFragment extends BaseFragment {
    ImageButton topSearchMain;
    SmartTabLayout viewpagertab;
    ViewPager viewpager;
    private List<InspirationChildFragment> inspirationChildFragments;
    private List<Title> titleList;
    private LoadingAndRetryManager mloadingAndRetryManager;

    @Override
    public View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.inspiration_fragment, null);
        topSearchMain = (ImageButton) view.findViewById(R.id.top_search_main);
        viewpagertab = (SmartTabLayout) view.findViewById(R.id.viewpagertab_inspiration);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager_inspiration);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        //loadingandretrymanager
        mloadingAndRetryManager = new LoadingAndRetryManager(viewpager, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                InspirationFragment.this.setRetryEvent(retryView);
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

    @Override
    public void getDataFromNet() {
        super.getDataFromNet();
        TkHttpUtils.getInstance().get(UrlContants.LG_1, new BaseCallBack<String>() {

            @Override
            public void onSuccess(Response response, String s) {
                if (TextUtils.isEmpty(s)) {
                    LogUtil.e("response is empty in inspirationfragment ");
                    mloadingAndRetryManager.showEmpty();
                    return;
                }
                processData(s);
            }

            @Override
            public void onFailure(Call call, Exception e) {
                LogUtil.e("tkhttp load err in inspiration fragment!!!");
                mloadingAndRetryManager.showRetry();
            }
        });
//        OkHttpUtils.get()
//                .url(UrlContants.LG_1)
//                .build()
//                .execute(new MyStringCallBack());
    }

    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        inspirationChildFragments = new ArrayList<>();
        String[] urlArray = {UrlContants.LG_1, UrlContants.LG_2, UrlContants.LG_3, UrlContants.LG_4, UrlContants.LG_5, UrlContants.LG_6, UrlContants.LG_7, UrlContants.LG_8};
        for (int i = 0; i < titleList.size(); i++) {
            inspirationChildFragments.add(new InspirationChildFragment(urlArray[i]));
        }

        viewpager.setAdapter(new MyFragmentPagerAdapter(getFragmentManager()));
        viewpagertab.setViewPager(viewpager);
    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position).getName();
        }

        @Override
        public Fragment getItem(int position) {
            return inspirationChildFragments.get(position);
        }

        @Override
        public int getCount() {
            return inspirationChildFragments == null ? 0 : inspirationChildFragments.size();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.top_search_main)
    public void onClick() {
    }

//    private class MyStringCallBack extends StringCallback {
//        @Override
//        public void onError(Call call, Exception e, int id) {
//            LogUtil.e("okhttp load err in inspiration fragment!!!");
//            mloadingAndRetryManager.showRetry();
//        }
//
//        @Override
//        public void onResponse(String response, int id) {
//            if (TextUtils.isEmpty(response)) {
//                LogUtil.e("response is empty in inspirationfragment ");
//                mloadingAndRetryManager.showEmpty();
//                return;
//            }
//            processData(response);
//        }
//    }

    private void processData(String response) {
        JSONObject jsonObject = JSON.parseObject(response);
        String data = jsonObject.getString("data");
        JSONObject dataObject = JSON.parseObject(data);
        String attr = dataObject.getString("attr");
        titleList = JSON.parseArray(attr, Title.class);
        mloadingAndRetryManager.showContent();
        EventBus.getDefault().post(new EventMessage(EventMessage.MESSAGE_DATA_GETED_INSPIRATION_TITLE_DATA));
    }

    @Subscribe
    public void onEventMessage(EventMessage eventMessage) {
        switch (eventMessage.getMessage()) {
            case EventMessage.MESSAGE_DATA_GETED_HomeData:
                break;
            case EventMessage.MESSAGE_DATA_GETED_INSPIRATION_TITLE_DATA:
                initViewPager();
                break;
        }
    }
}
