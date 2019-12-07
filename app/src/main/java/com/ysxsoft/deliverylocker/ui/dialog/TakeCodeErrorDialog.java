package com.ysxsoft.deliverylocker.ui.dialog;

import android.os.Bundle;
import android.os.Handler;

import com.ysxsoft.deliverylocker.R;

public class TakeCodeErrorDialog extends BaseDialog implements BaseDialog.OnDissmissListener {

    public static TakeCodeErrorDialog newInstance() {
        TakeCodeErrorDialog dialog = new TakeCodeErrorDialog();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isFull", false);
        dialog.setArguments(bundle);
        return dialog;
    }

    private Handler mHanlder = new Handler();
    private Runnable runnable = this::dismiss;

    @Override
    public int setUpLayoutId() {
        return R.layout.dialog_takecode_error;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialog dialog) {
        holder.getView(R.id.btnKnow).setOnClickListener(v -> dismiss());
        setOnDissmissListener(this);
        mHanlder.postDelayed(runnable, 15*1000);
    }

    @Override
    public void onDismiss() {
        mHanlder.removeCallbacks(runnable);
    }
}
