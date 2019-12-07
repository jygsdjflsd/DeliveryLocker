package com.ysxsoft.deliverylocker.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ysxsoft.deliverylocker.ui.fragment.BaseFragment;

import java.util.List;

public class FgvgAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mList;

    public FgvgAdapter(@NonNull FragmentManager fm, int behavior, List<BaseFragment> mList) {
        super(fm, behavior);
        this.mList = mList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

}
