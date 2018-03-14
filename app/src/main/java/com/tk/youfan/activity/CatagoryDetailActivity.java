package com.tk.youfan.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.tk.youfan.R;
import com.tk.youfan.base.BaseFragment;
import com.tk.youfan.domain.search.branddetail.BrandStory;
import com.tk.youfan.fragment.searchchild.BrandDetailFragment;
import com.tk.youfan.utils.LogUtil;
import com.tk.youfan.utils.UrlContants;
import com.tk.youfan.view.ObservableScrollView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class CatagoryDetailActivity extends FragmentActivity implements ObservableScrollView.Callbacks {
    @Bind(R.id.top_cebian_brand_detail)
    ImageButton topCebianBrandDetail;
    @Bind(R.id.tv_men_women_catagory_detail)
    TextView tv_men_women_catagory_detail;

    @Bind(R.id.top_share_brand_detail)
    ImageButton topShareBrandDetail;
    @Bind(R.id.rla_title)
    RelativeLayout rlaTitle;
    @Bind(R.id.stopView)
    View stopView;
    @Bind(R.id.viewpager_brand_detail)
    ViewPager viewpagerBrandDetail;
    @Bind(R.id.stickyView)
    LinearLayout stickyView;
    @Bind(R.id.scrollView_brand_detail)
    ObservableScrollView scrollViewBrandDetail;
    @Bind(R.id.img_header_brand_detail)
    ImageView img_header_brand_detail;
    @Bind(R.id.smart_tablayout)
    SmartTabLayout smartTablayout;
    @Bind(R.id.tv_select)
    TextView tvSelect;
    private String imgUrl;
    private List<BaseFragment> baseFragmentList;
    private MyfragmentViewPagerAdapter adapter;
    private String brand_code;
    private String brand_url;
    private String brand_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_catagory_detail);
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    private void initData() {
        brand_code = getIntent().getStringExtra("brand_code");
        brand_url = getIntent().getStringExtra("brand_url");
        brand_name = getIntent().getStringExtra("brand_name");
        imgUrl = UrlContants.BRAND_STORY_PRE + brand_code + UrlContants.BRAND_STORY_TAIL;
        setData();
    }

    private void initListener() {
        ViewGroup.LayoutParams params = viewpagerBrandDetail.getLayoutParams();
        params.height = 10000;
        viewpagerBrandDetail.setLayoutParams(params);
        scrollViewBrandDetail.setCallbacks(this);
        scrollViewBrandDetail.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        onScrollChanged(scrollViewBrandDetail.getScrollY());
                    }
                });
        scrollViewBrandDetail.scrollTo(0, 0);
        scrollViewBrandDetail.smoothScrollTo(0, 0);

    }



    @Override
    public void onScrollChanged(int scrollY) {
        //设置浮动view相对于父视图的位置
        ((LinearLayout) this.findViewById(R.id.stickyView))
                .setTranslationY(Math.max(stopView.getTop(), scrollY));
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent() {

    }



    private void setData() {
        Glide.with(this)
                .load(brand_url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_header_brand_detail);
        tv_men_women_catagory_detail.setText(brand_name);
        initViewPager();

    }

    private void initViewPager() {
        baseFragmentList = new ArrayList<>();
        String urlNew= UrlContants.PINLEI_NEW_PRE+ brand_code +UrlContants.PINLEI_NEW_TAIL;
        String urlHot = UrlContants.PINLEI_HOT_SALE_PRE+brand_code+UrlContants.PINLEI_HOT_SALE_TAIL;
        String urlPrice = UrlContants.PINLEI_PRICE_PRE+brand_code+UrlContants.PINLEI_PRICE_TAIL;
        baseFragmentList.add(new BrandDetailFragment(urlNew));
        baseFragmentList.add(new BrandDetailFragment(urlHot));
        baseFragmentList.add(new BrandDetailFragment(urlPrice));
        adapter = new MyfragmentViewPagerAdapter(getSupportFragmentManager());
        viewpagerBrandDetail.setAdapter(adapter);
        smartTablayout.setViewPager(viewpagerBrandDetail);
    }

    private class MyfragmentViewPagerAdapter extends FragmentPagerAdapter {
        public MyfragmentViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return baseFragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (position) {
                case 0:
                    title = "上新";
                    break;
                case 1:
                    title = "热销";
                    break;
                case 2:
                    title = "价格";
                    break;
            }
            return title;
        }

        @Override
        public int getCount() {
            return baseFragmentList == null ? 0 : baseFragmentList.size();
        }
    }
}
