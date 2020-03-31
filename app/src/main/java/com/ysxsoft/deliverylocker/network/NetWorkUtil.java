package com.ysxsoft.deliverylocker.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

public class NetWorkUtil {


    public static void getPhoneState(Context context, NetWorkListener listener) {
        final TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        PhoneStateListener MyPhoneListener = new PhoneStateListener() {
            @Override
            //获取对应网络的ID，这个方法在这个程序中没什么用处
            public void onCellLocationChanged(CellLocation location) {
                if (location instanceof GsmCellLocation) {
                    int CID = ((GsmCellLocation) location).getCid();
                } else if (location instanceof CdmaCellLocation) {
                    int ID = ((CdmaCellLocation) location).getBaseStationId();
                }
                Log.i("MyPhoneListener", "onCellLocationChanged");
            }

            //系统自带的服务监听器，实时监听网络状态
            @Override
            public void onServiceStateChanged(ServiceState serviceState) {
                super.onServiceStateChanged(serviceState);
                Log.i("MyPhoneListener", "onServiceStateChanged");
            }

            //这个是我们的主角，就是获取对应网络信号强度
            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                super.onSignalStrengthsChanged(signalStrength);
                Log.i("MyPhoneListener", "onSignalStrengthsChanged");
                //这个ltedbm 是4G信号的值
                String signalinfo = signalStrength.toString();
                String[] parts = signalinfo.split(" ");
                int ltedbm = Integer.parseInt(parts[9]);
                //这个dbm 是2G和3G信号的值
                int asu = signalStrength.getGsmSignalStrength();
                int dbm = -113 + 2 * asu;


                int nType = telephonyManager.getNetworkType();
                //获取手机所有连接管理对象
                ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                //获取NetworkInfo对象
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                //NetworkInfo对象为空 则代表没有网络
                if (networkInfo == null) {
                    listener.change(dbm, "无网络");
                    return;
                }
                if (nType == ConnectivityManager.TYPE_WIFI) {
                    //WIFI
                    listener.change(dbm, "wifi");
                } else if (nType == TelephonyManager.NETWORK_TYPE_LTE
                        && !telephonyManager.isNetworkRoaming()) {
                    listener.change(dbm, "4G");
                } else if (nType == TelephonyManager.NETWORK_TYPE_UMTS
                        || nType == TelephonyManager.NETWORK_TYPE_HSDPA
                        || nType == TelephonyManager.NETWORK_TYPE_EVDO_0
                        && !telephonyManager.isNetworkRoaming()) {
                    listener.change(dbm, "3G");
                    //2G 移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
                } else if (nType == TelephonyManager.NETWORK_TYPE_GPRS
                        || nType == TelephonyManager.NETWORK_TYPE_EDGE
                        || nType == TelephonyManager.NETWORK_TYPE_CDMA
                        && !telephonyManager.isNetworkRoaming()) {
                    listener.change(dbm, "2G");
                }else if (nType == 0){
                    listener.change(dbm, "有线网络");
                }
            }
        };
        telephonyManager.listen(MyPhoneListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }

    public interface NetWorkListener {
        void change(int size, String type);
    }
}
