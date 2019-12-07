package com.ysxsoft.deliverylocker.receiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.ysxsoft.deliverylocker.bus.SimCardBus;

import org.greenrobot.eventbus.EventBus;

public class SimStateReceiver extends BroadcastReceiver {

    public final String ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("SimStateReceiver", "收到广播：action =>"+ intent.getAction());
        if (ACTION_SIM_STATE_CHANGED.equals(intent.getAction())) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            int state = tm.getSimState();
            switch (state) {
                case TelephonyManager.SIM_STATE_READY ://有效
                    Log.e("SimStateReceiver", "有效");
                    EventBus.getDefault().post(new SimCardBus(true));
                    break;
                case TelephonyManager.SIM_STATE_UNKNOWN :
                case TelephonyManager.SIM_STATE_ABSENT :
                case TelephonyManager.SIM_STATE_PIN_REQUIRED :
                case TelephonyManager.SIM_STATE_PUK_REQUIRED :
                case TelephonyManager.SIM_STATE_NETWORK_LOCKED :
                default://都是无效的
                    Log.e("SimStateReceiver", "无效");
                    EventBus.getDefault().post(new SimCardBus(false));
                    break;
            }
        }
    }
}
