package com.tk.youfan.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tk.youfan.R;

/**
 * 作者：tpkeeper on 2016/9/30 05:59
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class MyPopupWindow extends PopupWindow {

    private Context mContext;

    private View view;

    public TextView tv_man;
    public TextView tv_women;
    public TextView tv_life;
    LinearLayout lin_pop;

    public MyPopupWindow(Context mContext) {

        this.view = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_layout, null);
        tv_man = (TextView) view.findViewById(R.id.tv_man);
        tv_women = (TextView) view.findViewById(R.id.tv_women);
        tv_life = (TextView) view.findViewById(R.id.tv_life);
        lin_pop = (LinearLayout) view.findViewById(R.id.lin_pop);
//        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
//        btn_sure= (Button) view.findViewById(R.id.btn_sure);
//        // 取消按钮
//        btn_cancel.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                // 销毁弹出框
//                dismiss();
//            }
//        });
//        btn_sure.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                // 销毁弹出框
//                dismiss();
//            }
//        });
        // 设置按钮监听
        tv_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onItemClickListener!=null) {
                    onItemClickListener.onClick(v,0);
                }
            }
        });
        tv_women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null) {
                    onItemClickListener.onClick(v,1);
                }
            }
        });

        tv_life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null) {
                    onItemClickListener.onClick(v,2);
                }
            }
        });

        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.lin_pop).getBottom();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > height) {
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
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.pop_home);
    }

    //点击事件接口
    public interface OnItemClickListener {
        void onClick(View view,int index);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

