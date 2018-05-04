package com.example.nb2998.taskwendor.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nb2998.taskwendor.Database.CartDBHelper;
import com.example.nb2998.taskwendor.Database.DBHelper;
import com.example.nb2998.taskwendor.Models.Cart;
import com.example.nb2998.taskwendor.Models.SingleItem;
import com.example.nb2998.taskwendor.R;

import java.util.ArrayList;

import static com.example.nb2998.taskwendor.Database.CartDBHelper.ADD;
import static com.example.nb2998.taskwendor.Database.CartDBHelper.DELETE;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemHolder> {

    private ArrayList<Pair<SingleItem, Integer>> cartItemArrayList;
    Context context;
    UpdateCartFragmentTotalPrice callbackPrice;
    UpdateLeftUnitsInFragAbove callbackLeftUnits;

    public CartAdapter(ArrayList<Pair<SingleItem, Integer>> cartItemArrayList, Context context) {
        this.cartItemArrayList = cartItemArrayList;
        this.context = context;
        callbackPrice = (UpdateCartFragmentTotalPrice) context;
        callbackLeftUnits = (UpdateLeftUnitsInFragAbove) context;
    }

    @NonNull
    @Override
    public CartItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartItemHolder(LayoutInflater.from(context).inflate(R.layout.single_row_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CartItemHolder holder, int position) {
        final SingleItem item = cartItemArrayList.get(position).first;
        holder.tv_cart_item_name.setText((item != null) ? item.getName() : "");
        holder.tv_cart_item_qty.setText(new StringBuilder().append("Rs. ").append(item.getPrice()).append(" X ").append(cartItemArrayList.get(position).second).append(" = Rs. ").append(item.getPrice() * (cartItemArrayList.get(position).second)).toString());
        holder.btn_remove_from_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartDBHelper cartDBHelper = new CartDBHelper(context);
                Pair<SingleItem, Integer> itemToBeRemoved = cartItemArrayList.get(holder.getAdapterPosition());
                if (itemToBeRemoved.second <= 0) {
                    cartDBHelper.removeItemsFromDb(itemToBeRemoved);
                    Toast.makeText(context, "Item not present", Toast.LENGTH_SHORT).show();
                } else {
                    (new DBHelper(context)).updateLeftUnits(itemToBeRemoved.first, DBHelper.ADD);

                    cartDBHelper.updateQtyOfItem(itemToBeRemoved, DELETE);

                    float initial_price = context.getSharedPreferences(String.valueOf(R.string.shared_pref), Context.MODE_PRIVATE).getFloat(String.valueOf(R.string.total_price_shared_preferences), 0);
                    SharedPreferences sp = context.getSharedPreferences(String.valueOf(R.string.shared_pref), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putFloat(String.valueOf(R.string.total_price_shared_preferences), initial_price - itemToBeRemoved.second);
                    editor.commit();
                    callbackPrice.updatePrice();
                    callbackLeftUnits.updateLeftUnits();
                    Toast.makeText(context, "Item removed from cart", Toast.LENGTH_SHORT).show();
                }
                cartItemArrayList.clear();
                cartItemArrayList.addAll(cartDBHelper.readItemsFromDb());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemArrayList.size();
    }

    class CartItemHolder extends RecyclerView.ViewHolder {
        TextView tv_cart_item_name, tv_cart_item_qty;
        Button btn_remove_from_cart;

        CartItemHolder(View itemView) {
            super(itemView);
            tv_cart_item_qty = itemView.findViewById(R.id.tv_cart_item_qty);
            tv_cart_item_name = itemView.findViewById(R.id.tv_cart_item_name);
            btn_remove_from_cart = itemView.findViewById(R.id.btn_remove_from_cart);
        }
    }

    public interface UpdateCartFragmentTotalPrice {
        void updatePrice();
    }

    public interface UpdateLeftUnitsInFragAbove {
        void updateLeftUnits();
    }
}
