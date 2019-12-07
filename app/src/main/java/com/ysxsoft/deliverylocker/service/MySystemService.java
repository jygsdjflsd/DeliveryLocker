package com.ysxsoft.deliverylocker.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ysxsoft.deliverylocker.utils.DateTimeUtil;
import com.ysxsoft.deliverylocker.utils.DateUtil;
import com.ysxsoft.deliverylocker.utils.SystemLumUtil;
import com.ysxsoft.deliverylocker.utils.SystemUtil;

/***
 *  系统服务 调整亮度
 */
public class MySystemService extends Service {


    private Handler mHandler;
    private Runnable systemRunnable;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
        systemRunnable = () -> {
            //判断当前时间
            if (DateUtil.isDayTime()){//白天
                SystemLumUtil.saveScreenBrightness(255);
                long endTime = DateUtil.getUnDayTime()- System.currentTimeMillis();
                mHandler.postDelayed(systemRunnable, endTime);
                Log.e("MySystemService", "白天");
            }else {
                SystemLumUtil.saveScreenBrightness(255*4/10);
                if (DateUtil.getDayTime() - System.currentTimeMillis()> 0 ){
                    //前半夜 凌晨12点之后，白天之前
                    long startime = DateUtil.getDayTime()- System.currentTimeMillis();
                    mHandler.postDelayed(systemRunnable, startime);
                }else if (System.currentTimeMillis() - DateUtil.getUnDayTime() > 0){
                    //后半夜 晚上之后，凌晨12点之前
                    long startime = DateUtil.getTomorrowDayTime()- System.currentTimeMillis();
                    mHandler.postDelayed(systemRunnable, startime);
                }
                Log.e("MySystemService", "夜里");
            }
        };
        mHandler.post(systemRunnable);
    }



}
