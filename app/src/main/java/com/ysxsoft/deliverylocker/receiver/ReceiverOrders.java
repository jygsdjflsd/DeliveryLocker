package com.ysxsoft.deliverylocker.receiver;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.ysxsoft.deliverylocker.app.MyApplication;
import com.ysxsoft.deliverylocker.utils.ToastUtils;
import com.ysxsoft.deliverylocker.utils.WifiUtils;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;

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

    // 开启热点
    public static void jsmethod_startAP(JSONObject moduleContext){
        String apName = moduleContext.optString("ap_name");
        String apPwd = moduleContext.optString("ap_password");
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


    private static void sendBroad(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getApplication().sendBroadcast(intent);
    }
}
