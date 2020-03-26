package com.ysxsoft.deliverylocker.utils;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.facebook.stetho.common.LogUtil;
import com.ysxsoft.deliverylocker.app.MyApplication;

import java.util.List;

public class SystemUtil {

    /**
     * 获取设备imei
     *
     * @return
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})//剔除高版本动态权限警告
    public static String getImei() {
        TelephonyManager tm = (TelephonyManager) MyApplication.getApplication().getSystemService(Service.TELEPHONY_SERVICE);
//        Log.e("imei", "deviceid:"+ tm.getDeviceId()+"\nmei:"+ tm.getMeid()+ "\nimei1:"+ tm.getImei(0)+ "\nimei1:"+ tm.getImei(1));
        return tm.getDeviceId();
//        return tm.getImei(1);//6.0以后对双卡双待手机获取imei号的方法，这里无用
    }


    /**
     *  sim卡ID
     */
    public static String getSimId(){
        TelephonyManager telephonyManager = (TelephonyManager)MyApplication.getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String id = telephonyManager.getSubscriberId();
        Log.e("getSimId", id);
//        TelephonyManager tm = (TelephonyManager) MyApplication.getApplication().getSystemService(Service.TELEPHONY_SERVICE);
//
//        SubscriptionManager mSubscriptionManager = SubscriptionManager.from(mContext);
//        int simNumberCard = mSubscriptionManager.getActiveSubscriptionInfoCount()；//获取当前sim卡数量
        return id;
    }
    /**
     *  sim卡手机号
     */
    public static String getTelNumb(){
        TelephonyManager telephonyManager = (TelephonyManager)MyApplication.getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint({"HardwareIds", "MissingPermission"}) String id = telephonyManager.getLine1Number();
        LogUtil.e("telNumb = "+ id);
        return id;
    }

    /**
     * 获取当前本地apk的版本
     *
     * @param mContext
     * @return
     */
    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }
}
