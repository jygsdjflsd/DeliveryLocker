package com.ysxsoft.deliverylocker.widget.password;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ysxsoft.deliverylocker.R;
import com.ysxsoft.deliverylocker.app.MyApplication;
import com.ysxsoft.deliverylocker.utils.DensityUtil;

import java.util.List;

public class PwdAdapter extends RecyclerView.Adapter<PwdAdapter.MyViewHolder> {

    private List<String> list;
    private Context context;

    private int sizeType = 10;//屏幕尺寸 默认10

    public PwdAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * 重置
     */
    void resetData() {
        for (int i = 0; i < 8; i++) {
            list.set(i, "");
        }
        notifyDataSetChanged();
    }
    void setNewData(StringBuilder buffer){
        list.clear();
        for (int i = 0; i < 8; i++) {
            list.add(i < buffer.length() ? String.valueOf(buffer.charAt(i)) : "");
        }
        notifyDataSetChanged();
    }
    void setData(@IntRange(from = 0) int index, @NonNull String data) {
        list.set(index, data);
        notifyItemChanged(index);
    }

    /**
     * 设置屏幕尺寸
     * @param sizeType
     */
    public void setSizeType(int sizeType) {
        this.sizeType = sizeType;
    }

    @Override
    public int getItemViewType(int position) {
        return sizeType;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate( sizeType == 10 ? R.layout.item_pwdview : R.layout.item_pwdview_seven, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvItem.setText(String.valueOf(position));
        if (TextUtils.isEmpty(list.get(position))) {
            holder.tvItem.setText("-");
        } else {
            holder.tvItem.setText(String.valueOf(list.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tvItem);
        }
    }
}
