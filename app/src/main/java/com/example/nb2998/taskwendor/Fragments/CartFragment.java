package com.example.nb2998.taskwendor.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nb2998.taskwendor.Adapters.CartAdapter;
import com.example.nb2998.taskwendor.Database.CartDBHelper;
import com.example.nb2998.taskwendor.Models.Cart;
import com.example.nb2998.taskwendor.Models.SingleItem;
import com.example.nb2998.taskwendor.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    RecyclerView rec_view_cart;
    TextView tv_cart_total;
    Button btn_cart_checkout;
    ArrayList<Pair<SingleItem, Integer>> cartItemArrayList;
    CartDBHelper cartDBHelper;
    Cart cart;
    CartAdapter cartAdapter;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        rec_view_cart = view.findViewById(R.id.rec_view_cart);
        tv_cart_total = view.findViewById(R.id.tv_cart_total);
        btn_cart_checkout = view.findViewById(R.id.btn_cart_checkout);
        cartDBHelper = new CartDBHelper(getContext());
        cart = Cart.getInstance();

        cartItemArrayList = cartDBHelper.readItemsFromDb();
        if(cartItemArrayList.size()==0) tv_cart_total.setText(R.string.cart_is_empty);

        rec_view_cart.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        cartAdapter = new CartAdapter(cartItemArrayList, getContext());
        rec_view_cart.setAdapter(cartAdapter);

        tv_cart_total.setText(new StringBuilder().append(getContext().getResources().getString(R.string.total_rs)).append(cart.getTotalPrice()).toString());

        btn_cart_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 03/05/18 CHECKOUT CART
            }
        });

        return view;
    }

    public void updateCart(SingleItem itemToBeAdded){
        Pair<SingleItem, Integer> itemExists = cartDBHelper.existsInDb(itemToBeAdded);
        if(itemExists!=null) {
            cartDBHelper.updateQtyOfItem(itemExists, CartDBHelper.ADD);
            cartItemArrayList.remove(itemExists);
            cartItemArrayList.add(new Pair<>(itemExists.first, itemExists.second));
            Log.d("TAG", "updateCart: ");
        }
        else {
            itemExists = new Pair<>(itemToBeAdded, 1);
            cartDBHelper.insertItemsIntoDb(itemExists);
            cartItemArrayList.add(itemExists);
            Log.d("TAG", "updateCart: else");
        }
        Log.d("TAG", "updateCart: out "+cartItemArrayList.size());
        cartAdapter.notifyDataSetChanged();
    }

}
