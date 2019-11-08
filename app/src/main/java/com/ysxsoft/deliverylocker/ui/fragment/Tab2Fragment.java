package com.ysxsoft.deliverylocker.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.ysxsoft.deliverylocker.R;
import com.ysxsoft.deliverylocker.widget.password.PasswordView;

import butterknife.BindView;

/**
 * 输入码取件
 */
public class Tab2Fragment extends BaseFragment {

    private static final String INTENT_POSTION = "position";

    public static Tab2Fragment newInstance(int position) {
        Tab2Fragment fragment = new Tab2Fragment();
        Bundle args = new Bundle();
        args.putInt(INTENT_POSTION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.pwdView)
    PasswordView pwdView;

    private int position;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        position = getArguments() != null ? getArguments().getInt(INTENT_POSTION) : position;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.include_fg2;
    }

    @Override
    protected void initView() {

    }


}
