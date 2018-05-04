package com.example.nb2998.taskwendor.Models;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private static Cart cartInstance;
    private static final int INITIAL_TOTAL_PRICE = 0;

//    private HashMap<SingleItem, Integer> cartItemQuantityMap;
    private List<Pair<SingleItem, Integer>> cartItemPairList;
    private double totalPrice;

    private Cart(List<Pair<SingleItem, Integer>> cartItemPairList, double totalPrice) {
        this.cartItemPairList = cartItemPairList;
        this.totalPrice = totalPrice;
    }

    public synchronized static Cart getInstance() {
        if (cartInstance == null) {
            cartInstance = new Cart(new ArrayList<Pair<SingleItem, Integer>>(), INITIAL_TOTAL_PRICE);
        }

        return cartInstance;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public List<Pair<SingleItem, Integer>> getCartItemPairList() {
        return cartItemPairList;
    }
}
