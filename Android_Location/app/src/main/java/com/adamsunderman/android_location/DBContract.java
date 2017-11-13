package com.adamsunderman.android_location;

import android.provider.BaseColumns;



final class DBContract {
    private DBContract(){};

    public final class mainTable implements BaseColumns {
        public static final String DB_NAME = "loc_db";
        public static final String TABLE_NAME = "loc_table";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_LAT = "lat";
        public static final String COLUMN_NAME_LON = "lon";
        public static final int DB_VERSION = 4;


        public static final String SQL_CREATE_MAIN_TABLE = "CREATE TABLE " +
                mainTable.TABLE_NAME + "(" + mainTable._ID + " INTEGER PRIMARY KEY NOT NULL," +
                mainTable.COLUMN_NAME_TEXT + " VARCHAR(255)," +
                mainTable.COLUMN_NAME_LAT + " DOUBLE," +
                mainTable.COLUMN_NAME_LON + " DOUBLE);";

        public static final String SQL_TEST_MAIN_TABLE_INSERT = "INSERT INTO " + TABLE_NAME +
                " (" + COLUMN_NAME_TEXT + "," + COLUMN_NAME_LAT + "," + COLUMN_NAME_LON + ") VALUES ('test', 123, 456);";

        public  static final String SQL_DROP_MAIN_TABLE = "DROP TABLE IF EXISTS " + mainTable.TABLE_NAME;
    }
}
