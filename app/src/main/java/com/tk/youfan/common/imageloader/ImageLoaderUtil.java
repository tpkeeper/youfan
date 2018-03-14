package com.tk.youfan.common.imageloader;
/**
 * 作者：tpkeeper on 2016/10/13 14:49
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：image load 统一入口
 */
public class ImageLoaderUtil {
    private static  ImageLoaderUtil mInstance;
    private BaseImageLoaderStrategy mBaseImageLoaderStrategy;
    private ImageLoaderUtil() {
        mBaseImageLoaderStrategy = new GlideImageLoaderStrategy();
    }
    public static ImageLoaderUtil getInstance(){
        if(mInstance == null) {
            mInstance = new ImageLoaderUtil();
        }
        return mInstance;
    }
    public void loadImage(ImageLoader imageLoader){
        mBaseImageLoaderStrategy.loadImage(imageLoader);
    }
    public void setStrategy(BaseImageLoaderStrategy baseImageLoaderStrategy){
        this.mBaseImageLoaderStrategy = baseImageLoaderStrategy;
    }
}
