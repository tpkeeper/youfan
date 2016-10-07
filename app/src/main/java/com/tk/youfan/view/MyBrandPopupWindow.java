package com.tk.youfan.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.quicksidebar.QuickSideBarView;
import com.bigkoo.quicksidebar.listener.OnQuickSideBarTouchListener;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.tk.youfan.R;
import com.tk.youfan.adapter.search.BrandSequenceListAdapter;
import com.tk.youfan.domain.search.brandrequence.BrandInfo;
import com.tk.youfan.domain.search.brandrequence.GroupData;
import com.tk.youfan.utils.LogUtil;
import com.tk.youfan.utils.UrlContants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

/**
 * 作者：tpkeeper on 2016/10/7 12:00
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class MyBrandPopupWindow extends PopupWindow implements OnQuickSideBarTouchListener {
    private Context mContext;
    private View view;
    private Button btn_cancel, btn_sure;
    RecyclerView recyclerview;
    QuickSideBarView quickSideBarView;
    private List<GroupData> groupDatas;
    private List<BrandInfo> brandInfoList;
    HashMap<String,Integer> letters = new HashMap<>();
    ImageView img_dismiss;
    public MyBrandPopupWindow(Context mContext, View.OnClickListener itemsOnClick) {
        this.mContext = mContext;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.brand_popupwindow_layout, null);
        img_dismiss = (ImageView) view.findViewById(R.id.img_dismiss);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        quickSideBarView = (QuickSideBarView) view.findViewById(R.id.quickSideBarView);
        //放到前边
        getDataFromNet();
        img_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.brand_popupwindow).getTop();

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
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xd0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.pop_brand);

    }

    private void getDataFromNet() {
        String url = UrlContants.SORT_BRAND_BLACK_LIFE;
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new MyStringCallBack());
    }

    /**
     * extends from OnQuickSideBarTouchListener
     * @param letter
     * @param position
     * @param y
     */
    @Override
    public void onLetterChanged(String letter, int position, float y) {

    }

    @Override
    public void onLetterTouching(boolean touching) {

    }



    private class MyStringCallBack extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            LogUtil.e("okhttputil load data err in myBrandPopupwindow");
        }

        @Override
        public void onResponse(String response, int id) {
            if (TextUtils.isEmpty(response)) {
                LogUtil.e("response is null in mybrandpopupwindow");
                return;
            }
            processData(response);
        }
    }

    private void processData(String response) {
        JSONObject jsonObject = JSON.parseObject(response);
        String data = jsonObject.getString("data");
        groupDatas = JSON.parseArray(data, GroupData.class);
        List<BrandInfo> brandInfos;
        brandInfoList = new ArrayList<>();
        for (int i= 0;i<groupDatas.size();i++){
            brandInfos = groupDatas.get(i).getBrandInfo();
            for (int j = 0;j<brandInfos.size();j++){
                brandInfoList.add(brandInfos.get(j));
            }
        }
        initData();
    }

    private void initData() {
        //设置监听
        quickSideBarView.setOnQuickSideBarTouchListener(this);

        //设置列表数据和浮动header
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(layoutManager);

        // Add the sticky headers decoration
        BrandSequenceListAdapter adapter = new BrandSequenceListAdapter(mContext,brandInfoList);

//       定义lettler
        ArrayList<String> customLetters = new ArrayList<>();
        int position = 0;
        for (BrandInfo brandInfo : brandInfoList) {
            String letter = brandInfo.getFirst_letter();
            //如果没有这个key则加入并把位置也加入
            if (!letters.containsKey(letter)) {
                letters.put(letter, position);
                customLetters.add(letter);
            }
            position++;
        }

        //不自定义则默认26个字母
        quickSideBarView.setLetters(customLetters);

        recyclerview.setAdapter(adapter);

        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        recyclerview.addItemDecoration(headersDecor);

        // Add decoration for dividers between list items
//        recyclerview.addItemDecoration(new DividerDecoration(mContext));
    }
}
