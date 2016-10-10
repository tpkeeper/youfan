package com.tk.youfan.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.tk.youfan.R;
import com.tk.youfan.domain.goodsdetail.GoodsDetailSelect;
import com.tk.youfan.utils.LogUtil;
import com.tk.youfan.utils.ScreenUtils;
import com.tk.youfan.utils.UrlContants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：tpkeeper on 2016/9/30 05:59
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class GoodsSelectPopupWindow extends PopupWindow {

    ImageView imgGoodsDetailSelect;
    TextView tvPrice;
    TextView tvStoreCount;
    TextView tvGoodsId;
    LinearLayout linGoodsColor;
    LinearLayout linGoodsSize;
    AutoSubAdd autoSubAdd;
    Button btnConfirm;
    LinearLayout linPup;
    private Context mContext;
    ImageView btn_cancle;
    String code;
    private View view;
    private String url;
    private List<GoodsDetailSelect> goodsDetailSelects;
    private List<String> colorList;
    private List<String> sizeList;

    public GoodsSelectPopupWindow(Context mContext, String code) {
        this.code = code;
        this.mContext = mContext;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.goods_select_popup, null);
        initView();
        initListener();
        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.lin_pup).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight((int) (ScreenUtils.getScreenHeight(mContext) * 0.7));
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.pop_goods_select);
        initData();
    }

    private void initListener() {
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initView() {
        imgGoodsDetailSelect = (ImageView) view.findViewById(R.id.img_goods_detail_select);
        tvPrice = (TextView) view.findViewById(R.id.tv_price);
        tvStoreCount = (TextView) view.findViewById(R.id.tv_store_count);
        tvGoodsId = (TextView) view.findViewById(R.id.tv_goods_id);
        linGoodsColor = (LinearLayout) view.findViewById(R.id.lin_goods_color);
        linGoodsSize = (LinearLayout) view.findViewById(R.id.lin_goods_size);
        autoSubAdd = (AutoSubAdd) view.findViewById(R.id.auto_sub_add);
        btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        linPup = (LinearLayout) view.findViewById(R.id.lin_pup);
        btn_cancle = (ImageView) view.findViewById(R.id.btn_cancle);
    }

    private void initData() {
        url = UrlContants.GOODS_DETAIL_SELECT_PRE + code + UrlContants.GOODS_DETAIL_SELECT_TAIL;
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new MyStringCallBack());
    }

    @OnClick(R.id.btn_confirm)
    public void onClick() {
    }

    //点击事件接口
    public interface OnItemClickListener {
        void onClick(View view, int index);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private class MyStringCallBack extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            LogUtil.e("okhttputils load err in goodsdetailselectpupwindow ");
        }

        @Override
        public void onResponse(String response, int id) {
            if (TextUtils.isEmpty(response)) {
                LogUtil.e("response is empty in goodsdetailselectpupwindow");
            }
            processData(response);
        }
    }

    private void processData(String response) {
        JSONObject jsonObject = JSON.parseObject(response);
        String results = jsonObject.getString("results");
        goodsDetailSelects = JSON.parseArray(results, GoodsDetailSelect.class);
//        得到所有的颜色,尺码
        colorList = new ArrayList<>();
        sizeList = new ArrayList<>();
        GoodsDetailSelect goodsDetailSelect;
        String coloR_name;
        String speC_name;
        for (int i = 0; i < goodsDetailSelects.size(); i++) {
            goodsDetailSelect = goodsDetailSelects.get(i);
            coloR_name = goodsDetailSelect.getColoR_NAME();
            speC_name = goodsDetailSelect.getSpeC_NAME();
            if (!colorList.contains(coloR_name)) {
                colorList.add(coloR_name);
            }
            if (!sizeList.contains(speC_name)) {
                sizeList.add(speC_name);
            }
        }
        //给view设置数据
        setData();
    }

    private void setData() {
        GoodsDetailSelect goodsDetailSelect = goodsDetailSelects.get(0);
        String coloR_file_path = goodsDetailSelect.getColoR_FILE_PATH();
        Glide.with(mContext)
                .load(coloR_file_path)
                .placeholder(R.drawable.default100)
                .into(imgGoodsDetailSelect);
        tvPrice.setText("￥" + goodsDetailSelect.getPrice());
        tvStoreCount.setText("库存" + goodsDetailSelect.getLisT_QTY() + "件");
        tvGoodsId.setText("商品编号:" + goodsDetailSelect.getProD_CLS_NUM());

        //填充颜色linGoodsColor
        View colorView;
        LinearLayout lin_parent = null;
        if (colorList.size() != 0) {
            for (int i = 0; i < colorList.size(); i++) {
                int childNum = i%4;
                if (i % 4 == 0) {
                    colorView = LayoutInflater.from(mContext).inflate(R.layout.item_goods_detail_select_color, null);
                    lin_parent = (LinearLayout) colorView.findViewById(R.id.lin_parent);
                    //加入一行布局,每行的textView设置为invisible占位
                    linGoodsColor.addView(colorView);
                }
                TextView textView = (TextView) lin_parent.getChildAt(childNum);
                textView.setVisibility(View.VISIBLE);
                textView.setText(colorList.get(i));
            }
        }

        //填充size
        View sizeView;
        LinearLayout lin_parent_size = null;
        if (sizeList.size() != 0) {
            for (int i = 0; i < sizeList.size(); i++) {
                int childNum = i%4;
                if (i % 4 == 0) {
                    sizeView = LayoutInflater.from(mContext).inflate(R.layout.item_goods_detail_select_size, null);
                    lin_parent_size = (LinearLayout) sizeView.findViewById(R.id.lin_parent);
                    //加入一行布局
                    linGoodsSize.addView(sizeView);
                }
                TextView textView = (TextView) lin_parent_size.getChildAt(childNum);
                textView.setVisibility(View.VISIBLE);
                textView.setText(sizeList.get(i));
            }
        }
    }
}

