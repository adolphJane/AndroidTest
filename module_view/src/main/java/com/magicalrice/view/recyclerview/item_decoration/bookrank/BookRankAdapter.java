package com.magicalrice.view.recyclerview.item_decoration.bookrank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.magicalrice.view.R;

import java.util.List;

public class BookRankAdapter extends RecyclerView.Adapter<BookRankAdapter.BookRankHolder> {

    List<String> data;
    int[] mIconResources;

    public BookRankAdapter(List<String> data,int[] ids) {
        this.data = data;
        this.mIconResources = ids;
    }
    @NonNull
    @Override
    public BookRankHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_itemdecoration_bookrank,parent,false);
        BookRankHolder holder = new BookRankHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookRankHolder holder, int position) {
        if (data != null && data.size() > 0) {
            String text = data.get(position);
            String[] infos = text.split("-");

            if (infos.length >= 2) {
                holder.tvOrder.setText(position + "");
                holder.tvTitle.setText(infos[0]);
                holder.tvPrice.setText(infos[1]);
                holder.ivCover.setImageResource(mIconResources[position]);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class BookRankHolder extends  RecyclerView.ViewHolder{
        public TextView tvOrder;
        public TextView tvTitle;
        public TextView tvPrice;
        public ImageView ivCover;
        public BookRankHolder(View itemView) {
            super(itemView);

            tvOrder = (TextView) itemView.findViewById(R.id.tv_rank_oder);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            ivCover = (ImageView) itemView.findViewById(R.id.iv_cover);

        }

    }
}
