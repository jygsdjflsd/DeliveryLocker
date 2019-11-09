package com.ysxsoft.deliverylocker.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.ysxsoft.deliverylocker.R;
import com.ysxsoft.deliverylocker.api.ApiUtils;
import com.ysxsoft.deliverylocker.bean.TakeCodeBean;
import com.ysxsoft.deliverylocker.network.AbsPostJsonStringCb;
import com.ysxsoft.deliverylocker.ui.dialog.TakeCodeErrorDialog;
import com.ysxsoft.deliverylocker.ui.dialog.TakeCodeSuccessDialog;
import com.ysxsoft.deliverylocker.utils.DensityUtil;
import com.ysxsoft.deliverylocker.widget.password.PasswordView;

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

    private int position;
    private String register_key;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        position = getArguments() != null ? getArguments().getInt(INTENT_POSTION) : position;
        register_key = getArguments() != null ? getArguments().getString(INTENT_REGISTER_KEY, "") : "";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.include_fg2;
    }

    @Override
    protected void initView() {
        pwdView.setListener(numb -> {
            ApiUtils.takeCode(numb, register_key, new AbsPostJsonStringCb() {
                @Override
                public void onSuccess(String str, String data) {
                    TakeCodeBean bean = new Gson().fromJson(str, TakeCodeBean.class);
                    if (bean.getStatus() == 0) {//success
                        TakeCodeSuccessDialog
                                .newInstance(bean.getResult().getDoor_number())
                                .setSize(DensityUtil.dp2px(getContext(), 400), DensityUtil.dp2px(getContext(), 205))
                                .show(getChildFragmentManager());
                    } else {
                        TakeCodeErrorDialog.newInstance()
                                .setSize(DensityUtil.dp2px(getContext(), 400), DensityUtil.dp2px(getContext(), 270))
                                .show(getChildFragmentManager());
                    }
                }

                @Override
                public void onFinish() {
                    pwdView.recoverKeybordClickEnable();
                }
            });

        });
    }

    private void showLoading() {
        relLoading.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        relLoading.setVisibility(View.GONE);
    }
}
