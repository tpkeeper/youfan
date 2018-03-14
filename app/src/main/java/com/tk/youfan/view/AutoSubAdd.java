package com.tk.youfan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tk.youfan.R;

/**
 * 作者：tpkeeper on 2016/8/31 18:46
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class AutoSubAdd extends LinearLayout {
    Button btn_sub;
    TextView tv_number;
    Button btn_add;
    private int value = 1;
    private int maxValue = 10;
    private int minValue = 1;
    private onAddAndSubClickListener clickListener;

    public void setClickListener(onAddAndSubClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public int getValue() {
        return value;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setValue(int value) {
        this.value = value;
        tv_number.setText(value + "");
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public AutoSubAdd(Context context) {
        this(context, null);
    }

    public AutoSubAdd(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoSubAdd(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = View.inflate(context, R.layout.auto_sub_add, this);
        btn_sub = (Button) findViewById(R.id.btn_sub);
        tv_number = (TextView) findViewById(R.id.tv_number);
        btn_add = (Button) findViewById(R.id.btn_add);
        initListener();
    }

    private void initListener() {
        btn_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addNumber();
                if (clickListener != null) {
                    clickListener.onAddClick(value);
                }
            }
        });
        btn_sub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                subNumber();
                if (clickListener != null) {
                    clickListener.onSubClick(value);
                }
            }
        });
    }

    private void subNumber() {
        if (value > minValue) {
            value--;
            tv_number.setText(value + "");
        }
    }

    private void addNumber() {
        if (value < maxValue) {
            value++;
            tv_number.setText(value + "");
        }
    }

    public interface onAddAndSubClickListener {
        public void onAddClick(int value);

        public void onSubClick(int value);
    }
}
