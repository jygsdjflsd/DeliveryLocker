package com.ysxsoft.deliverylocker.utils;

import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import okhttp3.Dns;

/**
 * Create By 胡
 * on 2019/11/8 0008
 */
public class SocketClient {

    private static Socket socket;
    private static InetAddress address;

    public static void socketMain(String host, int port) {
        Log.e("socketMain", "socketMain++++++++++++");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("run", "run>>>>>>");
                try {
                    address = InetAddress.getByName(host);
                    //和服务器创建连接
                    socket = new Socket(address.getHostAddress().toString(), port);
                    //发送给服务器的信息
                    OutputStream os = socket.getOutputStream();
                    PrintWriter pw = new PrintWriter(os);
                    pw.write("fsad");
                    Log.e("socket", "socket=====");
                    pw.flush();
                    socket.shutdownOutput();
                    //从服务器获取信息
                    InputStream is = socket.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    Log.e("br", "br=====" + br.readLine());
                    String info = null;
                    while ((info = br.readLine()) != null) {
                        System.out.println("我是客户端，服务器返回信息：" + info);
                        Log.e("info", "info=====" + info);
                    }
                    br.close();
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
     * 断开连接
     */
    public static void socketClose() {
        try {
            Log.e("socket", "socket=====断开");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
