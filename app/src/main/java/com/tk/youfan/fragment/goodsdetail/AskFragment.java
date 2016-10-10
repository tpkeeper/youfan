package com.tk.youfan.fragment.goodsdetail;

import android.view.View;
import android.widget.TextView;

import com.tk.youfan.base.BaseFragment;
import com.tk.youfan.domain.goodsdetail.GoodsDetail;
import com.tk.youfan.view.MySlideDetails;

/**
 * 作者：tpkeeper on 2016/10/9 22:08
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class AskFragment extends BaseFragment {
    GoodsDetail goodsDetail;
    public AskFragment(GoodsDetail goodsDetail, MySlideDetails slidedetails) {
        this.goodsDetail = goodsDetail;
    }

    @Override
    public View initView() {
        TextView textView = new TextView(mContext);
        textView.setText("xxxxxxxxx");
        return textView;
    }
}
