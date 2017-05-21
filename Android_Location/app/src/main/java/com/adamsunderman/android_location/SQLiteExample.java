package com.adamsunderman.android_location;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Adam on 5/20/2017.
 */

class SQLiteExample extends SQLiteOpenHelper {

    public SQLiteExample(Context context) {
        super(context, DBContract.mainTable.DB_NAME, null, DBContract.mainTable.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBContract.mainTable.SQL_CREATE_MAIN_TABLE);

        //ContentValues testValues = new ContentValues();
        //testValues.put(DBContract.mainTable.COLUMN_NAME_LON, 34);
        //testValues.put(DBContract.mainTable.COLUMN_NAME_LAT, 12);
        //testValues.put(DBContract.mainTable.COLUMN_NAME_TEXT, "Hello SQLite");
        //db.insert(DBContract.mainTable.TABLE_NAME,null,testValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBContract.mainTable.SQL_DROP_MAIN_TABLE);
        onCreate(db);
    }
}
