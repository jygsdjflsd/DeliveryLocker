package com.ysxsoft.deliverylocker.tcp;

import android.os.AsyncTask;
import android.util.Log;

import com.ysxsoft.deliverylocker.utils.SerialPortUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.Executors;

public class ReceiveAsyncTask extends AsyncTask<Void, Integer, Boolean> {


    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            if (SerialPortUtil.getSerialtty().getInputStream() != null) {
                byte[] buffer = new byte[512];
                int len;
                while ( (len = SerialPortUtil.getSerialtty().getInputStream().read(buffer, 0, buffer.length)) > 0){
                    JSONObject object = new JSONObject();
                    object.putOpt("time", System.currentTimeMillis());
                    JSONArray array = new JSONArray();
                    for (int i = 0; i < len; i++) {
                        array.put(buffer[i]);
                    }
                    object.putOpt("order_ary", array);
                    Log.e("socketMain", "receiver ==>"+ object.toString());
                    SocketClient.sendMsg(object.toString());
                }
            } else {
                return false;
            }
        } catch (Throwable e2) {
            e2.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean outputSuccess) {
        if (outputSuccess) {//接受成功

        } else {//接受失败

        }
    }
}
