package com.ysxsoft.deliverylocker.ui.fragment;

import android.os.Bundle;

import com.ysxsoft.deliverylocker.R;

/**
 */
public class TabFragment extends BaseFragment {


    /**
     *
     * @param position
     * @return
     */
    public static TabFragment newInstance(int position) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_qr_code;
    }


}
