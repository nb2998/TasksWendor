package com.example.nb2998.taskwendor.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nb2998.taskwendor.Database.DBHelper;
import com.example.nb2998.taskwendor.Fragments.FragmentBelow;
import com.example.nb2998.taskwendor.Models.SingleItem;
import com.example.nb2998.taskwendor.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemsBelowAdapter extends RecyclerView.Adapter<ItemsBelowAdapter.ItemHolder>{

    Context context;
    ArrayList<SingleItem> itemArrayList;
    ItemClicked callback;
    int selected = 0;

    public ItemsBelowAdapter(Context context) {
        this.context = context;
        itemArrayList = (new DBHelper(context)).readItemsFromDb();
        callback = (ItemClicked) context;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.single_item_below, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int position) {
        holder.tv_name_item_list.setText(itemArrayList.get(position).getName());
        Picasso.with(context)
                .load(itemArrayList.get(position).getImage_url())
                .into(holder.iv_img_item_list);
        if(selected==position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#000000"));
            holder.tv_name_item_list.setTextColor(Color.WHITE);
        }
        else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.tv_name_item_list.setTextColor(Color.BLACK);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected=position;
                notifyDataSetChanged();
                sendToCallback();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (itemArrayList.size()>6)?6:itemArrayList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView tv_name_item_list;
        ImageView iv_img_item_list;
        LinearLayout ll_single_item;
        ItemHolder(View itemView) {
            super(itemView);
            iv_img_item_list = itemView.findViewById(R.id.iv_img_item_list);
            tv_name_item_list = itemView.findViewById(R.id.tv_name_item_list);
            ll_single_item = itemView.findViewById(R.id.ll_single_item);
        }
    }

    public void sendToCallback(){
        callback.changeTextInFragmentAbove(selected);
    }

    public interface ItemClicked{
        void changeTextInFragmentAbove(int selected);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        callback=null;
        super.onDetachedFromRecyclerView(recyclerView);
    }
}

