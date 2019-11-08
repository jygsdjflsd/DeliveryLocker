package com.ysxsoft.deliverylocker.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class FgAdapter<T extends Fragment> extends FragmentPagerAdapter {

    private List<FgBean<T>> mList;

    public FgAdapter(@NonNull FragmentManager fm, int behavior, List<FgBean<T>> mList) {
        super(fm, behavior);
        this.mList = mList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getTabName();
    }

    public static class FgBean<T extends Fragment>{
        private T fragment;
        private String tabName;

        public FgBean(T fragment, String tabName) {
            this.fragment = fragment;
            this.tabName = tabName;
        }

        public T getFragment() {
            return fragment;
        }

        public void setFragment(T fragment) {
            this.fragment = fragment;
        }

        public String getTabName() {
            return tabName == null ? "" : tabName;
        }

        public void setTabName(String tabName) {
            this.tabName = tabName;
        }
    }
}
