package com.ysxsoft.deliverylocker;

import android.os.AsyncTask;
import android.util.Log;

import com.example.x6.serial.SerialPort;
import com.ysxsoft.deliverylocker.utils.SerialPortUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class SendAsyncTask extends AsyncTask<JSONObject, Integer, Boolean> {


    @Override
    protected Boolean doInBackground(JSONObject... jsonObjects) {
        if (jsonObjects.length < 1 || jsonObjects[0] == null) {
            return false;
        }
        try {
            JSONArray jsonArray = jsonObjects[0].optJSONArray("data");
            Log.e("socketMain", "data ==>"+ jsonArray.toString());
            byte[] bData = new byte[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                int item = (int) jsonArray.get(i);
                bData[i] = (byte) item;
            }
            if (SerialPortUtil.getSerialtty() != null){
                Log.e("socketMain", "bData ==>"+ jsonArray.toString());
                SerialPortUtil.getSerialtty().getOutputStream().write(bData);
                return true;
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean outputSuccess) {
        Log.e("socketMain", "onPostExecute ==>"+ outputSuccess);
        if (outputSuccess){//写入成功

        }else {//写入失败

        }
    }
}
