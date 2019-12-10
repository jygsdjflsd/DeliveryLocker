package com.ysxsoft.deliverylocker.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.lzy.okgo.model.Response;
import com.taobao.sophix.SophixManager;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.ysxsoft.deliverylocker.R;
import com.ysxsoft.deliverylocker.api.ApiUtils;
import com.ysxsoft.deliverylocker.app.MyApplication;
import com.ysxsoft.deliverylocker.bean.DeviceBean;
import com.ysxsoft.deliverylocker.bean.DeviceInfo;
import com.ysxsoft.deliverylocker.bus.DeviceRefreshBus;
import com.ysxsoft.deliverylocker.exceptionhandler.CrashHandler;
import com.ysxsoft.deliverylocker.network.AbsPostJsonStringCb;
import com.ysxsoft.deliverylocker.network.NetWorkUtil;
import com.ysxsoft.deliverylocker.receiver.ReceiverOrders;
import com.ysxsoft.deliverylocker.service.TimerService;
import com.ysxsoft.deliverylocker.tcp.SocketClient;
import com.ysxsoft.deliverylocker.ui.adapter.FgvgAdapter;
import com.ysxsoft.deliverylocker.ui.fragment.BaseFragment;
import com.ysxsoft.deliverylocker.ui.fragment.Tab1Fragment;
import com.ysxsoft.deliverylocker.ui.fragment.Tab2Fragment;
import com.ysxsoft.deliverylocker.utils.GlideImageLoader;
import com.ysxsoft.deliverylocker.utils.MD5Util;
import com.ysxsoft.deliverylocker.utils.OnPageChangeCallBack;
import com.ysxsoft.deliverylocker.utils.SerialPortUtil;
import com.ysxsoft.deliverylocker.utils.SystemUtil;
import com.ysxsoft.deliverylocker.utils.TtsUtil;
import com.ysxsoft.deliverylocker.utils.cache.ACacheHelper;
import com.ysxsoft.deliverylocker.utils.glide.GlideUtils;
import com.ysxsoft.deliverylocker.widget.CustomViewPager;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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

    private ArrayList<BaseFragment> fragments;//

    private List<DeviceBean.ResultBean.AdsBean> bannerList;//轮播图数据
    private long changeSelectedTime;

    private TimerService timerService;//心跳服务
    public ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            timerService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            timerService = ((TimerService.MyBinder) service).getService();
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        SophixManager.getInstance().queryAndLoadNewPatch();//检查更新
        EventBus.getDefault().register(this);

        TtsUtil.getInstance().speak("欢迎使用心甜智能柜");
        NetWorkUtil.getPhoneState(this, size -> tvNetWork.setText(String.format("4G/%s", size)));

        tvTop.setText(String.format("%s%s\u3000客服电话：%s", DeviceInfo.getIntence().getProperty(), DeviceInfo.getIntence().getTag(), DeviceInfo.getIntence().getService_tel()));
        GlideUtils.setBackgroud(ivLogo, DeviceInfo.getIntence().getLogo());

        initBanner(DeviceInfo.getIntence().getDeviceBean().getResult().getAds());
        initFragment();
        initTouchTimer();

        SocketClient.socketMain(DeviceInfo.getIntence().register_key());
        ReceiverOrders.openDog();//打开看门狗
        bindService(new Intent(mContext, TimerService.class), conn, Context.BIND_AUTO_CREATE);//绑定启动服务

        SerialPortUtil.init_receive_serial();//开启串口

        upDateErrorLog();//上传错误日志
    }


    private void initFragment() {
        viewPager.setScanScroll(false);//不支持滑动
        viewPager.setOffscreenPageLimit(3);
        fragments = new ArrayList<>();
        fragments.add(Tab1Fragment.newInstance(0, DeviceInfo.getIntence().register_key()));
        fragments.add(Tab2Fragment.newInstance(1, DeviceInfo.getIntence().register_key()));
        fragments.add(Tab1Fragment.newInstance(2, String.valueOf(DeviceInfo.getIntence().getCompany_id())));
        FgvgAdapter fgAdapter = new FgvgAdapter(getSupportFragmentManager(), 0, fragments);
        viewPager.setAdapter(fgAdapter);
        viewPager.addOnPageChangeListener(new OnPageChangeCallBack() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position != 1) {
                    if (fragments.size() > 1 && fragments.get(1) != null) {
                        fragments.get(1).externalInvoking("clear");
                    }
                }
            }
        });
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

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.tvNetWork})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1://二维码取件
                clearBtnBack();
                btn1.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
                btn1.setBackgroundResource(R.color.colorMaster);
                viewPager.setCurrentItem(0);
                mHandler.removeCallbacks(runnable);
                break;
            case R.id.btn2://取件码取件
                clearBtnBack();
                btn2.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
                btn2.setBackgroundResource(R.color.colorMaster);
                viewPager.setCurrentItem(1);
                touchTimer = 0;
                mHandler.removeCallbacks(runnable);
                mHandler.postDelayed(runnable, 1000);
                break;
            case R.id.btn3://投递员投件
                clearBtnBack();
                btn3.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
                btn3.setBackgroundResource(R.color.colorMaster);
                viewPager.setCurrentItem(2);
                touchTimer = 0;
                mHandler.removeCallbacks(runnable);
                mHandler.postDelayed(runnable, 1000);
                break;
            case R.id.tvNetWork:
                toast(String.format("当前版本%s", SystemUtil.getVerName(mContext)));
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
    /**
     * 刷新轮播图（）
     *
     * @param bus
     */
    @Subscribe
    public void refreshDevice(DeviceRefreshBus bus) {
        bannerList = DeviceInfo.getIntence().getDeviceBean().getResult().getAds();
        List<String> list = new ArrayList<>();
        for (DeviceBean.ResultBean.AdsBean bannerBean : bannerList) {
            list.add(bannerBean.getUrl());
        }
        bannerFlow.update(list);
    }

    private void initBanner(List<DeviceBean.ResultBean.AdsBean> adsBean) {
        List<String> list = new ArrayList<>();
        for (DeviceBean.ResultBean.AdsBean bannerBean : adsBean) {
            list.add(bannerBean.getUrl());
        }
        //设置banner样式(这里有5个样式，有三个必须设置title)
        bannerFlow.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        bannerFlow.setImageLoader(new GlideImageLoader());
        //设置图片集合
        bannerFlow.setImages(list);
        //设置banner动画效果 FlipHorizontal(翻页)　CubeOut（立方体）CubeIn（还行与Accordion效果类似）
        bannerFlow.setBannerAnimation(Transformer.Accordion);
//        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        bannerFlow.isAutoPlay(true);
        //设置轮播时间
        bannerFlow.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        bannerFlow.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        bannerFlow.start();
    }


    /**
     * 上传错误日志
     */
    private void upDateErrorLog(){
        String error_log = ACacheHelper.getString(CrashHandler.ERROR_LOG, "");

        Log.e("error_log", "log: "+ error_log);
        if (TextUtils.isEmpty(error_log)){
            return;
        }
        String device_id = MD5Util.md5Decode32(SystemUtil.getImei() + "iot");
        ApiUtils.errorLog(device_id, SystemUtil.getVerName(mContext), error_log, new AbsPostJsonStringCb() {
            @Override
            public void onSuccess(String str, String data) {
                //上传成功清空 log日志
                ACacheHelper.putString(CrashHandler.ERROR_LOG, "");
                Log.e("error_log", str);
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Log.e("error_log", "error");
            }

            @Override
            public void onFinish() {

            }
        });
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
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

    }

}
