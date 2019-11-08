package com.ysxsoft.deliverylocker.widget.password;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ysxsoft.deliverylocker.R;

import java.util.ArrayList;
import java.util.List;

public class PasswordView extends RelativeLayout {

    private RecyclerView pwdView;
    private PwdAdapter pwdAdapter;

    private RecyclerView keyBordView;
    private KeyBordAdapter keyBordAdapter;

    private StringBuilder pwdNumb;
    private List<String> pwdList;

    public PasswordView(Context context) {
        this(context, null);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.widget_password_view, this, false);
        pwdView = view.findViewById(R.id.pwdView);
        keyBordView = view.findViewById(R.id.keyBordView);

        initPwdAdapter();
        initRecyclerViewKeyBord();



        addView(view);
    }

    /**
     * 初始化密码输入框
     */
    private void initPwdAdapter(){
        pwdList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            pwdList.add("");
        }
        pwdAdapter = new PwdAdapter(getContext(), pwdList);
        pwdView.setLayoutManager(new GridLayoutManager(getContext(), 8));
        pwdView.setAdapter(pwdAdapter);
    }

    /**
     * 初始化自定义键盘
     */
    private void initRecyclerViewKeyBord() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            if (i == 10) {
                list.add(0);
            } else {
                list.add(i + 1);
            }
        }
        keyBordAdapter = new KeyBordAdapter(getContext(), list);
        keyBordView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        keyBordView.setAdapter(keyBordAdapter);
        keyBordAdapter.setOnitemClickListener(((adapter1, view, position) -> {
            int item = keyBordAdapter.getItem(position);
            pwdNumb(position, item);
        }));
    }

    /**
     * 第一次
     *
     * @param position
     * @param item
     */
    private void pwdNumb(int position, int item) {
        if (9 == position) {//空

        } else if (11 == position) {//返回
            if (pwdNumb.length() == 0) {
                return;
            }
            pwdNumb.deleteCharAt(pwdNumb.length() - 1);
            for (int i = 0; i < 6; i++) {
                if (pwdNumb.length() > i) {
                    pwdAdapter.setData(i, String.valueOf(pwdNumb.charAt(i)));
                } else {
                    pwdAdapter.setData(i, "");
                }
            }
        } else {//输入
            if (pwdNumb.length() >= 8) {//显示按钮
                return;
            }
            pwdNumb.append(item);
            for (int i = 0; i < pwdNumb.length(); i++) {
                pwdAdapter.setData(i, String.valueOf(pwdNumb.charAt(i)));
            }
            if (pwdNumb.length() == 8) {//输入完毕
                //TODO

            }
        }
    }

}
