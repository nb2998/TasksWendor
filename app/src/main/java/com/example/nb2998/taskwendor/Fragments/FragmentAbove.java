package com.example.nb2998.taskwendor.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nb2998.taskwendor.Database.DBHelper;
import com.example.nb2998.taskwendor.Models.SingleItem;
import com.example.nb2998.taskwendor.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAbove extends Fragment {

    TextView tv_name_item_details, tv_price_item_details, tv_tot_item_details, tv_left_item_details;
    ArrayList<SingleItem> itemArrayList;

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

        tv_name_item_details.setText("Name: "+itemArrayList.get(0).getName());
        tv_price_item_details.setText("Price: "+itemArrayList.get(0).getPrice());
        tv_tot_item_details.setText("Total units: "+itemArrayList.get(0).getTot_units());
        tv_left_item_details.setText("Left units: "+itemArrayList.get(0).getLeft_units());
        return view;
    }

    public void updateDetails(int selected){
        tv_name_item_details.setText("Name: "+itemArrayList.get(selected).getName());
        tv_price_item_details.setText("Price: "+itemArrayList.get(selected).getPrice());
        tv_tot_item_details.setText("Total units: "+itemArrayList.get(selected).getTot_units());
        tv_left_item_details.setText("Left units: "+itemArrayList.get(selected).getLeft_units());
    }
}
