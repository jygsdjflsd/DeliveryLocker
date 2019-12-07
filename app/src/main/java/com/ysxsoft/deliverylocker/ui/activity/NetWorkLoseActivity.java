package com.ysxsoft.deliverylocker.ui.activity;


import android.os.Handler;
import android.util.Log;

import com.lzy.okgo.model.Response;
import com.ysxsoft.deliverylocker.R;
import com.ysxsoft.deliverylocker.api.ApiUtils;
import com.ysxsoft.deliverylocker.bean.DeviceInfo;
import com.ysxsoft.deliverylocker.bus.NetWorkBus;
import com.ysxsoft.deliverylocker.network.AbsPostJsonStringCb;
import com.ysxsoft.deliverylocker.receiver.ReceiverOrders;
import com.ysxsoft.deliverylocker.tcp.SocketClient;

import org.greenrobot.eventbus.EventBus;


/**
 * 断网重连页面
 */
public class NetWorkLoseActivity extends BaseActivity {

    private static final String TAG = "NetWorkLoseActivity";

    private Handler mHandler;
    private Runnable runnable = ReceiverOrders::restartSystem;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_screen;
    }

    @Override
    protected void initView() {
        //只要进入网络检测页面 就关闭长链接 停止外部网络检测服务 开启当前页面网络检测
        SocketClient.socketClose();
        EventBus.getDefault().post(new NetWorkBus("4G", false));
        mHandler = new Handler();
        mHandler.postDelayed(runnable, 80 * 1000);
        tcpOnLine();//检测网络
    }

    /**
     * 检测网络
     */
    private void tcpOnLine(){
        ApiUtils.netOnline("", new AbsPostJsonStringCb() {
            @Override
            public void onSuccess(String str, String data) {
                Log.e(TAG, "onSuccess:str == "+ str);
                EventBus.getDefault().post(new NetWorkBus("4G", true));
                finish();
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Log.e(TAG, "onError:str == "+ response.getException().getMessage());
                new Handler().postDelayed(()-> tcpOnLine(), 5000);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(runnable);
        //重连
        if (SocketClient.isConnectioned())
            SocketClient.socketClose();
        SocketClient.socketMain(DeviceInfo.getIntence().register_key());
    }
}
