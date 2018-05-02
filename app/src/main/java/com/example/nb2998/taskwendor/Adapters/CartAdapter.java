package com.example.nb2998.taskwendor.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nb2998.taskwendor.Models.SingleItem;
import com.example.nb2998.taskwendor.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemHolder> {

    private ArrayList<SingleItem> cartItemArrayList;
    Context context;

    public CartAdapter(ArrayList<SingleItem> cartItemArrayList, Context context)
    {
        this.cartItemArrayList = cartItemArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartItemHolder cartItemHolder = new CartItemHolder(LayoutInflater.from(context).inflate(R.layout.single_row_cart, parent, false));
        return cartItemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return cartItemArrayList.size();
    }

    class CartItemHolder extends RecyclerView.ViewHolder {

        public CartItemHolder(View itemView) {
            super(itemView);
        }
    }
}
