package com.ysxsoft.deliverylocker.receiver;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.ysxsoft.deliverylocker.app.MyApplication;
import com.ysxsoft.deliverylocker.utils.ToastUtils;
import com.ysxsoft.deliverylocker.utils.WifiUtils;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReceiverOrders {
    /**
     * 打开看门狗
     */
    public static void openDog() {
        sendBroad("android.intent.action.WATCHDOG_INIT");
    }

    public static void feedDog() {
        sendBroad("android.intent.action.WATCHDOG_KICK");
    }

    public static void closeDog() {
        sendBroad("android.intent.action.WATCHDOG_STOP");
    }

    // 复位4G信号
    public static void restorationMobile() {
        sendBroad("android.intent.action.RESET_MOBILE");
    }

    /**
     * 显示nativebar
     * @return
     */
    public static boolean showNavigation() {
        boolean isshow;
        try {
            String command;
            command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib am startservice -n com.android.systemui/.SystemUIService";
            Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", command});
            proc.waitFor();
            isshow = true;
        } catch (Exception e) {
            isshow = false;
            e.printStackTrace();
        }
        return isshow;
    }
    /**
     * 隐藏nativebar
     *
     * @return
     */
    public static boolean hideNavigation() {
        boolean ishide;
        try {
            String command;
            command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib service call activity 42 s16 com.android.systemui";
            Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", command});
            proc.waitFor();
            ishide = true;
        } catch (Exception ex) {
            ToastUtils.show(ex.getMessage());
            ishide = false;
        }
        return ishide;
    }

    /**
     * 开启热点
     * @param object
     */
    public static void openHot(JSONObject object){
        WifiManager wifiManager = (WifiManager) MyApplication.getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) { //如果wifi打开关闭wifi
            wifiManager.setWifiEnabled(false);
        }
        try {
            WifiConfiguration apConfig = new WifiConfiguration();
            //配置热点的名称
            apConfig.SSID = object.optString("ssid");
            //配置热点的密码(至少8位)
            apConfig.preSharedKey = object.optString("psw");
            apConfig.allowedKeyManagement.set(4);
            //通过反射调用设置热点
            Method method = wifiManager.getClass().getMethod(
                    "setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
            Boolean rs = (Boolean) method.invoke(wifiManager, apConfig, true);//true开启热点 false关闭热点
            Log.d("-------", "开启是否成功:" + rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void closeHot(JSONObject object){
        WifiManager wifiManager = (WifiManager) MyApplication.getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) { //如果wifi打开关闭wifi
            wifiManager.setWifiEnabled(false);
        }
        try {
            WifiConfiguration apConfig = new WifiConfiguration();
            //配置热点的名称
            apConfig.SSID = object.optString("ssid");
            //配置热点的密码(至少8位)
            apConfig.preSharedKey = object.optString("psw");
            apConfig.allowedKeyManagement.set(4);
            //通过反射调用设置热点
            Method method = wifiManager.getClass().getMethod(
                    "setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
            Boolean rs = (Boolean) method.invoke(wifiManager, apConfig, false);//true开启热点 false关闭热点
            Log.d("-------", "开启是否成功:" + rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 开启热点
    public static void jsmethod_startAP(JSONObject moduleContext){
        String apName = moduleContext.optString("ssid");
        String apPwd = moduleContext.optString("psw");
        WifiManager wifiManager = (WifiManager) MyApplication.getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiUtils.creatHotspot(wifiManager,apName,apPwd);
    }
    // 关闭热点
    public static void jsmethod_closeAP(JSONObject moduleContext){
        WifiManager wifiManager = (WifiManager) MyApplication.getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        try {
            WifiUtils.closeWifiHotspot(wifiManager);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // 重启系统
    public static void restartSystem(){
        sendBroad("android.intent.action.MCREBOOT");
    }

    // 复位4G信号
    public static void jsmethod_resetMobile(){
        sendBroad("android.intent.action.RESET_MOBILE");
    }

    private static void sendBroad(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getApplication().sendBroadcast(intent);
    }
}
