package com.ysxsoft.deliverylocker.widget.password;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ysxsoft.deliverylocker.R;

import java.util.List;

public class KeyBordAdapter extends RecyclerView.Adapter<KeyBordAdapter.MyViewHolder>{

    private List<Integer> list;
    private Context context;

    private OnItemClickListener listener;

    void setOnitemClickListener( OnItemClickListener listener){
        this.listener = listener;
    }

    KeyBordAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
    }

    Integer getItem(int position){
        if (position < list.size())
            return list.get(position);
        else
            return null;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_keybordpwd, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(this, holder.itemView, position);
        });
        if (position == 9) {
            holder.tv.setText("重置");
            holder.rel.setBackgroundResource(R.color.colorCDCDCD);
        } else if (position == 11) {
            holder.tv.setText("删除");
            holder.rel.setBackgroundResource(R.color.colorCDCDCD);
        } else {
            holder.tv.setText(String.valueOf(list.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout rel;
        TextView tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rel = itemView.findViewById(R.id.rel);
            tv = itemView.findViewById(R.id.tv);
        }
    }

    public static interface OnItemClickListener{
        void onItemClick(KeyBordAdapter adapter, View parent, int position);
    }
}
