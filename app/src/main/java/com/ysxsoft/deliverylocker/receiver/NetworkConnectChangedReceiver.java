package com.ysxsoft.deliverylocker.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 监听网络变化的app
 */
public class NetworkConnectChangedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("receiver", "网络连接变化");
//            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
//                NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//                if (!mobNetInfo.isConnected()) {
//                    //移动网络未连接
////                        ReceiverOrders.restartSystem();
//                    EventBus.getDefault().post(new NetWorkBus("4G", false));
//                    Log.e("receiver", "网络未链接");
//                }else {
//                    //移动网络连接
//                    EventBus.getDefault().post(new NetWorkBus("4G", true));
//                    Log.e("receiver", "网络重新链接");
//                }
//            }
    }


//        NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
}
