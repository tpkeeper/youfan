package com.tk.youfan.activity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
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

public class BrandDetailActivity extends FragmentActivity implements ObservableScrollView.Callbacks {

    @Bind(R.id.top_cebian_brand_detail)
    ImageButton topCebianBrandDetail;
    @Bind(R.id.tv_men_women_brand_detail)
    TextView tvMenWomenBrandDetail;
    @Bind(R.id.top_search_brand_detail)
    ImageButton topSearchBrandDetail;
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
    @Bind(R.id.tv_brand_name_detail)
    TextView tvBrandNameDetail;
    @Bind(R.id.btn_collect_brand)
    Button btnCollectBrand;
    @Bind(R.id.img_logo)
    ImageView imgLogo;
    @Bind(R.id.smart_tablayout)
    SmartTabLayout smartTablayout;
    @Bind(R.id.tv_select)
    TextView tvSelect;
    @Bind(R.id.drawerlayout)
    DrawerLayout drawerLayout;
//    @Bind(R.id.recyclerview_drawer)
//    RecyclerView recyclerview_drawer;
    @Bind(R.id.drawer_side)
    FrameLayout drawer_side;
    @Bind(R.id.expandable_listview)
    ExpandableListView expandable_listview;
    private String url;
    private BrandStory brandStory;
    private List<BaseFragment> baseFragmentList;
    private MyfragmentViewPagerAdapter adapter;
    private String[] childs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_brand_detail);
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    private void initData() {
        String brand_code = getIntent().getStringExtra("brand_code");
        url = UrlContants.BRAND_STORY_PRE + brand_code + UrlContants.BRAND_STORY_TAIL;
        getDataFromNet();
        childs = new String[]{"男生","女生"};
        initExpandableListView();
    }

    private void initExpandableListView() {
        expandable_listview.setAdapter(new ExpandableListAdapter() {
            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getGroupCount() {
                return 1;
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return 2;
            }

            @Override
            public Object getGroup(int groupPosition) {
                return childs;
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return childs[childPosition];
            }

            @Override
            public long getGroupId(int groupPosition) {
                return groupPosition;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                View view = LayoutInflater.from(BrandDetailActivity.this).inflate(R.layout.group_right,parent,false);
                return view;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                View view = LayoutInflater.from(BrandDetailActivity.this).inflate(R.layout.item_child_right,parent,false);
                TextView tv_item = (TextView) view.findViewById(R.id.tv_item);
                tv_item.setText(childs[childPosition]);
                return view;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return false;
            }

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public void onGroupExpanded(int groupPosition) {

            }

            @Override
            public void onGroupCollapsed(int groupPosition) {

            }

            @Override
            public long getCombinedChildId(long groupId, long childId) {
                return 0;
            }

            @Override
            public long getCombinedGroupId(long groupId) {
                return 0;
            }
        });
    }

    private void getDataFromNet() {
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new MyStringCallBack());
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

        tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    drawerLayout.openDrawer(drawer_side);
            }
        });

    }

    @OnClick({R.id.top_cebian_brand_detail, R.id.tv_men_women_brand_detail, R.id.top_search_brand_detail, R.id.top_share_brand_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_cebian_brand_detail:
                break;
            case R.id.tv_men_women_brand_detail:
                break;
            case R.id.top_search_brand_detail:
                break;
            case R.id.top_share_brand_detail:
                break;
        }
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


    private class MyStringCallBack extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            LogUtil.e("okhttp load data err in BrandDetailActivity");
        }

        @Override
        public void onResponse(String response, int id) {
            processData(response);
        }
    }

    private void processData(String response) {
        if (TextUtils.isEmpty(response)) {
            LogUtil.e("response is null in brandDetailActivity ");
            return;
        }
        JSONObject jsonObject = JSON.parseObject(response);
        String data = jsonObject.getString("data");
        brandStory = JSON.parseObject(data, BrandStory.class);

        if (brandStory != null) {
            setData();
        }
    }

    private void setData() {
        Glide.with(this)
                .load(brandStory.getYoufan_img())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_header_brand_detail);
        Glide.with(this)
                .load(brandStory.getLogo_img())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgLogo);
        tvBrandNameDetail.setText(brandStory.getEname());

        initViewPager();

    }

    private void initViewPager() {
        baseFragmentList = new ArrayList<>();
        String brand_code = brandStory.getBrand_code();
        String urlNewHot = UrlContants.BRANDDETAIL_PRE + brand_code + UrlContants.BRANDDETAIL_TAIL;
        String urlPrice = UrlContants.BRANDDETAIL_PRICE_PRE + brand_code + UrlContants.BRANDDETAIL_TAIL;
        baseFragmentList.add(new BrandDetailFragment(urlNewHot));
        baseFragmentList.add(new BrandDetailFragment(urlNewHot));
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
