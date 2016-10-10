package com.tk.youfan.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.tk.youfan.R;


import uk.co.senab.photoview.PhotoViewAttacher;

public class ShowPhotoView extends Activity {
//    PhotoView photo_view;
    ImageView img_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_photo_view);
        String url = getIntent().getStringExtra("url");
        img_view = (ImageView)findViewById(R.id.img_view);
//        photo_view = (PhotoView)findViewById(R.id.photo_view);
        final PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(img_view);
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()//淡入淡出
                .into(new GlideDrawableImageViewTarget(img_view){
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        photoViewAttacher.update();
                    }
                });
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
//                        return false;
//                    }
//                });

//        Picasso.with(this)
//                .load(url)
//                .into(photo_view, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        photoViewAttacher.update();
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                });


    }
}
