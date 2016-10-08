package com.tk.youfan;

import android.app.Application;
import android.os.Build;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tk.youfan.utils.LogUtil;
import com.tk.youfan.utils.loadingandretry.LoadingAndRetryManager;

/**
 * 作者：tpkeeper on 2016/9/28 09:12
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：初始化数据
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化讯飞语音
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=57f62703");
        //初始化LoadingAndRetryManager
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.base_retry;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.base_loading;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_empty;
    }
}
