package com.ysxsoft.deliverylocker.tcp;

import android.net.wifi.p2p.WifiP2pManager;
import android.text.TextUtils;
import android.util.Log;

import com.ysxsoft.deliverylocker.utils.SerialPortUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Dns;

/**
 * Create By 胡
 * on 2019/11/8 0008
 */
public class SocketClient {

    public static final String TCP_HOST = "iot.tcp.modoubox.com";
    public static final int TCP_PORT = 8091;
    private static Socket socket;
    private static InetAddress address;
    private static InputStream is;
    private static OutputStream os;
    private static PrintWriter pw;
    private static BufferedReader br;

    private static String receiverStr = "";

    public static void socketMain(String register_key) {
        Log.e("socketMain", "register_key = " + register_key);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    address = InetAddress.getByName(TCP_HOST);
                    //和服务器创建连接
                    socket = new Socket(address.getHostAddress().toString(), TCP_PORT);
                    socket.setKeepAlive(true);
                    socket.sendUrgentData(0);

                    //发送给服务器的信息
                    os = socket.getOutputStream();
                    pw = new PrintWriter(os);
                    sendMsg(register_key);


                    //从服务器获取信息
                    while (socket.isConnected()) {
                        is = socket.getInputStream();
                        br = new BufferedReader(new InputStreamReader(is));
                        char[] buffer = new char[1024];
                        while (br.read(buffer) > 0) {
                            System.err.println(String.valueOf(buffer));
                            String orderResult = String.valueOf(buffer);
                            orderResult = orderResult.substring(0, orderResult.lastIndexOf("}")+1);
                            Log.e("socketMain", "socketMain=====>>>>" + String.valueOf(buffer));
                            Log.e("socketMain", "orderResult=====>>>>" + orderResult);
                            SerialPortUtil.parseData(orderResult);
                        }
                    }
                    is.close();
                    os.close();
                    pw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 向服务器发送信息
     * @param msg
     */
    public static void sendMsg(String msg){
        if (msg != null && !TextUtils.isEmpty(msg)) {
            pw.write(msg);
        }
        Log.e("socketMain", socket.isConnected() + "");
        pw.flush();
    }

    /**
     * 查看长链接是否链接
     * @return
     */
    public static boolean isConnectioned(){
        return socket != null && socket.isConnected();
    }
    /**
     * 断开连接
     */
    public static void socketClose() {
        try {
            if (socket != null) {
                Log.e("socketMain", "socket=====断开");
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
