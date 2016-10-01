package com.tk.youfan.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.ogaclejapan.smarttablayout.SmartTabIndicationInterpolator;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.tk.youfan.R;
import com.tk.youfan.activity.MainActivity;
import com.tk.youfan.base.BaseFragment;
import com.tk.youfan.fragment.searchchild.BrandFragment;
import com.tk.youfan.fragment.searchchild.CategoryFragment;
import com.tk.youfan.fragment.searchchild.TrendFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：tpkeeper on 2016/9/28 00:07
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：分类页面
 */
public class SearchFragment extends BaseFragment {
    @Bind(R.id.top_cebian_main)
    ImageButton topCebianMain;
    @Bind(R.id.top_search_main)
    ImageButton topSearchMain;
    @Bind(R.id.rla_title)
    RelativeLayout rlaTitle;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.viewpagertab)
    SmartTabLayout viewpagertab;
    private List<BaseFragment> baseFragmentList;

    @Override
    public View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_fragment, null);
        ButterKnife.bind(this, view);
        initListener();
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        initViewPager();
    }

    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        baseFragmentList = new ArrayList<>();
        baseFragmentList.add(new CategoryFragment());
        baseFragmentList.add(new BrandFragment());
        baseFragmentList.add(new TrendFragment());
        viewpager.setAdapter(new MyFragmentPagerAdapter(getFragmentManager()));
        viewpagertab.setViewPager(viewpager);
        viewpagertab.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        topCebianMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).slidingMenu.toggle();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            CharSequence title = "";
            switch (position) {
                case 0:
                    title = "品类";
                    break;
                case 1:
                    title = "品牌";
                    break;
                case 2:
                    title = "动态";
                    break;
            }
            return title;
        }

        @Override
        public Fragment getItem(int position) {
            return baseFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return baseFragmentList == null ? 0 : baseFragmentList.size();
        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
