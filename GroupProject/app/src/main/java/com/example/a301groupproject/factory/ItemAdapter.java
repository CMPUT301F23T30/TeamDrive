package com.example.a301groupproject.factory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a301groupproject.R;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private ArrayList<Item> items;

    public ItemAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Item item = items.get(position);
        holder.nameTextView.setText(item.getName());
        holder.modelTextView.setText(item.getModel());
        holder.makeTextView.setText(item.getMake());
        holder.dateTextView.setText(item.getDate());
        holder.valueTextView.setText(item.getValue());

        // 在您的 ItemAdapter 的 onBindViewHolder 方法内部
        holder.checkBox.setOnCheckedChangeListener(null); // 先置为null，以避免旧的监听器干扰
        holder.checkBox.setChecked(item.isChecked()); // 设置当前的勾选状态

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // 更新您的 Item 对象的 isChecked 状态
            item.setChecked(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        public TextView nameTextView;
        public TextView modelTextView;
        public TextView makeTextView;
        public TextView dateTextView;
        public TextView valueTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            nameTextView = itemView.findViewById(R.id.item_name);
            modelTextView = itemView.findViewById(R.id.item_model);
            makeTextView = itemView.findViewById(R.id.item_make);
            dateTextView = itemView.findViewById(R.id.item_date);
            valueTextView = itemView.findViewById(R.id.item_value);
        }
    }

}
