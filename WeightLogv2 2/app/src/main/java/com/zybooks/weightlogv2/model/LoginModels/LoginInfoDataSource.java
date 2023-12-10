package com.zybooks.weightlogv2.model.LoginModels;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zybooks.weightlogv2.model.DataTables;

public class LoginInfoDataSource {
    private static LoginInfoDataSource instance;
    private SQLiteDatabase database;

    //sets database variable equal to the tables created in DataTables file
    private LoginInfoDataSource(Context context) {
        DataTables dbHelper = new DataTables(context);
        database = dbHelper.getWritableDatabase();
    }

    public static LoginInfoDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new LoginInfoDataSource(context);
        }
        return instance;
    }

    // Function uses the model to send the attributes to the LoginInfo datatable
    //Used in create new account fragment
    public long insertUser(LoginInfoModel user) {
        ContentValues values = new ContentValues();
        values.put(DataTables.LoginInfo.COLUMN_USERNAME, user.getUsername());
        values.put(DataTables.LoginInfo.COLUMN_PASSWORD, user.getPassword());

        return database.insert(DataTables.LoginInfo.TABLE_NAME, null, values);
    }

    //Used in user login layout
    public boolean evaluateUserCredentials(String username, String password) {
        String[] projection = { DataTables.LoginInfo.COLUMN_USERNAME };
        String selection = DataTables.LoginInfo.COLUMN_USERNAME + " = ? AND " +
                DataTables.LoginInfo.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = { username, password };

        try (Cursor cursor = database.query(
                DataTables.LoginInfo.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            return cursor.moveToFirst();
        }
    }

}