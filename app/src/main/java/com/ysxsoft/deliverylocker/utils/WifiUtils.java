package com.ysxsoft.deliverylocker.utils;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 时间： 2018/1/17
 * 内容：打开/关闭热点
 */

public class WifiUtils {

    /**
     * 创建热点
     *
     * @return
     */
    public static boolean creatHotspot(WifiManager wifiManager, String apName, String apPwd) {
        boolean request;
        //开启热点
        if (wifiManager.isWifiEnabled()) {
            //如果wifi处于打开状态，则关闭wifi,
            wifiManager.setWifiEnabled(false);
        }
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = apName;
        config.preSharedKey = apPwd;
        config.hiddenSSID = false;//是否隐藏网络
        config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);//开放系统认证
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        config.status = WifiConfiguration.Status.ENABLED;
        //通过反射调用设置热点
        try {
            Method method = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
            boolean enable = (Boolean) method.invoke(wifiManager, config, true);
            if (enable) {
                request = true;
            } else {
                request = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request = false;
        }
        return request;
    }


    /**
     * 关闭热点,并开启wifi
     */
    public static void closeWifiHotspot(WifiManager wifiManager) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = wifiManager.getClass().getMethod("getWifiApConfiguration");
        method.setAccessible(true);
        WifiConfiguration config = (WifiConfiguration) method.invoke(wifiManager);
        Method method2 = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
        method2.invoke(wifiManager, config, false);
        //开启wifi
        wifiManager.setWifiEnabled(true);
    }
}
