package com.tk.youfan.common.request;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.tk.youfan.utils.LogUtil;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 作者：tpkeeper on 2016/10/14 08:30
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：对okhttp的进一步封装简化操作
 */
public class TkHttpUtils {
    private static OkHttpClient mOkHttpClient = null;
    private static TkHttpUtils mTkhttpInstance = null;
    private Handler mHandler = null;

    private TkHttpUtils() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * tkhttp 为单例模式 进而控制 okhttpclient 为单例
     *
     * @return
     */
    public static TkHttpUtils getInstance() {
        if (mTkhttpInstance == null) {
            synchronized (TkHttpUtils.class) {
                if (mTkhttpInstance == null) {
                    mTkhttpInstance = new TkHttpUtils();
                }
            }
        }
        return mTkhttpInstance;
    }

    /**
     * 用于发出请求，无论get还是post最终都会调用此方法
     *
     * @param request
     * @param callBack
     */
    public void call(final Request request, final BaseCallBack callBack) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackFailure(call, callBack, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //目前只支持String类型
                    String responseString = response.body().string();
                    Log.e("T", "TkHttpUtils load data successful !");
                    callbackSuccess(response, responseString, callBack);
                } else {
                    callbackFailure(call, callBack, null);
                }
            }
        });
    }

    /**
     * 对请求成功进行处理
     *
     * @param response
     * @param responseString
     * @param callBack
     */
    private void callbackSuccess(final Response response, final String responseString, final BaseCallBack callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //返回响应体
                callBack.onSuccess(response, responseString);
            }
        });
    }

    /**
     * 对请求失败进行处理
     *
     * @param call
     * @param callBack
     * @param e
     */
    private void callbackFailure(final Call call, final BaseCallBack callBack, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //返回请求还有错误原因
                callBack.onFailure(call, e);
            }
        });
    }

    /**
     * 最终暴漏给外界的get请求
     *
     * @param url
     * @param callBack
     */
    public void get(String url, BaseCallBack callBack) {
        call(buildRequest(url, null, HttpMethodType.GET), callBack);
    }

    /**
     * post请求
     *
     * @param url
     * @param params
     * @param callBack
     */
    public void post(String url, Map<String, String> params, BaseCallBack callBack) {
        call(buildRequest(url, params, HttpMethodType.POST), callBack);
    }

    /**
     * 构建请求对象，包括get和post请求对象
     *
     * @param url
     * @param params
     * @param httpMethodType
     * @return
     */
    public Request buildRequest(String url, Map<String, String> params, HttpMethodType httpMethodType) {
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);
        //根据params判断构建get还是post请求
        if (httpMethodType == HttpMethodType.GET) {
            requestBuilder.get();
        } else {
            requestBuilder.post(buildRequestBody(params));
        }
        return requestBuilder.build();
    }

    /**
     * 构建请求体，post请求使用
     *
     * @param params
     * @return
     */
    private RequestBody buildRequestBody(Map<String, String> params) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (params != null) {
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                formBodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        return formBodyBuilder.build();
    }

    /**
     * 请求类型
     */
    enum HttpMethodType {
        GET,
        POST
    }

}
