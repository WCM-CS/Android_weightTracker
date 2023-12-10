package com.zybooks.weightlogv2.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DataTables extends SQLiteOpenHelper {
    //Set the database name
    private static final String DATABASE_NAME = "weight_log.db";
    private static final int DATABASE_VERSION = 1;

    public DataTables(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTables(sqLiteDatabase);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void createTables(SQLiteDatabase db) {
        // Create Login info table
        db.execSQL("CREATE TABLE " + LoginInfo.TABLE_NAME + " (" +
                LoginInfo._ID + " INTEGER PRIMARY KEY," +
                LoginInfo.COLUMN_USERNAME + " TEXT," +
                LoginInfo.COLUMN_PASSWORD + " TEXT)");

        // Create users daily weights table
        db.execSQL("CREATE TABLE " + DailyWeightEntry.TABLE_NAME + " (" +
                DailyWeightEntry._ID + " INTEGER PRIMARY KEY," +
                DailyWeightEntry.COLUMN_WEIGHT + " REAL," +
                DailyWeightEntry.COLUMN_DATE + " TEXT)");

        // Create goal weight table
        db.execSQL("CREATE TABLE " + GoalWeightEntry.TABLE_NAME + " (" +
                GoalWeightEntry._ID + " INTEGER PRIMARY KEY," +
                GoalWeightEntry.COLUMN_GOAL_WEIGHT + " REAL)");
    }

    // User login information table
    public static abstract class LoginInfo implements BaseColumns {
        public static final String TABLE_NAME = "login_info";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
    }

    // Users daily weights table
    public static abstract class DailyWeightEntry implements BaseColumns {
        public static final String TABLE_NAME = "daily_weights";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_DATE = "date";
    }

    // Goal weight table
    public static abstract class GoalWeightEntry implements BaseColumns {
        public static final String TABLE_NAME = "goal_weight";
        public static final String COLUMN_GOAL_WEIGHT = "goal_weight";
    }
}
