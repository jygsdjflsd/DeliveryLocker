package com.ysxsoft.deliverylocker.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.ysxsoft.deliverylocker.R;
import com.ysxsoft.deliverylocker.app.MyApplication;
import com.ysxsoft.deliverylocker.bean.DeviceBean;
import com.ysxsoft.deliverylocker.bean.DeviceInfo;
import com.ysxsoft.deliverylocker.network.NetWorkUtil;
import com.ysxsoft.deliverylocker.receiver.ReceiverOrders;
import com.ysxsoft.deliverylocker.service.TimerService;
import com.ysxsoft.deliverylocker.tcp.SocketClient;
import com.ysxsoft.deliverylocker.ui.adapter.FgvgAdapter;
import com.ysxsoft.deliverylocker.ui.fragment.Tab1Fragment;
import com.ysxsoft.deliverylocker.ui.fragment.Tab2Fragment;
import com.ysxsoft.deliverylocker.utils.GlideImageLoader;
import com.ysxsoft.deliverylocker.utils.glide.GlideUtils;
import com.ysxsoft.deliverylocker.widget.CustomViewPager;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    public static void newIntent() {
        Intent intent = new Intent(MyApplication.getApplication(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getApplication().startActivity(intent);
    }

    @BindView(R.id.parent)
    ConstraintLayout parent;
    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @BindView(R.id.tvNetWork)
    TextView tvNetWork;
    @BindView(R.id.tvTop)
    TextView tvTop;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;
    @BindView(R.id.layoutTop)
    ConstraintLayout layoutTop;
    @BindView(R.id.bannerFlow)
    Banner bannerFlow;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;

    private int touchTimer;//页面切换计数

    private Handler mHandler;//handler计数器
    private Runnable runnable;

    private TimerService timerService;//心跳服务
    public ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            timerService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            timerService = ((TimerService.MyBinder)service).getService();
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {
        NetWorkUtil.getPhoneState(this, size -> tvNetWork.setText(String.format("4G/%s", size)));

        tvTop.setText(String.format("%s%s\u3000客服电话：%s", DeviceInfo.getIntence().getProperty(), DeviceInfo.getIntence().getTag(), DeviceInfo.getIntence().getService_tel()));
        GlideUtils.setBackgroud(ivLogo, DeviceInfo.getIntence().getLogo());
        initBanner(DeviceInfo.getIntence().getDeviceBean().getResult().getAds());
        initFragment();
        initTouchTimer();

        SocketClient.socketMain(DeviceInfo.getIntence().register_key());
        ReceiverOrders.openDog();//打开看门狗
        bindService(new Intent(mContext, TimerService.class), conn, Context.BIND_AUTO_CREATE);//绑定启动服务
    }

    private void initFragment() {
        viewPager.setScanScroll(false);//不支持滑动
        viewPager.setOffscreenPageLimit(3);
        List<Fragment> list = new ArrayList<>();
        list.add(Tab1Fragment.newInstance(0, DeviceInfo.getIntence().register_key()));
        list.add(Tab2Fragment.newInstance(1, DeviceInfo.getIntence().register_key()));
        list.add(Tab1Fragment.newInstance(2, String.valueOf(DeviceInfo.getIntence().getCompany_id())));
        FgvgAdapter fgAdapter = new FgvgAdapter(getSupportFragmentManager(), 0, list);
        viewPager.setAdapter(fgAdapter);
    }

    /**
     * 初始化页面切换计数器
     */
    private void initTouchTimer() {
        mHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                //TODO  计数器
                Log.e("runnable", "开始:" + touchTimer);
                touchTimer++;
                if (touchTimer == 20) {//
                    viewPager.setCurrentItem(0);
                    clearBtnBack();
                    btn1.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
                    btn1.setBackgroundResource(R.color.colorMaster);
                    mHandler.removeCallbacks(runnable);
                    Log.e("runnable", "结束");
                    return;
                }
                mHandler.postDelayed(this, 1000);
            }
        };
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
    public void onViewClicked(View view) {
        clearBtnBack();
        switch (view.getId()) {
            case R.id.btn1://二维码取件
                btn1.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
                btn1.setBackgroundResource(R.color.colorMaster);
                viewPager.setCurrentItem(0);
                mHandler.removeCallbacks(runnable);
                break;
            case R.id.btn2://取件码取件
                btn2.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
                btn2.setBackgroundResource(R.color.colorMaster);
                viewPager.setCurrentItem(1);
                touchTimer = 0;
                mHandler.removeCallbacks(runnable);
                mHandler.postDelayed(runnable, 1000);
                break;
            case R.id.btn3://投递员投件
                btn3.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
                btn3.setBackgroundResource(R.color.colorMaster);
                viewPager.setCurrentItem(2);
                touchTimer = 0;
                mHandler.removeCallbacks(runnable);
                mHandler.postDelayed(runnable, 1000);
                break;
        }
    }

    private void clearBtnBack() {
        btn1.setTextColor(ContextCompat.getColor(mContext, R.color.color282828));
        btn2.setTextColor(ContextCompat.getColor(mContext, R.color.color282828));
        btn3.setTextColor(ContextCompat.getColor(mContext, R.color.color282828));
        btn1.setBackgroundResource(R.color.colorF5F5F5);
        btn2.setBackgroundResource(R.color.colorF5F5F5);
        btn3.setBackgroundResource(R.color.colorF5F5F5);
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        bannerFlow.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        bannerFlow.stopAutoPlay();
    }

    private void initBanner(List<DeviceBean.ResultBean.AdsBean> bannerList) {
        List<String> images = new ArrayList<>();
        for (DeviceBean.ResultBean.AdsBean adsBean : bannerList) {
            images.add(adsBean.getUrl());
        }
        bannerFlow.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        bannerFlow.setImageLoader(new GlideImageLoader());
        bannerFlow.setImages(images);
        bannerFlow.setBannerAnimation(Transformer.Accordion);
        bannerFlow.isAutoPlay(true);
        bannerFlow.setDelayTime(2000);
        bannerFlow.setIndicatorGravity(BannerConfig.CENTER);
        bannerFlow.setOnBannerListener(position -> {//点击事件
        });
        bannerFlow.start();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            touchTimer = 0;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null)
            mHandler.removeCallbacks(runnable);
        unbindService(conn);
    }
}
