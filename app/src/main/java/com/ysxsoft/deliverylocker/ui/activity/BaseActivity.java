package com.ysxsoft.deliverylocker.ui.activity;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ysxsoft.deliverylocker.utils.ToastUtils;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mContext = this;
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();

    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    /**
     * 吐司
     * @param tost
     */
    protected void toast(String tost){
        ToastUtils.show(tost);
    }

    /**
     * 显示状态栏
     */
    protected void showStatusBar() {
        // 显示状态栏
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 隐藏状态栏
     */
    protected void hideStatusBar() {
        // 隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 获取设备imei
     *
     * @return
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})//剔除高版本动态权限警告
    protected String getImei() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);
//        Log.e("imei", "deviceid:"+ tm.getDeviceId()+"\nmei:"+ tm.getMeid()+ "\nimei1:"+ tm.getImei(0)+ "\nimei1:"+ tm.getImei(1));
        return tm.getDeviceId();
//        return tm.getImei(1);//6.0以后对双卡双待手机获取imei号的方法，这里无用
    }



}
