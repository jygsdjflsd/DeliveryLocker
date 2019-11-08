package com.ysxsoft.deliverylocker.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class FgvgAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;

    public FgvgAdapter(@NonNull FragmentManager fm, int behavior, List<Fragment> mList) {
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
