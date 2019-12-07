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
import com.ysxsoft.deliverylocker.utils.TtsUtil;

import java.util.ArrayList;
import java.util.List;

public class PasswordView extends RelativeLayout {

    private RecyclerView pwdView;
    private PwdAdapter pwdAdapter;

    private RecyclerView keyBordView;
    private KeyBordAdapter keyBordAdapter;

    private StringBuilder pwdNumb;
    private List<String> pwdList;

    private PwdInputFinishListener listener;

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


    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.widget_password_view, this, false);
        pwdView = view.findViewById(R.id.pwdView);
        keyBordView = view.findViewById(R.id.keyBordView);
        pwdNumb = new StringBuilder();
        initPwdAdapter();
        initRecyclerViewKeyBord();


        addView(view);
    }

    /**
     * 初始化密码输入框
     */
    private void initPwdAdapter() {
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
            if (position == 9){
                if (keyBordAdapter.getColorUnify()){
                    TtsUtil.getInstance().speak("重置");
                }
            }else if (position == 11){
                if (keyBordAdapter.getColorUnify()){
                    TtsUtil.getInstance().speak("删除");
                }
            }else {
                if (position == 1){
                    TtsUtil.getInstance().speak("二");
                }else {
                    TtsUtil.getInstance().speak(String.valueOf(keyBordAdapter.getItem(position)));
                }
            }
            if (pwdNumb.length() >= 8){
                return;
            }
            int item = keyBordAdapter.getItem(position);
            pwdNumb(position, item);
            keyBordAdapter.setColorUnify(pwdNumb.length() > 0);
        }));
    }

    /**
     * 第一次
     *
     * @param position
     * @param item
     */
    private void pwdNumb(int position, int item) {
        if (9 == position) {//重置
            pwdNumb.delete(0, pwdNumb.length());
            pwdAdapter.resetData();
        } else if (11 == position) {//返回
            if (pwdNumb.length() == 0) {
                return;
            }
            pwdNumb.deleteCharAt(pwdNumb.length() - 1);
            pwdAdapter.setNewData(pwdNumb);
        } else {//输入
            if (pwdNumb.length() >= 8) {//显示按钮
                return;
            }
            pwdNumb.append(item);
            pwdAdapter.setNewData(pwdNumb);
            if (pwdNumb.length() == 8) {//输入完毕
                //TODO
                keyBordAdapter.setClickEnable(false);
                listener.inputFinish(pwdNumb.toString());
                pwdNumb.delete(0, pwdNumb.length());
                pwdAdapter.resetData();
            }
        }
    }

    /**
     * 清空输入信息
     */
    public void clearInput(){
        pwdNumb = new StringBuilder();
        if (pwdAdapter != null)
            pwdAdapter.resetData();
        if (keyBordAdapter != null )
            keyBordAdapter.setColorUnify(false);
    }

    /**
     * 恢复键盘点击事件
     */
    public void recoverKeybordClickEnable(){
        keyBordAdapter.setClickEnable(true);
    }

    /**
     * 设置密码输入完毕监听
     * @param listener
     */
    public void setListener(PwdInputFinishListener listener) {
        this.listener = listener;
    }

    /**
     * 密码输入完毕监听
     */
    public interface PwdInputFinishListener{
        void inputFinish(String numb);
    }

}
