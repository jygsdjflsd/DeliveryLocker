package com.ysxsoft.deliverylocker.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ysxsoft.deliverylocker.receiver.ReceiverOrders;

public class TimerSerVice extends Service {

    private Handler mHandler;
    private Runnable dogRunnable;
    private Runnable soketRunnable;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        initHandler();
        return null;
    }


    private void initHandler(){
        mHandler = new Handler();
        dogRunnable = () -> {
            ReceiverOrders.feedDog();//喂养看门狗
            mHandler.postDelayed(dogRunnable, 10*1000);
            Log.e("TimerSerVice", "喂养看门狗");
        };
        soketRunnable = () -> {
            mHandler.postDelayed(dogRunnable, 10*1000);
            Log.e("TimerSerVice", "查看长链接");
        };
        mHandler.post(dogRunnable);
        mHandler.post(soketRunnable);
    }
}
