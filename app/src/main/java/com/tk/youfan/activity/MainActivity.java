package com.tk.youfan.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.tk.youfan.R;
import com.tk.youfan.base.BaseFragment;
import com.tk.youfan.fragment.HomeFragment;
import com.tk.youfan.fragment.InspirationFragment;
import com.tk.youfan.fragment.MeFragment;
import com.tk.youfan.fragment.PurchaseFragment;
import com.tk.youfan.fragment.SearchFragment;
import com.tk.youfan.utils.Constants;
import com.tk.youfan.utils.SPUtils;
import com.tk.youfan.utils.ScreenUtils;
import com.tk.youfan.utils.UrlContants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends SlidingFragmentActivity {

    @Bind(R.id.frm_content)
    FrameLayout frmContent;
    @Bind(R.id.rdo_bottom_home)
    RadioButton rdoBottomHome;
    @Bind(R.id.rdo_bottom_search)
    RadioButton rdoBottomSearch;
    @Bind(R.id.rdo_bottom_inspiration)
    RadioButton rdoBottomInspiration;
    @Bind(R.id.rdo_bottom_purchase)
    RadioButton rdoBottomPurchase;
    @Bind(R.id.rdo_bottom_me)
    RadioButton rdoBottomMe;
    @Bind(R.id.rdg_bottom)
    RadioGroup rdgBottom;

    private String nowTag;
    private String preTag;
    private TextView tv_man_left;
    private TextView tv_women_left;
    private TextView tv_life_left;
    private SPUtils spUtils;
    public SlidingMenu slidingMenu;
    private HomeFragment homeFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initSlidingMenu();
        initFragments(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //在此周期，保证initFragments执行完之后
        initListener();
    }

    private void initFragments(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            homeFragment = new HomeFragment();
            SearchFragment searchFragment = new SearchFragment();
            InspirationFragment inspirationFragment = new InspirationFragment();
            PurchaseFragment purchaseFragment = new PurchaseFragment();
            MeFragment meFragment = new MeFragment();
            //使用fragment的类名做tag
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frm_content, homeFragment, homeFragment.getClass().getSimpleName())
                    .hide(homeFragment)
                    .add(R.id.frm_content, searchFragment, searchFragment.getClass().getSimpleName())
                    .hide(searchFragment)
                    .add(R.id.frm_content, inspirationFragment, inspirationFragment.getClass().getSimpleName())
                    .hide(inspirationFragment)
                    .add(R.id.frm_content, purchaseFragment, purchaseFragment.getClass().getSimpleName())
                    .hide(purchaseFragment)
                    .add(R.id.frm_content, meFragment, meFragment.getClass().getSimpleName())
                    .hide(meFragment)
                    .commit();
            //第一次启动
            nowTag = HomeFragment.class.getSimpleName();
        } else {
            //内存启动
            preTag = savedInstanceState.getString("preTag");
            nowTag = savedInstanceState.getString("nowTag");
        }
    }

    private void initSlidingMenu() {
        //设置主界面
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //左侧菜单
        setBehindContentView(R.layout.left_menu);
        slidingMenu = getSlidingMenu();
        //左侧模式
        slidingMenu.setMode(SlidingMenu.LEFT);
        //边缘滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        int screenWidth = ScreenUtils.getScreenWidth(this);
        //设置主页宽度
        slidingMenu.setBehindOffset((int) (screenWidth * 0.425));

//        左侧菜单
        tv_man_left = (TextView) findViewById(R.id.tv_man_left);
        tv_women_left = (TextView) findViewById(R.id.tv_women_left);
        tv_life_left = (TextView) findViewById(R.id.tv_life_left);
    }

    private void initListener() {
        rdgBottom.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        if (preTag == null) {
//            第一次启动，check到主页
            rdgBottom.check(R.id.rdo_bottom_home);
        }
        //否则为内存恢复启动，由后台切换到前台仍然显示这个页面，不需要check

        //左侧菜单的点击事件
        initLeftMenuListener();
    }

    private void initLeftMenuListener() {
        spUtils = new SPUtils(this, Constants.SP_NAME);
        tv_man_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spUtils.putInt(Constants.GENDER, Constants.URL_TYPE_MAN);
                slidingMenu.toggle();
                //刷新
                //当fragment重新创建时，homeFragment 指向的引用已经不存在，造成空指针
                //homeFragment.refreshlayout.autoRefresh();
                //解决,重新找到真实引用
                HomeFragment homeFragment = (HomeFragment) MainActivity.this.getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
                SearchFragment searchFragment = (SearchFragment) MainActivity.this.getSupportFragmentManager().findFragmentByTag(SearchFragment.class.getSimpleName());
                if (!homeFragment.isHidden()) {
                    homeFragment.refreshlayout.autoRefresh();
                }
                if (!searchFragment.isHidden()) {
                    searchFragment.getDataFromNet();
                }

            }
        });
        tv_women_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spUtils.putInt(Constants.GENDER, Constants.URL_TYPE_WOMAN);
                slidingMenu.toggle();

//                刷新
                HomeFragment homeFragment = (HomeFragment) MainActivity.this.getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
                SearchFragment searchFragment = (SearchFragment) MainActivity.this.getSupportFragmentManager().findFragmentByTag(SearchFragment.class.getSimpleName());
                if (!homeFragment.isHidden()) {
                    homeFragment.refreshlayout.autoRefresh();
                }
                if (!searchFragment.isHidden()) {
                    searchFragment.getDataFromNet();
                }
            }
        });
        tv_life_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spUtils.putInt(Constants.GENDER, Constants.URL_TYPE_LIFE);
                slidingMenu.toggle();
                    //刷新
                HomeFragment homeFragment = (HomeFragment) MainActivity.this.getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
                SearchFragment searchFragment = (SearchFragment) MainActivity.this.getSupportFragmentManager().findFragmentByTag(SearchFragment.class.getSimpleName());
                if (!homeFragment.isHidden()) {
                    homeFragment.refreshlayout.autoRefresh();
                }
                if (!searchFragment.isHidden()) {
                    searchFragment.getDataFromNet();
                }
            }
        });
    }

    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            preTag = nowTag;
            switch (checkedId) {
                case R.id.rdo_bottom_home:
                    nowTag = HomeFragment.class.getSimpleName();
                    break;
                case R.id.rdo_bottom_search:
                    nowTag = SearchFragment.class.getSimpleName();
                    break;
                case R.id.rdo_bottom_inspiration:
                    nowTag = InspirationFragment.class.getSimpleName();
                    break;
                case R.id.rdo_bottom_purchase:
                    nowTag = PurchaseFragment.class.getSimpleName();
                    break;
                case R.id.rdo_bottom_me:
                    nowTag = MeFragment.class.getSimpleName();
                    break;
            }
            BaseFragment hideFragment = (BaseFragment) MainActivity.this.getSupportFragmentManager().findFragmentByTag(preTag);
            BaseFragment showFragment = (BaseFragment) MainActivity.this.getSupportFragmentManager().findFragmentByTag(nowTag);
            showHideFragment(showFragment, hideFragment);
        }
    }


    private void showHideFragment(Fragment showFragment, Fragment hideFragment) {
        if (showFragment == null || hideFragment == null) {
            Log.i("T", "showFragment ==null or hideFragment ==null");
            return;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .hide(hideFragment)
                .show(showFragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("nowTag", nowTag);
        outState.putString("preTag", preTag);
    }
}
