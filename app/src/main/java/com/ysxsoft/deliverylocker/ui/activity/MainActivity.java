package com.ysxsoft.deliverylocker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.ysxsoft.deliverylocker.R;
import com.ysxsoft.deliverylocker.app.MyApplication;
import com.ysxsoft.deliverylocker.bean.DeviceBean;
import com.ysxsoft.deliverylocker.ui.adapter.FgvgAdapter;
import com.ysxsoft.deliverylocker.ui.fragment.Tab1Fragment;
import com.ysxsoft.deliverylocker.ui.fragment.Tab2Fragment;
import com.ysxsoft.deliverylocker.widget.CustomViewPager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    public static void newIntent(Serializable serializable) {
        Intent intent = new Intent(MyApplication.getApplication(), MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", serializable);
        intent.putExtras(bundle);
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
    @BindView(R.id.ivFlow)
    ImageView ivFlow;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;

    private DeviceBean deviceBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {
        deviceBean = getIntent().getExtras() != null ? (DeviceBean) getIntent().getExtras().getSerializable("bean") : new DeviceBean();
        initFragment();
    }

    private void initFragment() {
        viewPager.setScanScroll(false);//不支持滑动
        viewPager.setOffscreenPageLimit(3);
        List<Fragment> list = new ArrayList<>();
        list.add(Tab1Fragment.newInstance(0, deviceBean.getResult().getCompany().getRegister_key()));
        list.add(Tab2Fragment.newInstance(1));
        list.add(Tab1Fragment.newInstance(2, String.valueOf(deviceBean.getResult().getCompany().getCompany_id())));
        FgvgAdapter fgAdapter = new FgvgAdapter(getSupportFragmentManager(), 0, list);
        viewPager.setAdapter(fgAdapter);
    }


    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
    public void onViewClicked(View view) {
        clearBtnBack();
        switch (view.getId()) {
            case R.id.btn1://二维码取件
                btn1.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
                btn1.setBackgroundResource(R.color.colorMaster);
                viewPager.setCurrentItem(0);
                break;
            case R.id.btn2://取件码取件
                btn2.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
                btn2.setBackgroundResource(R.color.colorMaster);
                viewPager.setCurrentItem(1);
                break;
            case R.id.btn3://投递员投件
                btn3.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
                btn3.setBackgroundResource(R.color.colorMaster);
                viewPager.setCurrentItem(2);
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
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {//有人摁下屏幕，//刷新回位倒计时

        }
        return super.onTouchEvent(event);

    }
}
