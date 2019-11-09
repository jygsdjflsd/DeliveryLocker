package com.ysxsoft.deliverylocker.utils.glide;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ysxsoft.deliverylocker.R;
import com.ysxsoft.deliverylocker.app.MyApplication;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class GlideUtils {

    private static RequestManager glide = Glide.with(MyApplication.getApplication());

    /**
     * 设置背景
     *
     * @param view
     * @param id
     */
    public static void setBackgroud(ImageView view, int id) {
        setBackgroud(view, id, R.mipmap.ic_preview_image);
    }

    public static void setBackgroud(ImageView view, int id, int defaultIcon) {
        glide.load(getDrawableURi(id))
                .apply(new RequestOptions()
                        .placeholder(defaultIcon)
                        .error(defaultIcon))
                .into(view);
    }

    /**
     * 设置背景
     *
     * @param view
     * @param url
     */
    public static void setBackgroud(ImageView view, String url) {
        setBackgroud(view, url, R.mipmap.ic_preview_image);
    }

    public static void setBackgroud(ImageView view, String url, int defaultIcon) {
        glide.load(url)
                .apply(new RequestOptions()
                        .placeholder(defaultIcon)
                        .error(defaultIcon)
                        .dontAnimate()//支持圆形自定义imageview加载
                        .skipMemoryCache(false)//如果内存不足不缓存
                )
                .thumbnail(0.5f)//压缩
                .into(view);
    }

    /**
     * 设置背景为圆
     *
     * @param view
     * @param url
     */
    public static void setBackgroudCircle(ImageView view, Object url) {
        if (url instanceof String) {
            setBackgroudCircle(view, (String) url, -1);
        } else if (url instanceof Integer) {
            setBackgroudCircle(view, (int) url, -1);
        }
    }


    /**
     * 设置背景为圆
     *
     * @param view
     * @param url
     */
    public static void setBackgroudCircle(ImageView view, String url, int def) {
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .error(def != -1 ? def : R.mipmap.ic_preview_image)
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存
        Glide.with(MyApplication.getApplication()).load(url).apply(mRequestOptions).into(view);
    }

    /**
     * 设置背景为圆
     *
     * @param view
     * @param rid
     */
    public static void setBackgroudCircle(ImageView view, int rid, int def) {
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .error(def != -1 ? def : R.mipmap.ic_preview_image)
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存

        Glide.with(MyApplication.getApplication()).load(rid).apply(mRequestOptions).into(view);

    }

    /**
     * 设置背景为圆角
     *
     * @param view
     * @param url
     */
    public static void setBackgroudCircular(ImageView view, String url, int cornersSize) {
        setBackgroudCircular(view, url, cornersSize, R.mipmap.ic_preview_image);
    }

    /**
     * 设置背景为圆角
     *
     * @param view
     * @param url
     */
    public static void setBackgroudCircular(ImageView view, String url, int cornersSize, int errorpic ) {
        //设置图片圆角角度
        GlideRoundCenterCropForm glideRoundTransform = new GlideRoundCenterCropForm(MyApplication.getApplication(), cornersSize);//解决scanType = centerCrop 问题 (动态设置imageview为centerCrop时失效)
        //通过RequestOptions扩展功能
        RequestOptions options = RequestOptions
                .bitmapTransform(glideRoundTransform)
                .error(errorpic)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)//自动磁盘缓存
                .skipMemoryCache(true)//不做内存缓存 缓存到外存
                .override(300, 300);
        Glide.with(MyApplication.getApplication()).load(TextUtils.isEmpty(url) ? R.mipmap.ic_preview_image : url).apply(options).into(view);

    }

    /**
     * 设置背景为圆角
     *
     * @param view
     * @param rid
     */
    public static void setBackgroudCircular(ImageView view, int rid, int cornersSize) {
        //设置图片圆角角度
//        RoundedCorners roundedCorners = new RoundedCorners(DensityUtil.dip2px(MyApplication.getAppContext(), cornersSize));
        GlideRoundCenterCropForm glideRoundTransform = new GlideRoundCenterCropForm(MyApplication.getApplication(), cornersSize);//解决scanType = centerCrop 问题
        //通过RequestOptions扩展功能
        RequestOptions options = RequestOptions
                .bitmapTransform(glideRoundTransform);
        Glide.with(MyApplication.getApplication()).load(getDrawableURi(rid)).apply(options).into(view);

    }

    /**
     * 高斯模糊
     *
     * @param view
     * @param url
     * @param error
     */
    public static void setBackGroundBlur(ImageView view, String url, int error) {
        //加载背景，
        setBackGroundBlur(view, url, error, 14, 3);
    }

    /**
     * 高斯模糊
     *
     * @param view
     * @param id
     * @param error
     */
    public static void setBackGroundBlur(ImageView view, int id, int error) {
        //加载背景，
        setBackGroundBlur(view, id, error, 14, 3);
    }

    /**
     * 高斯模糊
     *
     * @param view
     * @param url
     * @param error
     * @param blur     模糊度
     * @param sampling 图片缩放度
     */
    public static void setBackGroundBlur(ImageView view, String url, int error, int blur, int sampling) {
        //加载背景，
        Glide.with(MyApplication.getApplication())
                .load(url)
                .apply(RequestOptions
                        .bitmapTransform(new BlurTransformation(blur, sampling))// 设置高斯模糊
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .dontAnimate()//支持圆形自定义imageview加载
                        .skipMemoryCache(false)//如果内存不足不缓存
                        .error(error)
                )
                .into(view);
    }

    /**
     * @param view
     * @param id
     * @param error
     * @param flur     模糊度
     * @param sampling 图片缩放度
     */
    public static void setBackGroundBlur(ImageView view, int id, int error, int flur, int sampling) {
        //加载背景，
        Glide.with(MyApplication.getApplication())
                .load(getDrawableURi(id))
                .apply(RequestOptions
                        .bitmapTransform(new BlurTransformation(flur, sampling))// 设置高斯模糊
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .dontAnimate()//支持圆形自定义imageview加载
                        .skipMemoryCache(false)//如果内存不足不缓存
                        .error(error)
                )
                .into(view);
    }

    /**
     * 加载所有view
     *
     * @param viewBack
     * @param url
     */
    public static void setViewBack(View viewBack, String url) {//部分手机崩溃
        Glide.with(MyApplication.getApplication()).load(url)
                .into(new CustomViewTarget<View, Drawable>(viewBack) {
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {

                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition transition) {
                        viewBack.setBackgroundDrawable(resource);
                    }

                    @Override
                    protected void onResourceCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    private static Uri getDrawableURi(int id) {
        return Uri.parse("android.resource://" + MyApplication.getApplication().getPackageName() + "/" + id);
    }
}
