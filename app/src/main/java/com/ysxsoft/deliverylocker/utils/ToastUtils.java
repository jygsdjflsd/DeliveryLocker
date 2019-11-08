package com.ysxsoft.deliverylocker.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.ysxsoft.deliverylocker.app.MyApplication;


/**
 * Created by Liang_Lu on 2017/9/7.
 */

public class ToastUtils {
    private static Context context = MyApplication.getApplication();
    private static Toast toast;

    public static void show(@StringRes int resId) {
        show(context.getResources().getString(resId));
    }

    public static void show(CharSequence text) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }
    public static void showLong(CharSequence text){
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        toast.show();
    }
}
