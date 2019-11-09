package com.ysxsoft.deliverylocker.ui.dialog;

import android.os.Bundle;

import com.ysxsoft.deliverylocker.R;

public class TakeCodeSuccessDialog extends BaseDialog{

    public static TakeCodeSuccessDialog newInstance(String numb) {
        TakeCodeSuccessDialog dialog = new TakeCodeSuccessDialog(numb);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isFull", false);
        dialog.setArguments(bundle);
        return dialog;
    }

    private String numb;
    public TakeCodeSuccessDialog(String numb) {
        this.numb = numb;
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.dialog_takecode;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialog dialog) {
        holder.setText(R.id.tvNumb, numb);
        holder.getView(R.id.btnKnow).setOnClickListener(v -> dismiss());
    }
}
