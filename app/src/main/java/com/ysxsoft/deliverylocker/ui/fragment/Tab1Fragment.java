package com.ysxsoft.deliverylocker.ui.fragment;

import android.content.Context;
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
import androidx.constraintlayout.widget.ConstraintLayout;

import com.ysxsoft.deliverylocker.R;
import com.ysxsoft.deliverylocker.api.ApiUtils;
import com.ysxsoft.deliverylocker.network.AbsPostJsonStringCb;
import com.ysxsoft.deliverylocker.utils.QrCodeUtil;
import com.ysxsoft.deliverylocker.utils.glide.GlideUtils;

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
        if (position == 0) {
            tv2.setVisibility(View.GONE);
            tv1.setGravity(Gravity.CENTER_HORIZONTAL);
            tv1.setText("请使用微信扫描二维码");
            getQrCode();
        } else {
            hideLoadling();
            ivQrCode.post(()->{
                Bitmap bitmap = QrCodeUtil.getQrCodeWidthPic(String.format("https://iot.modoubox.com/web_wechat/download_app?cid=%s", register_key),
                        ivQrCode.getWidth(), ivQrCode.getHeight());
                ivQrCode.setImageBitmap(bitmap);
            });
        }
    }

    /**
     * 获取二维码
     */
    private void getQrCode() {
        showLoading();
        ApiUtils.getQrCode(register_key, new AbsPostJsonStringCb() {
            @Override
            public void onSuccess(String str, String data) {
                try {
                    JSONObject object = new JSONObject(str);
                    if (object.optInt("status") == 0) {
                        GlideUtils.setBackgroud(ivQrCode, object.optString("result"));//加载图片
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
}
