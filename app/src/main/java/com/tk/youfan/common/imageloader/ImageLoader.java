package com.tk.youfan.common.imageloader;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tk.youfan.R;

/**
 * 作者：tpkeeper on 2016/10/13 14:56
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：image Load 的参数配置对象
 */
public class ImageLoader {
    private Context mContext;
    private ImageView imageView;
    private String url;
    private int placeHolder;
    private int error;
    private int wifiStrategy;
    private Fragment mFragment;


    public String getUrl() {
        return url;
    }

    public Fragment getmFragment() {
        return mFragment;
    }


    public int getError() {
        return error;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Context getmContext() {
        return mContext;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public int getWifiStrategy() {
        return wifiStrategy;
    }

    private ImageLoader(Builder builder) {
        this.mContext = builder.mContext;
        this.imageView = builder.imageView;
        this.url = builder.url;
        this.error = builder.error;
        this.mFragment = builder.mFragment;
        this.placeHolder = builder.placeHolder;
        this.wifiStrategy = builder.wifiStrategy;
    }


    public static class Builder {
        private Context mContext;
        private String url;
        private Fragment mFragment;
        private ImageView imageView;
        private int placeHolder;
        private int error;
        private int wifiStrategy;
        public int defaultPlaceHolder = R.drawable.pic_loading;

        public Builder() {
            this.mContext = null;
            this.mFragment = null;
            this.imageView = null;
            this.url=null;
            this.placeHolder = defaultPlaceHolder;
            this.error = 0;
            this.wifiStrategy = 0;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setError(int error) {
            this.error = error;
            return this;
        }

        public Builder setImageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder with(Context mContext) {
            this.mContext = mContext;
            return this;
        }

        public Builder with(Fragment fragment) {
            this.mFragment = fragment;
            return this;
        }


        public Builder setPlaceHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder setWifiStrategy(int wifiStrategy) {
            this.wifiStrategy = wifiStrategy;
            return this;
        }

        public ImageLoader build() {
            return new ImageLoader(this);
        }
    }
}
