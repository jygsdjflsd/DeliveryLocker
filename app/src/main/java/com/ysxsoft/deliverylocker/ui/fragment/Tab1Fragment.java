package com.ysxsoft.deliverylocker.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.facebook.stetho.common.LogUtil;
import com.lzy.okgo.model.Response;
import com.ysxsoft.deliverylocker.R;
import com.ysxsoft.deliverylocker.api.ApiUtils;
import com.ysxsoft.deliverylocker.app.MyApplication;
import com.ysxsoft.deliverylocker.bus.RefreshQrCodeBus;
import com.ysxsoft.deliverylocker.network.AbsPostJsonStringCb;
import com.ysxsoft.deliverylocker.ui.activity.NetWorkLoseActivity;
import com.ysxsoft.deliverylocker.utils.DensityUtil;
import com.ysxsoft.deliverylocker.utils.QrCodeUtil;
import com.ysxsoft.deliverylocker.utils.ScreenUtils;
import com.ysxsoft.deliverylocker.utils.ToastUtils;
import com.ysxsoft.deliverylocker.utils.glide.GlideUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * 1.3 二维码页面
 */
public class Tab1Fragment extends BaseFragment {

    private static final String INTENT_POSTION = "position";
    private static final String INTENT_KEY = "register_key";

    public static Tab1Fragment newInstance(int position, String register_key) {
        Tab1Fragment fragment = new Tab1Fragment();
        Bundle args = new Bundle();
        args.putInt(INTENT_POSTION, position);
        args.putString(INTENT_KEY, register_key);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.ivQrCode)
    ImageView ivQrCode;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.layout)
    ConstraintLayout layout;

    private final int SIZE_SEVEN = DensityUtil.dp2px(MyApplication.getApplication(), 300);
    private final int SIZE_SEVEN_ = DensityUtil.dp2px(MyApplication.getApplication(), 350);

    private String register_key;//请求参数
    private int position;// 0二维码取件页面 2投递员投件
    private final String qrCodeDownload = "";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        position = getArguments() != null ? getArguments().getInt(INTENT_POSTION) : position;
        register_key = getArguments() != null ? getArguments().getString(INTENT_KEY, "") : "";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.include_fg1_3;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        if (ScreenUtils.getScreenInch((AppCompatActivity) getActivity()) < 9) {//7寸屏幕
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(position == 0 ? SIZE_SEVEN_ : SIZE_SEVEN, position == 0 ? SIZE_SEVEN_ : SIZE_SEVEN);
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            ivQrCode.setLayoutParams(params);
        }
        if (position == 0) {
            tv2.setVisibility(View.GONE);
            tv1.setGravity(Gravity.CENTER_HORIZONTAL);
            tv1.setText("请使用微信扫描二维码");
            getQrCode(null);
            ivQrCode.setOnClickListener(v -> {
                //点击更换二维码
                getQrCode(null);
            });
        } else {
            hideLoadling();
            ivQrCode.post(() -> {
                LogUtil.e("position != 0, width = " + ivQrCode.getWidth()+ ", size = "+ SIZE_SEVEN);
                Bitmap bitmap = QrCodeUtil.getQrCodeWidthPic(String.format("https://iot.modoubox.com/web_wechat/download_app?cid=%s", register_key),
                        ivQrCode.getWidth(), ivQrCode.getHeight());
                ivQrCode.setImageBitmap(bitmap);
            });
        }
    }

    /**
     * 获取二维码
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getQrCode(RefreshQrCodeBus bus) {
        if (position != 0) {
            return;
        }
        showLoading();
        ApiUtils.getQrCode(register_key, new AbsPostJsonStringCb() {
            @Override
            public void onSuccess(String str, String data) {
                try {
                    JSONObject object = new JSONObject(str);
                    if (object.optInt("status") == 0) {
                        GlideUtils.setBackgroud(ivQrCode, object.optString("result"));//加载图片
                    } else {
                        getQrCode(null);//失败重新获取网络加载二维码
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                hideLoadling();
            }
        });
    }

    /**
     * 刷新数据
     */
    private void showLoading() {
        progress.setVisibility(View.VISIBLE);
        layout.setVisibility(View.GONE);
    }

    /**
     * 刷新数据
     */
    private void hideLoadling() {
        progress.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
