package com.example.nb2998.taskwendor.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nb2998.taskwendor.Database.DBHelper;
import com.example.nb2998.taskwendor.Models.Cart;
import com.example.nb2998.taskwendor.Models.SingleItem;
import com.example.nb2998.taskwendor.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAbove extends Fragment implements View.OnClickListener {

    TextView tv_name_item_details, tv_price_item_details, tv_tot_item_details, tv_left_item_details;
    Button btnAddToCart;
    ArrayList<SingleItem> itemArrayList;
    int selected;

    public FragmentAbove() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =LayoutInflater.from(getContext()).inflate(R.layout.fragment__above, container, false);
        itemArrayList = (new DBHelper(getContext())).readItemsFromDb();

        tv_name_item_details = view.findViewById(R.id.tv_name_item_details);
        tv_price_item_details = view.findViewById(R.id.tv_price_item_details);
        tv_tot_item_details = view.findViewById(R.id.tv_tot_item_details);
        tv_left_item_details = view.findViewById(R.id.tv_left_item_details);
        btnAddToCart = view.findViewById(R.id.btn_add_to_cart);

        tv_name_item_details.setText(new StringBuilder().append(getString(R.string.name)).append(itemArrayList.get(0).getName()).toString());
        tv_price_item_details.setText(new StringBuilder().append(getString(R.string.price)).append(itemArrayList.get(0).getPrice()).toString());
        tv_tot_item_details.setText(new StringBuilder().append(getString(R.string.total_units)).append(itemArrayList.get(0).getTot_units()).toString());
        tv_left_item_details.setText(new StringBuilder().append(getString(R.string.left_units)).append(itemArrayList.get(0).getLeft_units()).toString());

        btnAddToCart.setOnClickListener(this);
        return view;
    }

    public void updateDetails(int selected){
        this.selected=selected;
        tv_name_item_details.setText(new StringBuilder().append(getString(R.string.name)).append(itemArrayList.get(selected).getName()).toString());
        tv_price_item_details.setText(new StringBuilder().append(getString(R.string.price)).append(itemArrayList.get(selected).getPrice()).toString());
        tv_tot_item_details.setText(new StringBuilder().append(getString(R.string.total_units)).append(itemArrayList.get(selected).getTot_units()).toString());
        tv_left_item_details.setText(new StringBuilder().append(getString(R.string.left_units)).append(itemArrayList.get(selected).getLeft_units()).toString());
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_add_to_cart){
            Cart cart = Cart.getInstance();
            if(cart!=null){
                Log.d("TAG", "onClick: "+cart.getTotalPrice());
                cart.addToCart(itemArrayList.get(selected));
                Log.d("TAG", "onClick: "+cart.getTotalPrice());

            }
        }
    }
}
