package com.ysxsoft.deliverylocker.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ysxsoft.deliverylocker.ui.activity.ScreenActivity;

public class RCVRemountApk extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent2 = new Intent(context, ScreenActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent2);
    }
}
