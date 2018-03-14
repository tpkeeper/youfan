package com.tk.youfan.view;

import android.content.Context;
import android.util.AttributeSet;

import cn.bleu.widget.slidedetails.SlideDetailsLayout;

/**
 * 作者：tpkeeper on 2016/10/9 15:46
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class MySlideDetails extends SlideDetailsLayout {
    boolean childCanScroll = true;

    public boolean isChildCanScroll() {
        return childCanScroll;
    }

    public void setChildCanScroll(boolean childCanScroll) {
        this.childCanScroll = childCanScroll;
    }

    public MySlideDetails(Context context) {
        super(context);
    }

    public MySlideDetails(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySlideDetails(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean canChildScrollVertically(int direction) {
//        return true;
        if (childCanScroll) {
            return true;
        } else {
            return super.canChildScrollVertically(direction);
        }
    }
}
