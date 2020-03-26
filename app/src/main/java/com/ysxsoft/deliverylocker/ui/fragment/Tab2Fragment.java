package com.ysxsoft.deliverylocker.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.ysxsoft.deliverylocker.R;
import com.ysxsoft.deliverylocker.api.ApiUtils;
import com.ysxsoft.deliverylocker.app.MyApplication;
import com.ysxsoft.deliverylocker.bean.CodeOvertimerBean;
import com.ysxsoft.deliverylocker.bean.DeviceInfo;
import com.ysxsoft.deliverylocker.bean.TakeCodeBean;
import com.ysxsoft.deliverylocker.bus.OverTimeBus;
import com.ysxsoft.deliverylocker.network.AbsPostJsonStringCb;
import com.ysxsoft.deliverylocker.ui.activity.NetWorkLoseActivity;
import com.ysxsoft.deliverylocker.ui.dialog.BaseDialog;
import com.ysxsoft.deliverylocker.ui.dialog.OverTimeDialog;
import com.ysxsoft.deliverylocker.ui.dialog.TakeCodeErrorDialog;
import com.ysxsoft.deliverylocker.ui.dialog.TakeCodeSuccessDialog;
import com.ysxsoft.deliverylocker.utils.DensityUtil;
import com.ysxsoft.deliverylocker.utils.QrCodeUtil;
import com.ysxsoft.deliverylocker.utils.ScreenUtils;
import com.ysxsoft.deliverylocker.utils.ToastUtils;
import com.ysxsoft.deliverylocker.widget.password.PasswordView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * 输入码取件
 */
public class Tab2Fragment extends BaseFragment {

    private static final String INTENT_POSTION = "position";
    private static final String INTENT_REGISTER_KEY = "register_key";

    public static Tab2Fragment newInstance(int position, String register_key) {
        Tab2Fragment fragment = new Tab2Fragment();
        Bundle args = new Bundle();
        args.putInt(INTENT_POSTION, position);
        args.putString(INTENT_REGISTER_KEY, register_key);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.pwdView)
    PasswordView pwdView;
    @BindView(R.id.relLoading)
    RelativeLayout relLoading;
    @BindView(R.id.overTimerLayout)
    ConstraintLayout overTimerLayout;
    @BindView(R.id.ivQrCode)
    ImageView ivQrCode;

    private int position;
    private String register_key;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
        position = getArguments() != null ? getArguments().getInt(INTENT_POSTION) : position;
        register_key = getArguments() != null ? getArguments().getString(INTENT_REGISTER_KEY, "") : "";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.include_fg2;
    }

    @Override
    protected void initView() {
        if (ScreenUtils.getScreenInch((AppCompatActivity) getActivity()) < 9) {//7寸屏幕
            pwdView.setViewSize(7);
        }
        pwdView.setListener(numb -> {
            ApiUtils.takeCode(numb, register_key, new AbsPostJsonStringCb() {
                @Override
                public void onSuccess(String str, String data) {
                    TakeCodeBean bean = new Gson().fromJson(str, TakeCodeBean.class);
                    if (bean.getStatus() == 0) {//success
                        TakeCodeSuccessDialog
                                .newInstance(bean.getResult().getDoor_number())
                                .setSize(DensityUtil.dp2px(MyApplication.getApplication(), 400), DensityUtil.dp2px(MyApplication.getApplication(), 205))
                                .show(getChildFragmentManager());
                    } else if (bean.getStatus() == 2){//快件超时

                    }else {
                        TakeCodeErrorDialog.newInstance()
                                .setSize(DensityUtil.dp2px(MyApplication.getApplication(), 400), DensityUtil.dp2px(MyApplication.getApplication(), 270))
                                .show(getChildFragmentManager());
                    }
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    //只要失败就进入网络重连页面
                    Intent intent = new Intent(getActivity(), NetWorkLoseActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFinish() {
                    pwdView.recoverKeybordClickEnable();
                }
            });

        });
    }
    /**
     * 超时订单
     *
     * @param bus
     */
    @Subscribe
    public void overTime(OverTimeBus bus) {
        new Handler(Looper.getMainLooper()).post(()->{
            OverTimeDialog.newInstance(bus.getOrderid())
                    .setShowBottom(false)
                    .setDimAmout(0.5f)//设置背景昏暗度//默认0.5f
                    .setOutCancel(true)
                    .setSize(DensityUtil.dp2px(MyApplication.getApplication(), 550), 0)
                    .show(getChildFragmentManager());

        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }
    /**
     * 外部命令
     * @param type
     */
    @Override
    public void externalInvoking(String type) {
        switch (type){
            case "clear"://清空数据
                if (pwdView != null){
                    pwdView.clearInput();
                }
                break;
        }
    }

    private void showLoading() {
        relLoading.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        relLoading.setVisibility(View.GONE);
    }
}
