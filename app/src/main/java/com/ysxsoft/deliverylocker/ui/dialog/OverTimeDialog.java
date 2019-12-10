package com.ysxsoft.deliverylocker.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.ysxsoft.deliverylocker.R;
import com.ysxsoft.deliverylocker.api.ApiUtils;
import com.ysxsoft.deliverylocker.app.MyApplication;
import com.ysxsoft.deliverylocker.bean.CodeOvertimerBean;
import com.ysxsoft.deliverylocker.bean.DeviceInfo;
import com.ysxsoft.deliverylocker.network.AbsPostJsonStringCb;
import com.ysxsoft.deliverylocker.ui.activity.NetWorkLoseActivity;
import com.ysxsoft.deliverylocker.utils.DensityUtil;
import com.ysxsoft.deliverylocker.utils.QrCodeUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 超时弹窗
 */
public class OverTimeDialog extends BaseDialog implements BaseDialog.OnDissmissListener {

    public static OverTimeDialog newInstance(String orderId) {
        OverTimeDialog dialog = new OverTimeDialog(orderId);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isFull", false);
        dialog.setArguments(bundle);
        return dialog;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHanlder = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 101://
                    overTimeQuery();
                    break;
                case 102:
                    tvTitle.setVisibility(View.GONE);
                    tvSuccess.setVisibility(View.VISIBLE);
                    ivQrCode.setImageDrawable(ContextCompat.getDrawable(MyApplication.getApplication(), R.mipmap.icon_duihao));
                    break;
            }
        }
    };
    private Runnable runnable = this::dismiss;

    private String orderId;//订单id
    private ImageView ivQrCode;
    private TextView tvTitle;
    private TextView tvSuccess;

    public OverTimeDialog(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.dialog_overtime;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialog dialog) {
        holder.getView(R.id.ivDismiss).setOnClickListener(v -> dismiss());
        ivQrCode = holder.getView(R.id.ivQrCode);
        tvTitle = holder.getView(R.id.tvTitle);
        tvSuccess = holder.getView(R.id.tvSuccess);
        setOnDissmissListener(this);
        mHanlder.postDelayed(runnable, 30*1000);
        overTime();
    }

    @Override
    public void onDismiss() {
        mHanlder.removeCallbacks(runnable);
        mHanlder.removeMessages(101);
    }

    /**
     * 取件码超时
     */
    private void overTime() {
        ApiUtils.overTime(DeviceInfo.getIntence().register_key(), orderId, new AbsPostJsonStringCb() {
            @Override
            public void onSuccess(String str, String data) {
                CodeOvertimerBean bean = new Gson().fromJson(str, CodeOvertimerBean.class);
                if (bean.getStatus() == 0) {
                    Bitmap bitmap = QrCodeUtil.getQrCodeWidthPic(bean.getResult().getQrcode(),
                            DensityUtil.dp2px( MyApplication.getApplication(), 450), DensityUtil.dp2px( MyApplication.getApplication(), 450));
                    ivQrCode.setImageBitmap(bitmap);
                    mHanlder.sendEmptyMessageDelayed(101, 3000);
                }else {
                    Intent intent = new Intent(getActivity(), NetWorkLoseActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                //只要失败就进入网络重连页面
                Intent intent = new Intent(getActivity(), NetWorkLoseActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 取件码超时
     */
    private void overTimeQuery() {
        ApiUtils.overTimeQuery(orderId, new AbsPostJsonStringCb() {
            @Override
            public void onSuccess(String str, String data) {
                try {
                    JSONObject object = new JSONObject(str);
                    if (object.optInt("status") == 0) {
                        JSONObject result = object.optJSONObject("result");
                        if (result.optInt("paid") == 1) {//已经支付
                            mHanlder.sendEmptyMessage(102);
                        } else {
                            mHanlder.sendEmptyMessageDelayed(101, 3000);
                        }
                    } else {
                        mHanlder.sendEmptyMessageDelayed(101, 3000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mHanlder.sendEmptyMessageDelayed(101, 3000);
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                //只要失败就进入网络重连页面
                dismiss();
                Intent intent = new Intent(getActivity(), NetWorkLoseActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFinish() {

            }
        });
    }

}
