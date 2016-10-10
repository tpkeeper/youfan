package com.tk.youfan.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tk.youfan.R;
import com.tk.youfan.base.BaseFragment;
import com.tk.youfan.dao.GoodsDao;
import com.tk.youfan.db.Model;
import com.tk.youfan.domain.goodsdetail.GoodsDetail;
import com.tk.youfan.domain.purchase.Goods;
import com.tk.youfan.fragment.goodsdetail.AskFragment;
import com.tk.youfan.fragment.goodsdetail.GoodsDetailFragment;
import com.tk.youfan.fragment.goodsdetail.ReviewFragment;
import com.tk.youfan.utils.LogUtil;
import com.tk.youfan.utils.ScreenUtils;
import com.tk.youfan.utils.UrlContants;
import com.tk.youfan.utils.loadingandretry.LoadingAndRetryManager;
import com.tk.youfan.utils.loadingandretry.OnLoadingAndRetryListener;
import com.tk.youfan.view.GoodsSelectPopupWindow;
import com.tk.youfan.view.MySlideDetails;
import com.tk.youfan.view.ObservableScrollView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bleu.widget.slidedetails.SlideDetailsLayout;
import okhttp3.Call;

public class GoodsDetailActivity extends FragmentActivity {
    private LoadingAndRetryManager mloadingAndRetryManager;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.tv_indicate)
    TextView tvIndicate;
    @Bind(R.id.tv_show_name)
    TextView tvShowName;
    @Bind(R.id.tv_sale_price)
    TextView tvSalePrice;
    @Bind(R.id.tv_market_price)
    TextView tvMarketPrice;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.img_share)
    ImageView imgShare;
    @Bind(R.id.btn_purchase_bag)
    Button btnPurchaseBag;
    @Bind(R.id.img_collect)
    ImageView imgCollect;
    @Bind(R.id.lin_activity)
    LinearLayout lin_activity;
    @Bind(R.id.slidedetails)
    MySlideDetails slidedetails;
    @Bind(R.id.img_brand_dispatch)
    ImageView img_brand_dispatch;
    @Bind(R.id.tv_show_bottom)
    TextView tv_show_bottom;
    @Bind(R.id.observable_scrollview)
    ObservableScrollView observable_scrollview;
    @Bind(R.id.viewpager_content)
    ViewPager viewpager_content;
    @Bind(R.id.lin_all)
    LinearLayout lin_all;
    @Bind(R.id.lin_top)
    LinearLayout lin_top;
    @Bind(R.id.img_purchase_bag)
    ImageView img_purchase_bag;
    private GoodsDetail goodsDetail;
    private List<GoodsDetail.ProPicUrlBean> proPicUrlBeanList;
    private int scrollViewHight;
    private int contentViewHight;
    private List<BaseFragment> baseFragmentList;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        //获取goods的Code
        code = getIntent().getStringExtra("code");
        initListener();
        getData();
    }
    private void getData(){
        //loadingpage加载
        mloadingAndRetryManager = new LoadingAndRetryManager(lin_top, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {

                GoodsDetailActivity.this.setRetryEvent(retryView);
            }
            //重写加载动画
            @Override
            public View generateLoadingLayout() {
                ImageView imageView = new ImageView(GoodsDetailActivity.this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setImageResource(R.drawable.loadingicons);
                AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
                animationDrawable.start();
                return imageView;
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
        btnPurchaseBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsSelectPopupWindow goodsSelectPopupWindow= new GoodsSelectPopupWindow(GoodsDetailActivity.this,code);
                goodsSelectPopupWindow.showAsDropDown(lin_all,0, (int) (-ScreenUtils.getScreenHeight(GoodsDetailActivity.this)*0.7));
            }
        });
        img_purchase_bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsDao goodsDao = Model.getInstance().getDbManager().getGoodsDao();
                List<Goods> goodsList = goodsDao.getGoodsList();
                if(goodsList==null) {
                    LogUtil.e("nullllll");
                }else {
                    LogUtil.e("dbsize:"+goodsList.size());
                    for (Goods goods : goodsList) {
                        LogUtil.e(goods.toString());
                    }
                }
            }
        });
    }

    private void debugScroll() {
        super.onResume();
        View parentView = (View) observable_scrollview.getParent();
        int parentViewHight = parentView.getBottom() - parentView.getTop();
        //解决scrollView内容少不能滑动响应的情况
        scrollViewHight = observable_scrollview.getBottom() - observable_scrollview.getTop();
        View contentView = observable_scrollview.getChildAt(0);
        contentViewHight = contentView.getBottom() - contentView.getTop();
        //解决slidingDetailsLayout嵌套scrollView不能滑动的问题

        if (parentViewHight > scrollViewHight) {
            //如果scrollView中的内容较少就直接设置为不能滑动
            slidedetails.setChildCanScroll(false);
        }
        observable_scrollview.setCallbacks(new myScrollCallBack());

    }

    private void getDataFromNet() {
        String url = UrlContants.GOODS_DETAIL_PRE + code + UrlContants.GOODS_DETAIL_TAIL;
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new MyStringCallBack());
    }

    @OnClick({R.id.img_back, R.id.img_share, R.id.btn_purchase_bag, R.id.img_collect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                break;
            case R.id.img_share:
                break;
            case R.id.btn_purchase_bag:
                break;
            case R.id.img_collect:
                break;
        }
    }

    private class MyStringCallBack extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            mloadingAndRetryManager.showRetry();
            LogUtil.e("okhttp load data err in goodsdetailactivity !");
        }

        @Override
        public void onResponse(String response, int id) {
            if (TextUtils.isEmpty(response)) {
                mloadingAndRetryManager.showEmpty();
                LogUtil.e("response is null in goodsdetailactivity");
                return;
            }
            processData(response);
        }
    }

    private void processData(String response) {
        JSONObject jsonObject = JSON.parseObject(response);
        String data = jsonObject.getString("data");
        goodsDetail = JSON.parseObject(data, GoodsDetail.class);
        proPicUrlBeanList = goodsDetail.getProPicUrl();
        mloadingAndRetryManager.showContent();
        initData();
    }

    private void initData() {
        tvShowName.setText(goodsDetail.getClsInfo().getShowName());
        tvSalePrice.setText("￥" + goodsDetail.getClsInfo().getSale_price());
        tvMarketPrice.setText("￥" + goodsDetail.getClsInfo().getMarketPrice());
        tvMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvIndicate.setText((1) + "/" + proPicUrlBeanList.size());
        List<GoodsDetail.ActivityBean> activityList = goodsDetail.getActivity();
        for (int i = 0; i < activityList.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_goods_detail_activity, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.img_activity1);
            TextView textView = (TextView) view.findViewById(R.id.tv_activity_name1);

            Glide.with(this)
                    .load(activityList.get(i).getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
            textView.setText(activityList.get(i).getName());
            lin_activity.addView(view);
        }
        Glide.with(this)
                .load("http://metersbonwe.qiniucdn.com/youfanchengnuo_shangjia.png")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_brand_dispatch);
        img_brand_dispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodsDetailActivity.this, JumpActivity.class);
                intent.putExtra("url", "http://m.funwear.com/wx/?p=4427&a=");
                intent.putExtra("title_later", "用户协议");
                GoodsDetailActivity.this.startActivity(intent);
            }
        });
        initViewPager();

        //放在数据填充之后不然测量的不准确
        debugScroll();

        //初始化第二个页面
        baseFragmentList = new ArrayList<>();
        baseFragmentList.add(new GoodsDetailFragment(goodsDetail,slidedetails));
        baseFragmentList.add(new ReviewFragment(goodsDetail,slidedetails));
        baseFragmentList.add(new AskFragment(goodsDetail,slidedetails));
        viewpager_content.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
    }

    private void initViewPager() {
        viewpager.setAdapter(new MyViewPagerAdapter());
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvIndicate.setText((position + 1) + "/" + proPicUrlBeanList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class MyViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return proPicUrlBeanList == null ? 0 : proPicUrlBeanList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.item_googds_detail_img, null);
            ImageView img_goods_detail = (ImageView) view.findViewById(R.id.img_goods_detail);
            Glide.with(GoodsDetailActivity.this)
                    .load(proPicUrlBeanList.get(position).getFilE_PATH())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_goods_detail);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (container != null) {
                container.removeViewAt(position);
            }
        }
    }

    private class myScrollCallBack implements ObservableScrollView.Callbacks {
        @Override
        public void onScrollChanged(int scrollY) {
            scrollViewHight = observable_scrollview.getBottom() - observable_scrollview.getTop();
            if (scrollY >= contentViewHight - scrollViewHight) {
                slidedetails.setChildCanScroll(false);
            } else {
                slidedetails.setChildCanScroll(true);
            }
        }

        @Override
        public void onDownMotionEvent() {

        }

        @Override
        public void onUpOrCancelMotionEvent() {

        }
    }

    private class MyFragmentAdapter extends FragmentPagerAdapter {
        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
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
}