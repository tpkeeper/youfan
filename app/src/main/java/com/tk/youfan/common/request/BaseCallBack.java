package com.tk.youfan.common.request;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：tpkeeper on 2016/10/14 11:48
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public abstract class BaseCallBack<T> {
    public BaseCallBack() {
    }

    /**
     * 请求成功调用
     * @param response
     * @param t
     */
    public abstract void onSuccess(Response response, T t);

    /**
     * 请求失败调用
     * @param call
     * @param e
     */
    public abstract void onFailure(Call call, Exception e);

//    /**
//     * 请求成功但是有错误的时候调用
//     */
//    abstract void onError();
}
