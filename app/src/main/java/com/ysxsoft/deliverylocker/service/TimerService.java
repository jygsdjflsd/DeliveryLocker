package com.ysxsoft.deliverylocker.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ysxsoft.deliverylocker.receiver.ReceiverOrders;
import com.ysxsoft.deliverylocker.tcp.SocketClient;

public class TimerService extends Service {

    private Handler mHandler;
    private Runnable dogRunnable;
    private Runnable soketRunnable;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initHandler();

    }

    private void initHandler(){
        mHandler = new Handler();
        dogRunnable = () -> {
            ReceiverOrders.feedDog();//喂养看门狗
            mHandler.postDelayed(dogRunnable, 10*1000);
        };
        soketRunnable = () -> {
            if (SocketClient.isConnectioned()){
                SocketClient.sendMsg("cabinet_heartbeat");
            }
            mHandler.postDelayed(soketRunnable, 60*1000);
        };
        mHandler.post(dogRunnable);
        mHandler.post(soketRunnable);
    }

    public class MyBinder extends Binder {
        public TimerService getService(){
            return TimerService.this;
        }
    }
}
