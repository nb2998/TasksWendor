package com.example.nb2998.taskwendor.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nb2998.taskwendor.Adapters.ItemsBelowAdapter;
import com.example.nb2998.taskwendor.Models.SingleItem;
import com.example.nb2998.taskwendor.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBelow extends Fragment {

    ItemsBelowAdapter itemsBelowAdapter;

    public FragmentBelow() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            itemsBelowAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_below, container, false);
        RecyclerView recViewItems = view.findViewById(R.id.rec_view_items);
        recViewItems.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        itemsBelowAdapter = new ItemsBelowAdapter(getContext());
        recViewItems.setAdapter(itemsBelowAdapter);
        return view;
    }
}
