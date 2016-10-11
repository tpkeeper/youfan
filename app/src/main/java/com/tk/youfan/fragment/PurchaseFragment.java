package com.tk.youfan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.BoringLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tk.youfan.R;
import com.tk.youfan.base.BaseFragment;
import com.tk.youfan.base.BaseHolder;
import com.tk.youfan.dao.GoodsDao;
import com.tk.youfan.db.Model;
import com.tk.youfan.domain.goodsdetail.GoodsDetailSelect;
import com.tk.youfan.domain.home.Module;
import com.tk.youfan.domain.purchase.Goods;
import com.tk.youfan.utils.LogUtil;
import com.tk.youfan.utils.UrlContants;
import com.tk.youfan.view.AutoSubAdd;
import com.tk.youfan.view.DividerItemDecoration;
import com.tk.youfan.view.MyRecycleView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 作者：tpkeeper on 2016/9/28 00:07
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class PurchaseFragment extends BaseFragment {
    private static final int EDIT = 0;
    private static final int SHOW = 1;
    int state = SHOW;
    @Bind(R.id.tv_men_women)
    TextView tvMenWomen;
    @Bind(R.id.top_search_main)
    TextView topSearchMain;
    @Bind(R.id.rla_title)
    RelativeLayout rlaTitle;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;

    @Bind(R.id.checkbox_all)
    CheckBox checkboxAll;
    @Bind(R.id.tv_total_price)
    TextView tvTotalPrice;
    @Bind(R.id.btn_order)
    Button btnOrder;
    @Bind(R.id.btn_collect)
    Button btnCollect;
    @Bind(R.id.btn_delete)
    Button btnDelete;
    @Bind(R.id.lin_totalprice)
    LinearLayout lin_totalprice;
    private GoodsDao goodsDao;
    private List<Goods> goodsList;
    private List<GoodsDetailSelect> goodsDetailSelects;
    private RecyclerView.Adapter adapter;

    @Override
    public View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.purchase_fragment, null);
        ButterKnife.bind(this, view);
        initListener();
        return view;
    }

    private void initListener() {
        topSearchMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == SHOW) {
                    topSearchMain.setText("完成");
                    state = EDIT;
                } else {
                    topSearchMain.setText("编辑");
                    state = SHOW;
                }
                changeView();
            }
        });
    }

    private void changeView() {
        if (state == EDIT) {
            btnOrder.setVisibility(View.INVISIBLE);
            lin_totalprice.setVisibility(View.INVISIBLE);
            btnCollect.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            btnOrder.setVisibility(View.VISIBLE);
            lin_totalprice.setVisibility(View.VISIBLE);
            btnCollect.setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.INVISIBLE);
        }
        //把问题想复杂了，直接notifydatasetchanged就行了
        adapter.notifyDataSetChanged();
//        initrecyclerView();
        //这样调用会出现有的item不执行onbindViewHolder的方法，这样就导致有些item的view不能得到及时的更新
