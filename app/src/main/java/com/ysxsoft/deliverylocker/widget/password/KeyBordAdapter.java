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

public class KeyBordAdapter extends RecyclerView.Adapter<KeyBordAdapter.MyViewHolder> {

    private List<Integer> list;
    private Context context;

    private OnItemClickListener listener;

    private boolean colorUnify;//颜色统一
    private boolean clickEnable;//按键锁定

    void setOnitemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    KeyBordAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
        clickEnable = true;
    }

    /**
     * 设置键盘状态
     * @param clickEnable
     */
    void setClickEnable(boolean clickEnable){
        this.clickEnable = clickEnable;
        notifyDataSetChanged();
    }
    /**
     * 设置颜色统一
     *
     * @param colorUnify
     */
    void setColorUnify(boolean colorUnify) {
        this.colorUnify = colorUnify;
        notifyDataSetChanged();
    }

    Integer getItem(int position) {
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
        holder.itemView.setEnabled(clickEnable);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(this, holder.itemView, position);
        });
        if (position == 9) {
            holder.tv.setText("重置");
            holder.rel.setBackgroundResource(clickEnable ? colorUnify ? R.drawable.solid_master_dp5 : R.drawable.solid_d1d1d1_dp5 :  R.drawable.solid_d1d1d1_dp5);
        } else if (position == 11) {
            holder.tv.setText("删除");
            holder.rel.setBackgroundResource(clickEnable ? colorUnify ? R.drawable.solid_master_dp5 : R.drawable.solid_d1d1d1_dp5 : R.drawable.solid_d1d1d1_dp5);
        } else {
            holder.tv.setText(String.valueOf(list.get(position)));
            holder.rel.setBackgroundResource(clickEnable ? R.drawable.solid_master_dp5 : R.drawable.solid_d1d1d1_dp5);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rel;
        TextView tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rel = itemView.findViewById(R.id.rel);
            tv = itemView.findViewById(R.id.tv);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(KeyBordAdapter adapter, View parent, int position);
    }
}
