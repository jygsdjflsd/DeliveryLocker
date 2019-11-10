package com.ysxsoft.deliverylocker.utils;


import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;
import com.ysxsoft.deliverylocker.R;
import com.ysxsoft.deliverylocker.utils.glide.GlideUtils;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        GlideUtils.setBackgroud(imageView, String.valueOf(path), R.mipmap.icon_flowpath);
    }

}
