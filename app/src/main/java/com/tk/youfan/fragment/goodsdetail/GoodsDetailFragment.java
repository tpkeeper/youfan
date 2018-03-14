package com.tk.youfan.fragment.goodsdetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tk.youfan.R;
import com.tk.youfan.activity.ShowPhotoView;
import com.tk.youfan.base.BaseFragment;
import com.tk.youfan.domain.goodsdetail.GoodsDetail;
import com.tk.youfan.domain.search.branddetail.ClsInfo;
import com.tk.youfan.utils.LogUtil;
import com.tk.youfan.view.MySlideDetails;
import com.tk.youfan.view.ObservableScrollView;

import java.util.List;

import cn.bleu.widget.slidedetails.SlideDetailsLayout;

/**
 * 作者：tpkeeper on 2016/10/9 22:08
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class GoodsDetailFragment extends BaseFragment {
    GoodsDetail goodsDetail;
    private LinearLayout lin_image;
    private List<GoodsDetail.ClsPicUrlBean> clsPicUrl;
    private ObservableScrollView observable_scrollview_foot;
    private int scrollViewHight;
    private int contentViewHight;
    MySlideDetails slidedetails;
    private ViewGroup.LayoutParams layoutParams;

    public GoodsDetailFragment(GoodsDetail goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public GoodsDetailFragment(GoodsDetail goodsDetail, MySlideDetails slidedetails) {
        this.slidedetails = slidedetails;
        this.goodsDetail = goodsDetail;
    }

    @Override
    public View initView() {
       View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_goods_detail,null);
        observable_scrollview_foot = (ObservableScrollView) view.findViewById(R.id.observable_scrollview_foot);
        lin_image = (LinearLayout) view.findViewById(R.id.lin_image);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        getDataFromNet();
    }

    @Override
    public void getDataFromNet() {
        super.getDataFromNet();
        if(goodsDetail!=null) {
            clsPicUrl = goodsDetail.getClsPicUrl();
        }

        for(int i = 0;i<clsPicUrl.size();i++ ){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_googds_detail_foot_img, null);
            ImageView img_goods = (ImageView) view.findViewById(R.id.img_goods);

            final String filE_path = clsPicUrl.get(i).getFilE_PATH();
            Glide.with(mContext)
                    .load(filE_path)
                    .placeholder(R.drawable.default100)
                    .into(img_goods);
            img_goods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ShowPhotoView.class);
                    intent.putExtra("url", filE_path);
                    mContext.startActivity(intent);
                }
            });
            lin_image.addView(view);
        }

        debugScroll();
    }
    private class myScrollCallBack implements ObservableScrollView.Callbacks {
        @Override
        public void onScrollChanged(int scrollY) {
//            int scrollViewHight = observable_scrollview_foot.getBottom() - observable_scrollview_foot.getTop();
//            if (scrollY >= contentViewHight - scrollViewHight) {
//                slidedetails.setChildCanScroll(false);
//            } else {
//                slidedetails.setChildCanScroll(true);
//            }
            //上划没有冲突，下滑有冲突
            if(scrollY<=0) {
                slidedetails.setChildCanScroll(false);
            }else {
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
    private void debugScroll() {
        super.onResume();
        View parentView = (View) observable_scrollview_foot.getParent();
        int parentViewHight = parentView.getBottom() - parentView.getTop();
        //解决scrollView内容少不能滑动响应的情况
        scrollViewHight = observable_scrollview_foot.getBottom() - observable_scrollview_foot.getTop();
        View contentView = observable_scrollview_foot.getChildAt(0);
        contentViewHight = contentView.getBottom() - contentView.getTop();
        //解决slidingDetailsLayout嵌套scrollView不能滑动的问题

        if (parentViewHight > scrollViewHight) {
            //如果scrollView中的内容较少就直接设置为不能滑动
            slidedetails.setChildCanScroll(false);
        }
        observable_scrollview_foot.setCallbacks(new myScrollCallBack());

    }
}
