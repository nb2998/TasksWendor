package com.example.nb2998.taskwendor.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nb2998.taskwendor.Models.SingleItem;

import java.util.ArrayList;

import static com.example.nb2998.taskwendor.Database.DBContract.*;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = CREATE + TABLE_NAME + LBR +
                COLUMN_NAME + TYPE_TEXT + COMMA +
                COLUMN_PRICE + TYPE_INTEGER + COMMA +
                COLUMN_TOTAL_UNITS +TYPE_INTEGER + COMMA +
                COLUMN_LEFT_UNITS +TYPE_INTEGER +COMMA +
                COLUMN_IMAGE_URL + TYPE_TEXT +
                RBR + TERMINATE;
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertItemsIntoDb(ArrayList<SingleItem> itemArrayList) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        for(int i=0; i<itemArrayList.size(); i++) {
            SingleItem item = itemArrayList.get(i);
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, item.getName());
            cv.put(COLUMN_PRICE, item.getPrice());
            cv.put(COLUMN_TOTAL_UNITS, item.getTot_units());
            cv.put(COLUMN_LEFT_UNITS, item.getLeft_units());
            cv.put(COLUMN_IMAGE_URL, item.getImage_url());
            sqLiteDatabase.insert(TABLE_NAME, null, cv);
        }
    }

    public ArrayList<SingleItem> readItemsFromDb() {
        ArrayList<SingleItem> itemArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            itemArrayList.add((new SingleItem(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URL)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL_UNITS)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_LEFT_UNITS)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE)))));
        }
        sqLiteDatabase.close();
        return itemArrayList;
    }
}
