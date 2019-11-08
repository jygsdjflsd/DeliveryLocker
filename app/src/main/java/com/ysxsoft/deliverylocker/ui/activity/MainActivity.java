package com.ysxsoft.deliverylocker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.ysxsoft.deliverylocker.R;
import com.ysxsoft.deliverylocker.app.MyApplication;
import com.ysxsoft.deliverylocker.ui.adapter.FgAdapter;
import com.ysxsoft.deliverylocker.ui.adapter.FgvgAdapter;
import com.ysxsoft.deliverylocker.ui.fragment.TabFragment;
import com.ysxsoft.deliverylocker.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

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

    /**
     * @param json
     */
    public static void newIntent(String json) {
        Intent intent = new Intent(MyApplication.getApplication(), MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("json", json);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getApplication().startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initFragment();
    }

    private void initFragment() {
        viewPager.setScanScroll(false);//不支持滑动
        List<Fragment> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(TabFragment.newInstance(i));
        }
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
    private void clearBtnBack(){
        btn1.setBackgroundResource(R.color.colorF5F5F5);
        btn2.setBackgroundResource(R.color.colorF5F5F5);
        btn3.setBackgroundResource(R.color.colorF5F5F5);
    }
}
