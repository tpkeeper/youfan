package com.tk.youfan;

import android.app.Application;
import android.os.Build;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tk.youfan.db.DBManager;
import com.tk.youfan.db.Model;
import com.tk.youfan.utils.LogUtil;
import com.tk.youfan.utils.loadingandretry.LoadingAndRetryManager;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

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

        initOkhttpUtils();
        initDB();

//        U盟
//        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");

    }

    private void initOkhttpUtils() {
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(this));
        OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(10000L, TimeUnit.MILLISECONDS)
                .connectTimeout(10000L,TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    private void initDB() {
        Model.getInstance().init(this,"tk");
    }

}
