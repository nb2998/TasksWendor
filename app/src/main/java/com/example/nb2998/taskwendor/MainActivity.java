package com.example.nb2998.taskwendor;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.nb2998.taskwendor.Adapters.ItemsBelowAdapter;
import com.example.nb2998.taskwendor.Database.DBHelper;
import com.example.nb2998.taskwendor.Fragments.CartFragment;
import com.example.nb2998.taskwendor.Fragments.FragmentAbove;
import com.example.nb2998.taskwendor.Fragments.FragmentBelow;
import com.example.nb2998.taskwendor.Models.Items;
import com.example.nb2998.taskwendor.Models.SingleItem;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements ItemsBelowAdapter.ItemClicked, FragmentAbove.OnItemAdded {

    public ArrayList<SingleItem> itemsArrayList;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        itemsArrayList = dbHelper.readItemsFromDb();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(itemsArrayList==null || itemsArrayList.size()==0) {
            try {
                itemsArrayList = new ArrayList<>();
                fetchItemsFromUrl();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else{
            itemsArrayList = dbHelper.readItemsFromDb();
            addFragments();
        }
    }

    private void fetchItemsFromUrl() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(getString(R.string.basic_url)).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainActivity.this, R.string.error_failed_to_fetch, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                Items items = gson.fromJson(response.body().string(), Items.class);
                itemsArrayList.addAll(items.getItems());
                dbHelper.insertItemsIntoDb(itemsArrayList);

                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                FragmentAbove fragmentAbove = new FragmentAbove();
                fragmentTransaction.add(R.id.container_above, fragmentAbove);
                fragmentTransaction.commit();

                addFragments();
            }
        });

    }

    private void addFragments(){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        FragmentAbove fragmentAbove = new FragmentAbove();
        fragmentTransaction.replace(R.id.container_above, fragmentAbove);
        fragmentTransaction.commit();

        FragmentTransaction fragmentTransaction1 = fm.beginTransaction();
        FragmentBelow fragmentBelow = new FragmentBelow();
        fragmentTransaction1.replace(R.id.container_below, fragmentBelow);
        fragmentTransaction1.commit();

        FragmentTransaction fragmentTransaction2 = fm.beginTransaction();
        CartFragment cartFragment = new CartFragment();
        fragmentTransaction2.replace(R.id.container_right, cartFragment);
        fragmentTransaction2.commit();
    }

    @Override
    public void changeTextInFragmentAbove(int selected) {
        FragmentAbove fragmentAbove = (FragmentAbove) getSupportFragmentManager().findFragmentById(R.id.container_above);
        fragmentAbove.updateDetails(selected);
    }

    @Override
    public void updateCart(SingleItem singleItem) {
        CartFragment cartFragment = (CartFragment) getSupportFragmentManager().findFragmentById(R.id.container_right);
        cartFragment.updateCart(singleItem);
    }
}
