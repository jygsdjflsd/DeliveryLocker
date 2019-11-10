package com.ysxsoft.deliverylocker.utils;

import android.util.Log;

import com.example.x6.serial.SerialPort;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ysxsoft.deliverylocker.ReceiveAsyncTask;
import com.ysxsoft.deliverylocker.SendAsyncTask;
import com.ysxsoft.deliverylocker.receiver.ReceiverOrders;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * 串口工具类
 */
public class SerialPortUtil {

    private static final String TAG = "SerialPortUtil";
    private static final String devPath = "/dev/ttyS3";
    private static final int baudrate = 9600;
    private static SerialPort serialtty;

    public static SerialPort getSerialtty() {
        if (serialtty == null) {
            synchronized (SerialPort.class) {
                if (serialtty == null) {
                    try {
                        serialtty = new SerialPort(new File(devPath), baudrate, 0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return serialtty;
    }

    /**
     * 执行远程命令
     * @param result
     */
    public static void parseData(String result) {
        try {
            JSONObject object = new JSONObject(result);
            String type = object.optString("type");
            long time = object.optLong("time", 0);
            if (System.currentTimeMillis() - time > 20*1000){//超出命令时间20秒，不予执行
                return;
            }
            switch (type) {
                /*系统广播*/
                case "stc:restart"://重启系统
                    ReceiverOrders.restartSystem();
                    break;
                case "stc:ap_on"://打开热点
                    ReceiverOrders.jsmethod_startAP(object);
                    break;
                case "stc:ap_off"://关闭热点
                    ReceiverOrders.jsmethod_closeAP(object);
                    break;
                case "stc:hide_navigation"://隐藏菜单
                    ReceiverOrders.hideNavigation();
                    break;
                case "stc:show_navigation"://显示菜单
                    ReceiverOrders.showNavigation();
                    break;
                /*串口命令*/
                case "stc:opendoor"://开门需要播放提示音：XX号门已开，请随手关门
                case "stc:openall"://打开全部柜门
                case "stc:light_on"://打开灯箱
                case "stc:light_off"://关闭灯箱
                case "stc:other"://其它命令（直接把命令下传即可）
                    init_send_serial(object.optJSONObject("order_ary"));
                    break;
                default:
                    Log.e(TAG, "未知命令 type ==>" + type);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "解析失败： json ==>" + result);
        }
    }


    // 串口数据操作 -- 写入 打开串口 开始写操作
    private static void init_send_serial(JSONObject object) {
        new SendAsyncTask().execute(object);
    }

    /* 打开串口 开始读操作*/
    private static void init_receive_serial() {
        new ReceiveAsyncTask().execute();
    }


}
