package com.example.a301groupproject.factory.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a301groupproject.R;
import com.example.a301groupproject.ui.home.RvInterface;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private final RvInterface rvInterface;
    private ArrayList<Item> items;

    public ItemAdapter(ArrayList<Item> items,RvInterface rvInterface) {

        this.items = items;
        this.rvInterface = rvInterface;
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
        return new ViewHolder(view,rvInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.nameTextView.setText(item.getName());
        holder.modelTextView.setText(item.getModel());
        holder.makeTextView.setText(item.getMake());
        holder.dateTextView.setText(item.getDate());
        holder.valueTextView.setText(item.getValue());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView modelTextView;
        public TextView makeTextView;
        public TextView dateTextView;
        public TextView valueTextView;

        public ViewHolder(View itemView,RvInterface rvInterface) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_name);
            modelTextView = itemView.findViewById(R.id.item_model);
            makeTextView = itemView.findViewById(R.id.item_make);
            dateTextView = itemView.findViewById(R.id.item_date);
            valueTextView = itemView.findViewById(R.id.item_value);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(rvInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            rvInterface.onItemClick(pos);
                        }

                    }
                }
            });
        }
    }

}
