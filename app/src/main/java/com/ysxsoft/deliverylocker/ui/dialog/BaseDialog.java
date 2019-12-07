package com.ysxsoft.deliverylocker.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.ysxsoft.deliverylocker.R;

public abstract class BaseDialog extends DialogFragment {

    @LayoutRes
    protected int mLayoutResId;

    private float mDimAmount = 0.5f;//背景昏暗度
    private boolean mShowBottomEnable;//是否底部显示
    private int mMargin = 0;//左右边距
    private int mAnimStyle = 0;//进入退出动画
    private boolean mOutCancel = true;//点击外部取消
    private Context mContext;
    private int mWidth;
    private int mHeight;

    private boolean isFull;

    private OnDissmissListener listener;//dissmiss 监听


    public boolean isShowing(){
        return getDialog() != null && getDialog().isShowing();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        isFull = null != getArguments() && getArguments().getBoolean("isFull", false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BaseDialog);
        mLayoutResId = setUpLayoutId();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(mLayoutResId, container, false);
        convertView(ViewHolder.create(view), this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initParams();
    }

    private void initParams() {
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.dimAmount = mDimAmount;
//设置dialog显示位置
            if (mShowBottomEnable) {
                params.gravity = Gravity.BOTTOM;
            }else {
                params.gravity = Gravity.CENTER;
            }
            if (isFull) {//全屏
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            } else {//非全屏
//设置dialog宽度
                if (mWidth == 0) {
                    params.width = getScreenWidth(getContext()) - 2 * dp2px(getContext(), mMargin);
                } else {
                    params.width = dp2px(getContext(), mWidth);
                }
//设置dialog高度
                if (mHeight == 0) {
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                } else {
                    params.height = dp2px(getContext(), mHeight);
                }
            }
//设置dialog动画
            if (mAnimStyle != 0) {
                window.setWindowAnimations(mAnimStyle);
            }

            window.setAttributes(params);
        }
        setCancelable(mOutCancel);
    }

    /**
     * 设置背景昏暗度
     *
     * @param dimAmount
     * @return
     */
    public BaseDialog setDimAmout(@FloatRange(from = 0, to = 1) float dimAmount) {
        mDimAmount = dimAmount;
        return this;
    }

    /**
     * 是否显示底部
     *
     * @param showBottom
     * @return
     */
    public BaseDialog setShowBottom(boolean showBottom) {
        mShowBottomEnable = showBottom;
        return this;
    }

    /**
     * 设置宽高
     *
     * @param width
     * @param height
     * @return
     */
    public BaseDialog setSize(int width, int height) {
        mWidth = width;
        mHeight = height;
        return this;
    }

    /**
     * 设置左右margin
     *
     * @param margin
     * @return
     */
    public BaseDialog setMargin(int margin) {
        mMargin = margin;
        return this;
    }

    /**
     * 设置进入退出动画
     *
     * @param animStyle
     * @return
     */
    public BaseDialog setAnimStyle(@StyleRes int animStyle) {
        mAnimStyle = animStyle;
        return this;
    }

    /**
     * 设置是否点击外部取消
     *
     * @param outCancel
     * @return
     */
    public BaseDialog setOutCancel(boolean outCancel) {
        mOutCancel = outCancel;
        return this;
    }

    public BaseDialog show(FragmentManager manager) {
        super.show(manager, String.valueOf(System.currentTimeMillis()));
        return this;
    }

    public BaseDialog setOnDissmissListener(OnDissmissListener listener){
        this.listener = listener;
        return this;
    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if ( null != listener){
            listener.onDismiss();
        }
    }

    /**
     * 设置dialog布局
     *
     * @return
     */
    public abstract int setUpLayoutId();

    /**
     * 操作dialog布局
     *
     * @param holder
     * @param dialog
     */
    public abstract void convertView(ViewHolder holder, BaseDialog dialog);

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public interface OnDissmissListener{
        void onDismiss();
    }

    public static class ViewHolder{
        private SparseArray<View> views;
        private View convertView;

        private ViewHolder(View view) {
            convertView = view;
            views = new SparseArray<>();
        }

        public static ViewHolder create(View view) {
            return new ViewHolder(view);
        }

        /**
         * 获取View
         *
         * @param viewId
         * @param <T>
         * @return
         */
        public <T extends View> T getView(@IdRes int viewId) {
            View view = views.get(viewId);
            if (view == null) {
                view = convertView.findViewById(viewId);
                views.put(viewId, view);
            }
            return (T) view;
        }

        /**
         * 设置文本
         *
         * @param viewId
         * @param text
         */
        public void setText(int viewId, String text) {
            TextView textView = getView(viewId);
            textView.setText(text);
        }

        /**
         * 设置字体颜色
         *
         * @param viewId
         * @param colorId
         */
        public void setTextColor(int viewId, int colorId) {
            TextView textView = getView(viewId);
            textView.setTextColor(colorId);
        }

        /**
         * 设置背景图片
         *
         * @param viewId
         * @param resId
         */
        public void setBackgroundResource(int viewId, int resId) {
            View view = getView(viewId);
            view.setBackgroundResource(resId);
        }

        /**
         * 设置背景颜色
         *
         * @param viewId
         * @param colorId
         */
        public void setBackgroundColor(int viewId, int colorId) {
            View view = getView(viewId);
            view.setBackgroundColor(colorId);
        }

        /**
         * 设置点击事件
         *
         * @param viewId
         * @param listener
         */
        public void setOnClickListener(int viewId, View.OnClickListener listener) {
            View view = getView(viewId);
            view.setOnClickListener(listener);
        }
    }
}
