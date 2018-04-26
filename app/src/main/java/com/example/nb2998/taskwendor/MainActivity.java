package com.example.nb2998.taskwendor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.nb2998.taskwendor.Database.DBHelper;
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

public class MainActivity extends AppCompatActivity {

    public ArrayList<SingleItem> itemsArrayList;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        itemsArrayList = dbHelper.readItemsFromDb();
        if(itemsArrayList==null || itemsArrayList.size()==0) {
            try {
                itemsArrayList = new ArrayList<>();
                fetchItemsFromUrl();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            }
        });

    }
}
