package com.ysxsoft.deliverylocker.ui.dialog;

import android.os.Bundle;

import com.ysxsoft.deliverylocker.R;

public class TakeCodeErrorDialog extends BaseDialog{

    public static TakeCodeErrorDialog newInstance() {
        TakeCodeErrorDialog dialog = new TakeCodeErrorDialog();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isFull", false);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.dialog_takecode_error;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialog dialog) {
        holder.getView(R.id.btnKnow).setOnClickListener(v -> dismiss());
    }
}
