package com.example.nb2998.taskwendor.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.example.nb2998.taskwendor.Adapters.CartAdapter;
import com.example.nb2998.taskwendor.Database.CartDBHelper;
import com.example.nb2998.taskwendor.Database.DBHelper;
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
    CartAdapter cartAdapter;
    UpdateLeftUnitsInFragAbove callbackLeftUnits;

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
        callbackLeftUnits = (UpdateLeftUnitsInFragAbove) getContext();

        cartItemArrayList = cartDBHelper.readItemsFromDb();
        if (cartItemArrayList.size() == 0) tv_cart_total.setText(R.string.cart_is_empty);

        rec_view_cart.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        cartAdapter = new CartAdapter(cartItemArrayList, getContext());
        rec_view_cart.setAdapter(cartAdapter);

        int price = 0;
        for(int i=0; i<cartItemArrayList.size(); i++) {
            if(cartItemArrayList.get(i)!=null && cartItemArrayList.get(i).second!=null) price+=cartItemArrayList.get(i).second;
        }
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.shared_pref), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(getString(R.string.total_price_shared_preferences), price);
        editor.commit();

        tv_cart_total.setText(new StringBuilder().append(getContext().getResources().getString(R.string.total_rs)).append(sharedPreferences.getFloat(getString(R.string.total_price_shared_preferences), 0)));

        btn_cart_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 03/05/18 CHECKOUT CART
            }
        });

        return view;
    }

    public void updateCart(SingleItem itemToBeAdded) {
        (new DBHelper(getContext())).updateLeftUnits(itemToBeAdded, DBHelper.SUBTRACT);
        Pair<SingleItem, Integer> itemExists = cartDBHelper.existsInDb(itemToBeAdded);
        if (itemExists != null) {
            if(cartDBHelper.updateQtyOfItem(itemExists, CartDBHelper.ADD)) {
                cartItemArrayList.clear();
                cartItemArrayList.addAll(cartDBHelper.readItemsFromDb());
            } else{
                Toast.makeText(getContext(), "No more items can be added", Toast.LENGTH_SHORT).show();
            }
        } else {
            cartDBHelper.insertItemsIntoDb(new Pair<>(itemToBeAdded, 1));
            cartItemArrayList.clear();
            cartItemArrayList.addAll(cartDBHelper.readItemsFromDb());
        }

        float initial_price = getContext().getSharedPreferences(String.valueOf(R.string.shared_pref), Context.MODE_PRIVATE).getFloat(String.valueOf(R.string.total_price_shared_preferences), 0);
        SharedPreferences sp = getContext().getSharedPreferences(String.valueOf(R.string.shared_pref), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(getString(R.string.total_price_shared_preferences), initial_price+itemToBeAdded.getPrice());
        editor.commit();
        updatePrice();
        callbackLeftUnits.updateLeftUnits();

        cartAdapter.notifyDataSetChanged();
    }

    public void updatePrice() {
        // TODO: 04/05/18 Price not updating
        tv_cart_total.setText(new StringBuilder().append(getContext().getResources().getString(R.string.total_rs)).append(getContext().getSharedPreferences(getString(R.string.shared_pref), Context.MODE_PRIVATE).getFloat(getString(R.string.total_price_shared_preferences), 0)));
    }

    public interface UpdateLeftUnitsInFragAbove {
        void updateLeftUnits();
    }
}
