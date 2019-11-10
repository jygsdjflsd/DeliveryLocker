package com.ysxsoft.deliverylocker;

import android.os.AsyncTask;

import com.ysxsoft.deliverylocker.utils.SerialPortUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ReceiveAsyncTask extends AsyncTask<Void, Integer, Boolean> {


    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            if (SerialPortUtil.getSerialtty().getInputStream() != null) {
                byte[] buffer = new byte[512];
                int size = SerialPortUtil.getSerialtty().getInputStream().read(buffer);
                if (size > 0) {
                    JSONArray retArray = new JSONArray();
                    for (int i = 0; i < size; i++) {
                        retArray.put(buffer[i]);
                    }
                    JSONObject ret = new JSONObject();
                    ret.putOpt("ret_array", retArray);
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
