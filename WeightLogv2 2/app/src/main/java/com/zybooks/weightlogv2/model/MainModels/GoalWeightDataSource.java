package com.zybooks.weightlogv2.model.MainModels;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zybooks.weightlogv2.model.DataTables;

public class GoalWeightDataSource {
    private static GoalWeightDataSource instance;
    private SQLiteDatabase database;

    private GoalWeightDataSource(Context context) {
        DataTables dbHelper = new DataTables(context);
        database = dbHelper.getWritableDatabase();
    }

    public static GoalWeightDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new GoalWeightDataSource(context);
        }
        return instance;
    }

    // Insert the new goal weight into the database
    public long insertGoalWeight(GoalWeightModel goalWeight) {
        ContentValues values = new ContentValues();
        values.put(DataTables.GoalWeightEntry.COLUMN_GOAL_WEIGHT, goalWeight.getGoalWeight());

        return database.insert(DataTables.GoalWeightEntry.TABLE_NAME, null, values);
    }

    // Delete all previous goal weights
    public void deleteAllGoalWeights() {
        database.delete(DataTables.GoalWeightEntry.TABLE_NAME, null, null);
    }

    public double getCurrentGoalWeight() {
        double currentGoalWeight = 0.0;

        String[] projection = { DataTables.GoalWeightEntry.COLUMN_GOAL_WEIGHT };
        try (Cursor cursor = database.query(
                DataTables.GoalWeightEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null,
                "1"
        )) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(DataTables.GoalWeightEntry.COLUMN_GOAL_WEIGHT);
                if (columnIndex != -1) {
                    currentGoalWeight = cursor.getDouble(columnIndex);
                }
            }
        }

        return currentGoalWeight;
    }
}