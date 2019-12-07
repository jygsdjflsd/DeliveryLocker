package com.ysxsoft.deliverylocker.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PingUtil {


    private static final String PING_IP = "https://iot.modoubox.com/images/arrow_left.png?";//t={{1526464646}}

    /**
     * 判断网络是否可用
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     * <p>需要异步ping，如果ping不通就说明网络不可用</p>
     *
     * @param time 时间戳
     * @return {@code true}: 可用<br>{@code false}: 不可用
     */
    public static boolean isNetworkOnline(String time) {

        Runtime runtime = Runtime.getRuntime();
        Process ipProcess = null;
        try {
            ipProcess = runtime.exec("ping -c 1 www.baidu.com");
//            ipProcess = runtime.exec("ping -c1 " + PING_IP + String.format("t={{%s}}", time));
            InputStream input = ipProcess.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }

            int exitValue = ipProcess.waitFor();
            if (exitValue == 0) {
                //WiFi连接，网络正常
                return true;
            } else {
                return false;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (ipProcess != null) {
                ipProcess.destroy();
            }
            runtime.gc();
        }
        return false;
    }
    /**
     * 判断当前的网络连接状态是否能用
     * return 0可用   其他值不可用
     */
    public static int ping() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process p = runtime.exec("ping -c 1 https://iot.modoubox.com/cabinet/tcp_online_check");
            return p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
