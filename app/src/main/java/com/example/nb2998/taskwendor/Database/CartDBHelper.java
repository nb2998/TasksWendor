package com.example.nb2998.taskwendor.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.nb2998.taskwendor.Database.CartDBContract.*;

public class CartDBHelper extends SQLiteOpenHelper{
    public CartDBHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String CREATE_TABLE = CREATE + TABLE_NAME + LBR +
//                COLUMN_NAME + TYPE_TEXT + COMMA +
//                COLUMN_PRICE + TYPE_INTEGER + COMMA +
//                COLUMN_TOTAL_UNITS +TYPE_INTEGER + COMMA +
//                COLUMN_LEFT_UNITS +TYPE_INTEGER +COMMA +
//                COLUMN_IMAGE_URL + TYPE_TEXT +
//                RBR + TERMINATE;
//        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
