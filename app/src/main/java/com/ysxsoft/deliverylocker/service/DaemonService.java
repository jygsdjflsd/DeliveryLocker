package com.ysxsoft.deliverylocker.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

public class DaemonService extends Service {

    private static final String TAG = "DaemonService";

    public DaemonService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate");
//        if (!isRunningTaskExist(this,android.os.Process.myPid())){
//        isLancherActExist();
        Intent newIntent = this.getPackageManager()
                .getLaunchIntentForPackage("com.ysxsoft.deliverylocker");
        this.startActivity(newIntent);
        Log.e(TAG,"start launch Activity");
//        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public boolean isRunningTaskExist(Context context, int pid){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> lists ;
        if (am != null) {
            lists = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo appProcess : lists) {
                if (appProcess.pid == pid) {
                    Log.e(TAG,"isRunningTaskExist true");
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isLancherActExist(){
        Intent intent = this.getPackageManager()
                .getLaunchIntentForPackage("com.ysxsoft.deliverylocker");

        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
        if (list.size() > 0) {
            // 说明系统中不存在这个activity
            Log.e(TAG,"isLancherActExist true");
//            return true;
        }
        if (getPackageManager().resolveActivity(intent, 0) == null) {

            Log.e(TAG,"getPackageManager().resolveActivity null");
        }
        if(intent.resolveActivity(getPackageManager()) == null) {

            Log.e(TAG,"intent.resolveActivity null");
        }
        Log.e(TAG,"isLancherActExist false");
        return false;
    }


}