//        updateRecyclerItem();
    }

    private void updateRecyclerItem() {
        int childCount = recyclerview.getChildCount();
        LogUtil.e("childcount"+childCount);
        if (state == EDIT) {
            for (int i = 0; i < childCount; i++) {
                LogUtil.e("item:"+i);
                recyclerview.getChildAt(i).findViewById(R.id.auto_sub_add).setVisibility(View.VISIBLE);
                recyclerview.getChildAt(i).findViewById(R.id.rel_price).setVisibility(View.INVISIBLE);
                recyclerview.getChildAt(i).findViewById(R.id.tv_brand).setVisibility(View.INVISIBLE);
                recyclerview.getChildAt(i).findViewById(R.id.tv_name).setVisibility(View.INVISIBLE);
            }
        }else {
            for (int i = 0; i < childCount; i++) {
                recyclerview.getChildAt(i).findViewById(R.id.auto_sub_add).setVisibility(View.INVISIBLE);
                recyclerview.getChildAt(i).findViewById(R.id.rel_price).setVisibility(View.VISIBLE);
                recyclerview.getChildAt(i).findViewById(R.id.tv_brand).setVisibility(View.VISIBLE);
                recyclerview.getChildAt(i).findViewById(R.id.tv_name).setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        goodsDao = Model.getInstance().getDbManager().getGoodsDao();
        goodsList = goodsDao.getGoodsList();
        if (goodsList == null || goodsList.size() <= 0) {
            return;
        }
        initrecyclerView();
    }

    private void initrecyclerView() {
        adapter = new MyRecycleViewAdapter();
        LinearLayoutManager linearLayout = new LinearLayoutManager(mContext);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(linearLayout);
        recyclerview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class MyRecycleViewAdapter extends RecyclerView.Adapter<BaseHolder> {
        @Override
        public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LogUtil.e("viewType"+viewType);
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_purchase, parent, false);
            return new MyBaseHolder(mContext, view);
        }

        @Override
        public void onBindViewHolder(BaseHolder holder, int position) {
            LogUtil.e("bind data"+position);
            ((MyBaseHolder) holder).setItemData(goodsList.get(position));
        }

        @Override
        public int getItemCount() {
            return goodsList == null ? 0 : goodsList.size();
        }

        @Override
        public long getItemId(int position) {
            LogUtil.e("itemid"+position);
            return super.getItemId(position);
        }

        @Override
        public int getItemViewType(int position) {

            return super.getItemViewType(position);
        }
    }
int p;
    class MyBaseHolder extends BaseHolder {
        CheckBox checkbox;
        ImageView img_purchase;
        TextView tv_brand;
        TextView tv_name;
        TextView tv_color;
        TextView tv_size;
        TextView tv_price;
        TextView tv_count;
        Goods goods;
        LinearLayout lin_item;
        RelativeLayout rel_price;
        private GoodsDetailSelect goodsDetailSelect;
        AutoSubAdd auto_sub_add;

        public MyBaseHolder(Context mContext, View itemView) {
            super(mContext, itemView);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
            auto_sub_add = (AutoSubAdd) itemView.findViewById(R.id.auto_sub_add);
            img_purchase = (ImageView) itemView.findViewById(R.id.img_purchase);
            tv_brand = (TextView) itemView.findViewById(R.id.tv_brand);
            rel_price = (RelativeLayout) itemView.findViewById(R.id.rel_price);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_color = (TextView) itemView.findViewById(R.id.tv_color);
            tv_size = (TextView) itemView.findViewById(R.id.tv_size);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
            lin_item = (LinearLayout) itemView.findViewById(R.id.lin_item);
            LogUtil.e("new holder"+p++);
        }

        @Override
        public void setData(Module module) {

        }

        public void setItemData(Goods goods) {
            //根据状态设置要显示的view
            if(state==EDIT) {
                tv_brand.setVisibility(View.INVISIBLE);
                tv_name.setVisibility(View.INVISIBLE);
                rel_price.setVisibility(View.INVISIBLE);
                auto_sub_add.setVisibility(View.VISIBLE);
            }else {
                tv_brand.setVisibility(View.VISIBLE);
                tv_name.setVisibility(View.VISIBLE);
                rel_price.setVisibility(View.VISIBLE);
                auto_sub_add.setVisibility(View.INVISIBLE);
            }
            this.goods = goods;
            String url = UrlContants.GOODS_DETAIL_SELECT_PRE + goods.getProdClsNum() + UrlContants.GOODS_DETAIL_TAIL;
            OkHttpUtils.get()
                    .url(url)
                    .build()
                    .execute(new MyStringCb());

        }

        private class MyStringCb extends StringCallback {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtil.e("okhttp load data err in purchasefragment ");
            }

            @Override
            public void onResponse(String response, int id) {
                processData(response);
            }
        }

        private void processData(String response) {
            JSONObject jsonObject = JSON.parseObject(response);
            String results = jsonObject.getString("results");
            goodsDetailSelects = JSON.parseArray(results, GoodsDetailSelect.class);
            if (goodsDetailSelects == null || goodsDetailSelects.size() <= 0) {
                return;
            }
            for (int i = 0; i < goodsDetailSelects.size(); i++) {
                if (goodsDetailSelects.get(i).getLM_PROD_CLS_ID().equals(goods.getLmProdClsId())) {
                    goodsDetailSelect = goodsDetailSelects.get(i);
                    break;
                }
            }
            if (goodsDetailSelect != null) {
                Glide.with(mContext)
                        .load(goodsDetailSelect.getColoR_FILE_PATH())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.default100)
                        .into(img_purchase);
                tv_brand.setText(goodsDetailSelect.getBrand_name());
                tv_name.setText(goodsDetailSelect.getProD_NAME());
                tv_color.setText("颜色:" + goodsDetailSelect.getColoR_NAME());
                tv_size.setText("尺寸:" + goodsDetailSelect.getSpeC_NAME());
                tv_price.setText("￥" + goodsDetailSelect.getSalE_PRICE());
                tv_count.setText("X" + goods.getCount());
                auto_sub_add.setValue(goods.getCount());
                auto_sub_add.setMaxValue(goodsDetailSelect.getLisT_QTY());
            }

        }


    }


}
