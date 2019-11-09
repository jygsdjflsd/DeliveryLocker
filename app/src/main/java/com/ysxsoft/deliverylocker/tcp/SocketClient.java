package com.ysxsoft.deliverylocker.tcp;

import android.net.wifi.p2p.WifiP2pManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
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

    public static final String TCP_HOST = "iot.tcp.modoubox.com";
    public static final int TCP_PORT = 8091;


    private static Socket socket;
    private static InetAddress address;

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
                    socket.sendUrgentData(60 * 1000);

                    //发送给服务器的信息
                    OutputStream os = socket.getOutputStream();
                    PrintWriter pw = new PrintWriter(os);
                    if (register_key != null && !TextUtils.isEmpty(register_key)) {
                        pw.write(register_key);
                    }
                    Log.e("socketMain", socket.isConnected() + "");
                    pw.flush();
                    socket.shutdownOutput();

                    //从服务器获取信息
                    InputStream inputStream = socket.getInputStream();
                    while (socket.isConnected()) {
                        try {
                            /**得到的是16进制数，需要进行解析*/
                            byte[] bt = new byte[1024];
//                获取接收到的字节和字节数
                            int length = inputStream.read(bt);
                            if (length > 0){
                                //                获取正确的字节
                                byte[] bs = new byte[length];
                                System.arraycopy(bt, 0, bs, 0, length);

                                String str = new String(bs, "UTF-8");
                                Log.e("socketMain", "receiver: " + str);
                            }
                        } catch (IOException e) {
                            Log.e("socketMain", e.toString());
                        }
                    }
//                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
//                    Log.e("socketMain", "br=====" + br.readLine());
//                    String info = null;
//                    while ((info = br.readLine()) != null) {
//                        System.out.println("我是客户端，服务器返回信息：" + info);
//                        Log.e("socketMain", "info=====" + info);
//                    }
//                    DataInputStream input = new DataInputStream(inputStream);
//                    int count = 0;
//                    while (count == 0) {
//                        count = input.available();
//                        Log.e("socketMain", new DataInputStream(socket.getInputStream()).available() + "");
//                    }
//                    byte[] b = new byte[count];
//                    int len = input.read(b);
//                    String result = new String(b, 0, len);
//                    Log.e("socketMain", result);
//                    parseString(result);//解析得到的字符串

//                    br.close();
                    inputStream.close();
                    os.close();
                    pw.close();
//                    socket.close();
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
            Log.e("socketMain", "socket=====断开");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
