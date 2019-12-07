package com.ysxsoft.deliverylocker.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.x6.serial.SerialPort;
import com.ysxsoft.deliverylocker.app.MyApplication;
import com.ysxsoft.deliverylocker.tcp.ReceiveAsyncTask;
import com.ysxsoft.deliverylocker.tcp.SendAsyncTask;
import com.ysxsoft.deliverylocker.receiver.ReceiverOrders;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * 串口工具类
 */
public class SerialPortUtil {

    private static final String TAG = "SerialPortUtil";
    private static final String devPath = "/dev/ttyS3";
    private static final int baudrate = 9600;
    private static SerialPort serialtty;

    private static List<String> orderList = new ArrayList<>();//消息队列
    private static final int MSG_DOING = 101;//
    @SuppressLint("HandlerLeak")
    private static Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_DOING){
                if (orderList.size() > 0){
                    parseData(orderList.get(0));
                    orderList.remove(0);
                    if (!mHandler.hasMessages(MSG_DOING) && orderList.size() > 0)
                        mHandler.sendEmptyMessageDelayed(MSG_DOING, 1000);
                }
            }
        }
    };

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
     * 添加到消息队列
     * @param result
     */
    public static void addOrderList(String result){
        orderList.add(result);//将消息添加到队列
        if (!mHandler.hasMessages(MSG_DOING)){
            //开始执行消息队列
            mHandler.sendEmptyMessageDelayed(MSG_DOING, 1000);
        }
    }
    /**
     * 执行远程命令
     * @param result
     */
    public static void parseData(String result) {
        Log.e(TAG, "parseData");
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
//                    ReceiverOrders.jsmethod_startAP(object);
                    ReceiverOrders.openHot(object);
                    break;
                case "stc:ap_off"://关闭热点
//                    ReceiverOrders.jsmethod_closeAP(object);
                    ReceiverOrders.closeHot(object);
                    break;
                case "stc:hide_navigation"://隐藏菜单
                    ReceiverOrders.hideNavigation();
                    break;
                case "stc:show_navigation"://显示菜单
                    ReceiverOrders.showNavigation();
                    break;
                /*串口命令*/
                case "stc:opendoor"://开门需要播放提示音：XX号门已开，请随手关门
                    String door_numb = object.optString("door_number");
                    TtsUtil.getInstance().speak(String.format("%s号门已开, 请随手关门", door_numb));
                case "stc:open_all"://打开全部柜门
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
        new SendAsyncTask().executeOnExecutor(Executors.newCachedThreadPool(), object);
    }

    /* 打开串口 开始读操作*/
    public static void init_receive_serial() {
        new ReceiveAsyncTask().executeOnExecutor(Executors.newCachedThreadPool());
    }


}
