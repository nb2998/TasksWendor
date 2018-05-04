package com.example.nb2998.taskwendor.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.example.nb2998.taskwendor.Models.SingleItem;

import java.util.ArrayList;
import java.util.List;

import static com.example.nb2998.taskwendor.Database.DBContract.*;

public class CartDBHelper extends SQLiteOpenHelper {
    public static final int ADD = 0;
    public static final int DELETE = 1;

    public CartDBHelper(Context context) {
        super(context, CART_TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = CREATE + CART_TABLE_NAME + LBR +
                COLUMN_NAME + TYPE_TEXT + COMMA +
                COLUMN_PRICE + TYPE_INTEGER + COMMA +
                COLUMN_TOTAL_UNITS + TYPE_INTEGER + COMMA +
                COLUMN_LEFT_UNITS + TYPE_INTEGER + COMMA +
                COLUMN_IMAGE_URL + TYPE_TEXT + COMMA +
                COLUMN_QUANTITY + TYPE_INTEGER +
                RBR + TERMINATE;
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertItemsIntoDb(android.support.v4.util.Pair<SingleItem, Integer> singleItemIntegerPair) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        SingleItem item = singleItemIntegerPair.first;
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, singleItemIntegerPair.first.getName());
        cv.put(COLUMN_PRICE, item.getPrice());
        cv.put(COLUMN_TOTAL_UNITS, item.getTot_units());
        cv.put(COLUMN_LEFT_UNITS, item.getLeft_units());
        cv.put(COLUMN_IMAGE_URL, item.getImage_url());
        cv.put(COLUMN_QUANTITY, singleItemIntegerPair.second);
        sqLiteDatabase.insert(CART_TABLE_NAME, null, cv);

    }

    public ArrayList<android.support.v4.util.Pair<SingleItem, Integer>> readItemsFromDb() {
        ArrayList<android.support.v4.util.Pair<SingleItem, Integer>> itemArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(CART_TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            SingleItem singleItem = new SingleItem(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URL)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL_UNITS)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_LEFT_UNITS)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE)));
            itemArrayList.add(new android.support.v4.util.Pair<>(singleItem,
                    cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))));
        }
        cursor.close();
        sqLiteDatabase.close();
        return itemArrayList;
    }

    public void removeItemsFromDb(android.support.v4.util.Pair<SingleItem, Integer> itemToBeRemoved) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(CART_TABLE_NAME, COLUMN_NAME + "=?", new String[]{itemToBeRemoved.first.getName()});
        sqLiteDatabase.close();
    }

    public boolean updateQtyOfItem(android.support.v4.util.Pair<SingleItem, Integer> itemToBeUpdated, int addOrDelete) { //add=0, delete=1
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, itemToBeUpdated.first.getName());
        if (addOrDelete == ADD) {
            if ((itemToBeUpdated.second + 1) <= itemToBeUpdated.first.getTot_units())
                cv.put(COLUMN_QUANTITY, itemToBeUpdated.second + 1);
            else return false;
        } else if (addOrDelete == DELETE) {
            if ((itemToBeUpdated.second - 1) > 0)
                cv.put(COLUMN_QUANTITY, itemToBeUpdated.second - 1);
            else {
                removeItemsFromDb(itemToBeUpdated);
                return true;
            }
        }
        sqLiteDatabase.update(CART_TABLE_NAME, cv, COLUMN_NAME + "=?", new String[]{itemToBeUpdated.first.getName()});
        sqLiteDatabase.close();
        return true;
    }

    public android.support.v4.util.Pair<SingleItem, Integer> existsInDb(SingleItem singleItem) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(CART_TABLE_NAME, null, COLUMN_NAME + " =?", new String[]{singleItem.getName()}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            // TODO: 04/05/18 New Item increase quantity
            cursor.moveToFirst();
            SingleItem item = new SingleItem(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URL)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL_UNITS)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_LEFT_UNITS)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE)));
            return (new android.support.v4.util.Pair<>(singleItem,
                    cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))));
        }
        return null;
    }
}
