package com.tk.youfan.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tk.youfan.R;
import com.tk.youfan.domain.goodsdetail.GoodsDetail;
import com.tk.youfan.utils.LogUtil;
import com.tk.youfan.utils.UrlContants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class GoodsDetailActivity extends AppCompatActivity {

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
    private GoodsDetail goodsDetail;
    private List<GoodsDetail.ProPicUrlBean> proPicUrlBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        getDataFromNet();
    }

    private void getDataFromNet() {
        String code = getIntent().getStringExtra("code");
        String url = UrlContants.GOODS_DETAIL_PRE+code+UrlContants.GOODS_DETAIL_TAIL;
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
            LogUtil.e("okhttp load data err in goodsdetailactivity !");
        }

        @Override
        public void onResponse(String response, int id) {
            if(TextUtils.isEmpty(response)) {
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
        initData();
    }

    private void initData() {
        tvShowName.setText(goodsDetail.getClsInfo().getShowName());
        tvSalePrice.setText("￥"+goodsDetail.getClsInfo().getSale_price());
        tvMarketPrice.setText("￥"+goodsDetail.getClsInfo().getMarketPrice());
        tvMarketPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
        tvIndicate.setText((1)+"/"+proPicUrlBeanList.size());
        initViewPager();
    }

    private void initViewPager() {
        viewpager.setAdapter(new MyViewPagerAdapter());
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvIndicate.setText((position+1)+"/"+proPicUrlBeanList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class MyViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return proPicUrlBeanList == null ?0:proPicUrlBeanList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(GoodsDetailActivity.this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(GoodsDetailActivity.this)
                    .load(proPicUrlBeanList.get(position).getFilE_PATH())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeViewAt(position);
        }
    }
}
