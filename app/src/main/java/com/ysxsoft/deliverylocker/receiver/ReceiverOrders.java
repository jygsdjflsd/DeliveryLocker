package com.ysxsoft.deliverylocker.receiver;

import android.content.Intent;
import android.util.Log;

import com.ysxsoft.deliverylocker.app.MyApplication;

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
