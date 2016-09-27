package com.tk.youfan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.tk.youfan.utils.ScreenUtils;

public class MainActivity extends SlidingFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initSlidingMenu();
    }

    private void initSlidingMenu() {
        //设置主界面
        setContentView(R.layout.activity_main);
        //左侧菜单
        setBehindContentView(R.layout.left_menu);
        SlidingMenu slidingMenu = getSlidingMenu();
        //左侧模式
        slidingMenu.setMode(SlidingMenu.LEFT);
        //边缘滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        int screenWidth = ScreenUtils.getScreenWidth(this);
        //设置主页宽度
        slidingMenu.setBehindOffset((int)(screenWidth*0.625));
    }
}
