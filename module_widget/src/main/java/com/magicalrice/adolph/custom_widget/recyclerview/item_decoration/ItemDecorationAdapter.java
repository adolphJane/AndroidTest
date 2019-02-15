package com.magicalrice.adolph.custom_widget.recyclerview.item_decoration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.magicalrice.adolph.custom_widget.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDecorationAdapter extends RecyclerView.Adapter<ItemDecorationAdapter.ItemDecorationHolder> {
    private List<String> data;

    public ItemDecorationAdapter(List<String> data) {
        this.data = data;
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemDecorationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_itemdecoation,parent,false);
        ItemDecorationHolder holder = new ItemDecorationHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemDecorationHolder holder, int position) {
        if (data != null && data.size() > 0) {
            String text = data.get(position);
            holder.textView.setText(text);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ItemDecorationHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ItemDecorationHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_content);
        }
    }
}
