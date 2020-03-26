package com.ysxsoft.deliverylocker.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;


import com.ysxsoft.deliverylocker.app.MyApplication;

import java.lang.reflect.Method;
import java.math.BigDecimal;

public class ScreenUtils {


    /**
     * @ 获取当前手机屏幕的尺寸(单位:像素)
     */
    public static float getPingMuInch(Context mContext) {
        int densityDpi = mContext.getResources().getDisplayMetrics().densityDpi;
        float scaledDensity = mContext.getResources().getDisplayMetrics().scaledDensity;
        float density = mContext.getResources().getDisplayMetrics().density;
        float xdpi = mContext.getResources().getDisplayMetrics().xdpi;
        float ydpi = mContext.getResources().getDisplayMetrics().ydpi;
        int width = mContext.getResources().getDisplayMetrics().widthPixels;
        int height = mContext.getResources().getDisplayMetrics().heightPixels;

        // 这样可以计算屏幕的物理尺寸
        float width2 = (width / xdpi)*(width / xdpi);
        float height2 = (height / ydpi)*(width / xdpi);

        return (float) Math.sqrt(width2+height2);
    }

    /**
     * 获取视频分辨率
     * @return
     */
    public static int[] getDistinguishability(){
        int[] dis = new int[2];
        DisplayMetrics dm2 = MyApplication.getApplication().getResources().getDisplayMetrics();
        dis[0] = dm2.widthPixels;
        dis[1] = dm2.heightPixels;
        return dis;
    }

    /**
     * 获取屏幕的大小像素
     * @param appCompatActivity
     * @return
     */
    public static int[] getPingMuPx(AppCompatActivity appCompatActivity){
        int[] size = new int[2];
        // 通过Activity类中的getWindowManager()方法获取窗口管理，再调用getDefaultDisplay()方法获取获取Display对象
        Display display = appCompatActivity.getWindowManager().getDefaultDisplay();
        // 方法一(推荐使用)使用Point来保存屏幕宽、高两个数据
        Point outSize = new Point();
        // 通过Display对象获取屏幕宽、高数据并保存到Point对象中
        display.getSize(outSize);
        // 从Point对象中获取宽、高
        int x = outSize.x;
        int y = outSize.y;
        // 通过吐司显示屏幕宽、高数据522
        size[0] = x;
        size[1] = y;
        return size;
    }


    /**
     * 获取屏幕宽高及尺寸的方法
     */
    public static void getPingMuWidthHeight(AppCompatActivity appCompatActivity){
        // 通过WindowManager获取
        DisplayMetrics dm = new DisplayMetrics();
        appCompatActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        System.out.println("width-display :" + dm.widthPixels);
        System.out.println("heigth-display :" + dm.heightPixels);

        // 通过Resources获取
        DisplayMetrics dm2 = appCompatActivity.getResources().getDisplayMetrics();
        System.out.println("width-display :" + dm2.widthPixels);
        System.out.println("heigth-display :" + dm2.heightPixels);

        // 获取屏幕的默认分辨率
        Display display = appCompatActivity.getWindowManager().getDefaultDisplay();
        System.out.println("width-display :" + display.getWidth());
        System.out.println("heigth-display :" + display.getHeight());
    }

    /**
     * 获取屏幕比率
     * @param appCompatActivity
     * @return
     */
    public static float getScreenRatio(AppCompatActivity appCompatActivity){
        // 通过WindowManager获取
        DisplayMetrics dm = new DisplayMetrics();
        appCompatActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return (float) dm.heightPixels/dm.widthPixels;
    }

    /**
     * 获取屏幕尺寸
     * @param ctx
     * @return
     */
    public static double getScreenPhysicalSize(Activity ctx) {
        DisplayMetrics dm = new DisplayMetrics();
        ctx.getWindowManager().getDefaultDisplay().getMetrics(dm);
        double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels, 2) + Math.pow(dm.heightPixels, 2));
        return diagonalPixels / (160 * dm.density);
    }

    /**
     *  获取屏幕尺寸
     * @param appCompatActivity
     */
    private static float mInch = 0;
    public static float getScreenInch(AppCompatActivity appCompatActivity){
        if (mInch != 0.0d) {
            return mInch;
        }

        try {
            int realWidth = 0, realHeight = 0;
            Display display = appCompatActivity.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                Point size = new Point();
                display.getRealSize(size);
                realWidth = size.x;
                realHeight = size.y;
            } else if (android.os.Build.VERSION.SDK_INT < 17
                    && android.os.Build.VERSION.SDK_INT >= 14) {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                realWidth = (Integer) mGetRawW.invoke(display);
                realHeight = (Integer) mGetRawH.invoke(display);
            } else {
                realWidth = metrics.widthPixels;
                realHeight = metrics.heightPixels;
            }

            mInch =formatFloat(Math.sqrt((realWidth/metrics.xdpi) * (realWidth /metrics.xdpi) + (realHeight/metrics.ydpi) * (realHeight / metrics.ydpi)),1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mInch;
    }
    /**
     * Double类型保留指定位数的小数，返回double类型（四舍五入）
     * newScale 为指定的位数
     */
    private static float formatFloat(double d,int newScale) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(newScale, BigDecimal.ROUND_HALF_UP).floatValue();
    }



}
