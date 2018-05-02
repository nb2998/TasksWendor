package com.example.nb2998.taskwendor.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart {

    private static Cart cartInstance;

    private HashMap<SingleItem, Integer> cartItemQuantityMap;
    private double totalPrice;

    private Cart(HashMap<SingleItem, Integer> cartItemQuantityMap, double totalPrice) {
        this.cartItemQuantityMap = cartItemQuantityMap;
        this.totalPrice = totalPrice;
    }

    public synchronized static Cart getInstance() {
        if (cartInstance == null) {
            cartInstance = new Cart(new HashMap<SingleItem, Integer>(), 0);
        }

        return cartInstance;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public HashMap<SingleItem, Integer> getCartItemQuantityMap() {
        return cartItemQuantityMap;
    }

    public boolean addToCart(SingleItem item) {
        if (item.getLeft_units() > 0) {
            if (cartItemQuantityMap.containsKey(item) && cartItemQuantityMap.get(item)+1 <item.getTot_units())
                cartItemQuantityMap.put(item, cartItemQuantityMap.get(item) + 1);
            else cartItemQuantityMap.put(item, 1);
            this.totalPrice += item.getPrice();
            return true;
        }
        return false;
    }

    public boolean removeFromCart(SingleItem item) {
        if (cartItemQuantityMap.containsKey(item) && cartItemQuantityMap.get(item) > 0) {
            cartItemQuantityMap.put(item, cartItemQuantityMap.get(item) - 1);
            if(cartItemQuantityMap.get(item)==0) cartItemQuantityMap.remove(item);
            this.totalPrice -= item.getPrice();
            return true;
        }
        return false;
    }
}
