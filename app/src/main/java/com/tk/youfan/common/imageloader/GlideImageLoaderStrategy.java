package com.tk.youfan.common.imageloader;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.tk.youfan.R;

/**
 * 作者：tpkeeper on 2016/10/13 15:02
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {

    @Override
    public void loadImage(ImageLoader imageLoader) {
        Context context = imageLoader.getmContext();
        Fragment fragment = imageLoader.getmFragment();
        if (imageLoader == null || (fragment == null && context == null) || imageLoader.getImageView() == null || imageLoader.getUrl() == null) {
            Log.e("T", "some important params in imageLoader is null");
            return;
        }
        if (context != null) {
            if (imageLoader.getError() == 0) {
                Glide.with(context)
                        .load(imageLoader.getUrl())
                        .placeholder(imageLoader.getPlaceHolder())
                        .animate(R.anim.push_left_in)
                        .into(imageLoader.getImageView());
            } else {
                Glide.with(context)
                        .load(imageLoader.getUrl())
                        .placeholder(imageLoader.getPlaceHolder())
                        .animate(R.anim.push_left_in)
                        .error(imageLoader.getError())
                        .into(imageLoader.getImageView());
            }

        } else {
            if (imageLoader.getError() == 0) {
                Glide.with(fragment)
                        .load(imageLoader.getUrl())
                        .placeholder(imageLoader.getPlaceHolder())
                        .animate(R.anim.push_left_in)
                        .into(imageLoader.getImageView());
            } else {
                Glide.with(fragment)
                        .load(imageLoader.getUrl())
                        .placeholder(imageLoader.getPlaceHolder())
                        .animate(R.anim.push_left_in)
                        .error(imageLoader.getError())
                        .into(imageLoader.getImageView());
            }
        }

    }
}
