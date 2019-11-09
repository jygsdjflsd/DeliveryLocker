package com.ysxsoft.deliverylocker.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ysxsoft.deliverylocker.R;
import com.ysxsoft.deliverylocker.api.ApiUtils;
import com.ysxsoft.deliverylocker.bean.DeviceBean;
import com.ysxsoft.deliverylocker.bean.DeviceInfo;
import com.ysxsoft.deliverylocker.network.AbsPostJsonStringCb;
import com.ysxsoft.deliverylocker.receiver.ReceiverOrders;
import com.ysxsoft.deliverylocker.service.DaemonService;
import com.ysxsoft.deliverylocker.service.TimerSerVice;
import com.ysxsoft.deliverylocker.tcp.SocketClient;
import com.ysxsoft.deliverylocker.utils.MD5Util;
import com.ysxsoft.deliverylocker.utils.PingUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

public class ScreenActivity extends BaseActivity {

    private final int HANDLER_NETWORK_ON = 101;//网络畅通
    private final int HANDLER_NETWORK_OFF = 102;//网络畅通
    private int pingTime;//重试次数

    @BindView(R.id.tvTop)
    TextView tvTop;
    @BindView(R.id.tvBottom)
    TextView tvBottom;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_NETWORK_ON://网络畅通
                    getService();//获取设备信息
                    break;
                case HANDLER_NETWORK_OFF://网络不通
                    Log.e("runnable", "pingTime = "+ pingTime+ "网络不通，重启");
                    break;
            }
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    if (PingUtil.isNetworkOnline(String.valueOf(System.currentTimeMillis()))) {//网络通
                        mHandler.sendEmptyMessage(HANDLER_NETWORK_ON);
                    } else {//网络不通
                        pingTime++;
                        if (pingTime == 10) {
                            mHandler.sendEmptyMessage(HANDLER_NETWORK_OFF);
                            return;
                        }
                        mHandler.postDelayed(this, 5000);//重新屏
                        Log.e("runnable", "pingTime = "+ pingTime);
                    }
                }
            }.start();
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_screen;
    }

    @Override
    protected void initView() {
        startService(new Intent(mContext, DaemonService.class) );
        ReceiverOrders.openDog();//打开看门狗
        mHandler.post(runnable);//查询网络
        hideNavigation();
    }

    /**
     * 获取设备注册信息
     */
    private void getService() {
        tvTop.setText("请求网络获取设备信息");
        ApiUtils.getFacility(MD5Util.md5Decode32(getImei() + "iot"), new AbsPostJsonStringCb() {
            @Override
            public void onSuccess(String str, String data) {
                try {
                    JSONObject obj = new JSONObject(str);
                    int status = obj.optInt("status");
                    if (status == 0) {//设备未绑定 需要注册
                        JSONObject result = obj.optJSONObject("result");
                        tvTop.setText(String.format("设备激活ID：%s", result.optString("active_code")));
                        tvBottom.setText("请在运营后台添加设备，如有疑问请联系经销商");
                    } else if (status == 1) {//设备不合法
                        startActivity(new Intent(mContext, MainActivity.class));
                        finish();
                    }else if (status == 2){//设备合法 进入取件界面
                        DeviceBean device = new Gson().fromJson(str, DeviceBean.class);
                        DeviceInfo.getIntence().setDeviceBean(device);
                        MainActivity.newIntent();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFinish() {
            }
        });
    }
}
