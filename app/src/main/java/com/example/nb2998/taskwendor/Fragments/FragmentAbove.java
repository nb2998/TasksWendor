package com.example.nb2998.taskwendor.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nb2998.taskwendor.Database.DBHelper;
import com.example.nb2998.taskwendor.Models.Cart;
import com.example.nb2998.taskwendor.Models.SingleItem;
import com.example.nb2998.taskwendor.R;
import com.example.nb2998.taskwendor.Utils.OnSwipeTouchListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAbove extends Fragment implements View.OnClickListener {

    TextView tv_name_item_details, tv_price_item_details, tv_tot_item_details, tv_left_item_details;
    LinearLayout ll_item_details;
    Button btn_add_to_cart;
    ImageButton btn_swipe_left, btn_swipe_right;
    ArrayList<SingleItem> itemArrayList;
    int selected;

    public FragmentAbove() {
        // Required empty public constructor
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment__above, container, false);
        itemArrayList = (new DBHelper(getContext())).readItemsFromDb();

        tv_name_item_details = view.findViewById(R.id.tv_name_item_details);
        tv_price_item_details = view.findViewById(R.id.tv_price_item_details);
        tv_tot_item_details = view.findViewById(R.id.tv_tot_item_details);
        tv_left_item_details = view.findViewById(R.id.tv_left_item_details);
        btn_add_to_cart = view.findViewById(R.id.btn_add_to_cart);
        btn_swipe_left = view.findViewById(R.id.btn_swipe_left);
        btn_swipe_right = view.findViewById(R.id.btn_swipe_right);
        ll_item_details = view.findViewById(R.id.ll_item_details);

        tv_name_item_details.setText(new StringBuilder().append(getString(R.string.name)).append(itemArrayList.get(0).getName()).toString());
        tv_price_item_details.setText(new StringBuilder().append(getString(R.string.price)).append(itemArrayList.get(0).getPrice()).toString());
        tv_tot_item_details.setText(new StringBuilder().append(getString(R.string.total_units)).append(itemArrayList.get(0).getTot_units()).toString());
        tv_left_item_details.setText(new StringBuilder().append(getString(R.string.left_units)).append(itemArrayList.get(0).getLeft_units()).toString());

        btn_add_to_cart.setOnClickListener(this);
        btn_swipe_left.setOnClickListener(this);
        btn_swipe_right.setOnClickListener(this);

        ll_item_details.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeLeft() {
                Log.d("TAG", "onSwipeLeft: sel i "+selected);
                if((selected+1)!=(itemArrayList.size()-1)) updateDetails((selected+1)%(itemArrayList.size()-1));
                else updateDetails(selected+1);
            }

            @Override
            public void onSwipeRight() {
                if(selected!=0) updateDetails((selected-1)%(itemArrayList.size()-1));
                else updateDetails(itemArrayList.size()-1);
            }
        });
        return view;
    }

    public void updateDetails(int selected){
        Log.d("TAG", "updateDetails: sel "+selected);
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
        } else if(v.getId()==R.id.btn_swipe_left){
            Log.d("TAG", "onClick: "+selected);
            if(selected!=(itemArrayList.size()-1)) updateDetails((selected+1)%(itemArrayList.size()-1));
            else updateDetails(selected+1);
        } else if(v.getId()==R.id.btn_swipe_right) {
            updateDetails((selected+1)%(itemArrayList.size()-1));
        }
    }
}
