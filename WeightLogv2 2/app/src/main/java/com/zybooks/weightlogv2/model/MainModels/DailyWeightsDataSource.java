package com.zybooks.weightlogv2.model.MainModels;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.zybooks.weightlogv2.model.DataTables;
import com.zybooks.weightlogv2.model.LoginModels.LoginInfoDataSource;

import java.util.ArrayList;
import java.util.List;

public class DailyWeightsDataSource {
    private static DailyWeightsDataSource instance;
    private SQLiteDatabase database;

    private DailyWeightsDataSource(Context context) {
        DataTables dbHelper = new DataTables(context);
        database = dbHelper.getWritableDatabase();
    }

    public static DailyWeightsDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new DailyWeightsDataSource(context);
        }
        return instance;
    }

    public long insertDailyWeight(DailyWeightsModel dailyWeight) {
        ContentValues values = new ContentValues();
        values.put(DataTables.DailyWeightEntry.COLUMN_WEIGHT, dailyWeight.getWeight());
        values.put(DataTables.DailyWeightEntry.COLUMN_DATE, dailyWeight.getDate());

        return database.insert(DataTables.DailyWeightEntry.TABLE_NAME, null, values);
    }

    public List<DailyWeightsModel> getAllDailyWeights() {
        List<DailyWeightsModel> dailyWeightsList = new ArrayList<>();

        String[] projection = {
                DataTables.DailyWeightEntry.COLUMN_DATE,
                DataTables.DailyWeightEntry.COLUMN_WEIGHT
        };

        try (Cursor cursor = database.query(
                DataTables.DailyWeightEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        )) {
            while (cursor.moveToNext()) {
                int dateIndex = cursor.getColumnIndex(DataTables.DailyWeightEntry.COLUMN_DATE);
                int weightIndex = cursor.getColumnIndex(DataTables.DailyWeightEntry.COLUMN_WEIGHT);

                String date = cursor.getString(dateIndex);
                String weight = cursor.getString(weightIndex);

                DailyWeightsModel dailyWeight = new DailyWeightsModel(weight, date);
                dailyWeightsList.add(dailyWeight);
            }
        }

        return dailyWeightsList;
    }
}