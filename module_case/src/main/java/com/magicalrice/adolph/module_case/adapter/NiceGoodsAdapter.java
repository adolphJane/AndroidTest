package com.magicalrice.adolph.module_case.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.magicalrice.adolph.module_case.ImageUtil;
import com.magicalrice.adolph.module_case.R;
import com.magicalrice.adolph.module_case.bean.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adolph on 2018/6/5.
 */

public class NiceGoodsAdapter extends RecyclerView.Adapter<NiceGoodsAdapter.GoodsHolder> {
    Context mContext;
    List<Goods.SubModel> data = new ArrayList<>();

    private LayoutInflater mLayoutInflater;



    public NiceGoodsAdapter(Context context, @NonNull List<Goods.SubModel> list){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        data.addAll(list);
    }

    @Override
    public GoodsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.c_item_goods_nice, parent, false);
        return new GoodsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GoodsHolder holder, int position) {
        holder.imageView.setImageResource(ImageUtil.getNiceGoodsImageResId(position));
        holder.textView.setText(data.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class GoodsHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public GoodsHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_goods_img);
            textView = itemView.findViewById(R.id.iv_goods_name);

        }
    }
}
