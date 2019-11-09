package com.ysxsoft.deliverylocker.widget.password;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ysxsoft.deliverylocker.R;

import java.util.List;

public class PwdAdapter extends RecyclerView.Adapter<PwdAdapter.MyViewHolder> {

    private List<String> list;
    private Context context;

    public PwdAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }
    void setData(@IntRange(from = 0) int index, @NonNull String data) {
        list.set(index, data);
        notifyItemChanged(index);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_pwdview, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvItem.setText(String.valueOf(position));
        if (!TextUtils.isEmpty(list.get(position))) {
            holder.tvItem.setText("-");
        } else {
            holder.tvItem.setText("");
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
