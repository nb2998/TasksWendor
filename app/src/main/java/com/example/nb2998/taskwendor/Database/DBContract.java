package com.example.nb2998.taskwendor.Database;

public interface DBContract {
    String CREATE=" CREATE TABLE ";  //leave spaces
    String COMMA=" , ";
    String LBR=" ( ";
    String RBR=" ) ";
    String TERMINATE=" ; ";
    String INT_PK_AUTOIC = " INTEGER PRIMARY KEY AUTOINCREMENT ";
    String TYPE_TEXT=" TEXT ";
    String TYPE_INTEGER = " INTEGER ";

    String TABLE_NAME="SAVED_ITEMS";
    String COLUMN_NAME="Name";
    String COLUMN_PRICE="Price";
    String COLUMN_TOTAL_UNITS="Total_Units";
    String COLUMN_LEFT_UNITS="Left_Units";
    String COLUMN_IMAGE_URL="Image_url";
}
