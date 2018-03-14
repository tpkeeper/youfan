package com.tk.youfan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * 作者：tpkeeper on 2016/10/6 02:33
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class MyScrollView extends ScrollView {
    private float startY;
    private float distanceY;
    private View childView;
    private int childViewLeft;
    private int childViewTop;
    private int childViewRight;
    private int childViewBottom;
    private boolean isFirst = true;
    private int childViewLeftZero;
    private int childViewTopZero;
    private int childViewRightZero;
    private int childViewBottomZero;
    private float interStartX;
    private float interStartY;

    public MyScrollView(Context context) {
        super(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //获取子视图
        int childCount = getChildCount();
        if (childCount >= 0) {
            childView = getChildAt(0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                interStartX = ev.getX();
                interStartY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float interDistanceX = Math.abs(ev.getX() - interStartX);
                float interDistanceY = Math.abs(ev.getY() - interStartY);
                if (interDistanceX < interDistanceY) {
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return intercept;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //在onlayout的时候获取，子控件布局
        getChildViewZeroPosition();
    }

    private void getChildViewZeroPosition() {
        childViewLeftZero = childView.getLeft();
        childViewTopZero = childView.getTop();
        childViewRightZero = childView.getRight();
        childViewBottomZero = childView.getBottom();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //每次完整触摸事件只执行一次，目的是防止子控件消费ev的down事件，导致不能记录down的真实坐标
        if (isFirst) {
            getChildViewPosition();
            startY = ev.getY();
            isFirst = false;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //每次触摸事件中，都会循环执行，实时获得当前y坐标
        float moveY = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //每次触摸事件只执行一次，获取其y坐标
                startY = ev.getY();
                getChildViewPosition();
                break;
            case MotionEvent.ACTION_MOVE:
                //移动的距离
                distanceY = moveY - startY;
                reLayoutChild();
                break;
            case MotionEvent.ACTION_UP:
                isFirst = true;
                doAfterUp();
                break;
        }
//        return super.onTouchEvent(ev);
        return true;
    }

    private void doAfterUp() {
        getChildViewPosition();
        if (childViewTop > childViewTopZero) {
//            下拉恢复
            childView.layout(childViewLeftZero, childViewTopZero, childViewRightZero, childViewBottomZero);
        }
        if (childViewBottom < childViewTopZero + getHeight()) {
//            上拉恢复
            int childViewTempTop = childViewTopZero - (childViewBottomZero - childViewTopZero - getHeight());
            int childViewTempBottom = childViewBottomZero - (childViewBottomZero - childViewTopZero - getHeight());
            childView.layout(childViewLeftZero, childViewTempTop, childViewRightZero, childViewTempBottom);
        }
    }

    private void getChildViewPosition() {
        childViewLeft = childView.getLeft();
        childViewTop = childView.getTop();
        childViewRight = childView.getRight();
        childViewBottom = childView.getBottom();
    }

    /**
     * 重新布局子布局
     */
    private void reLayoutChild() {
        //left top right bottom
        childView.layout(childViewLeft, childViewTop + (int) distanceY, childViewRight, childViewBottom + (int) distanceY);
    }
}
