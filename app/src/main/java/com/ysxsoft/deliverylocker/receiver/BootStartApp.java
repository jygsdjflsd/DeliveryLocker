package com.ysxsoft.deliverylocker.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.ysxsoft.deliverylocker.ui.activity.MainActivity;
import com.ysxsoft.deliverylocker.utils.ToastUtils;

/**
 * 开机自启动广播
 */
public class BootStartApp extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("receiver", "开机了！！启动APP了！！");
        ToastUtils.show("开机了！！启动APP了！！");
        String action = intent.getAction();
        if (TextUtils.equals(Intent.ACTION_BOOT_COMPLETED, action)) {
            //启动APP某个Activity即可，或者主activity，也可是其他组件等
            Intent intent1 = new Intent(context, MainActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }
}
