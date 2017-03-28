package com.ericwei.project333.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ericwei.project333.data.ClothesContract.ClothesEntry;

/**
 * Created by ericwei on 2017-03-26.
 */

public class ClothesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "clothes.db";

    private static final int DATABASE_VERSION = 1;

    public ClothesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "CREATE TABLE " + ClothesEntry.TABLE_NAME + " (" +
                ClothesEntry.COLUMN_CATEGORY + " TEXT NOT NULL, " +
                ClothesEntry.COLUMN_SUBCATEGORY + " TEXT NOT NULL, " +
                ClothesEntry.COLUMN_IMAGE + " BLOB NOT NULL, " +
                ClothesEntry.COLUMN_ID + " INTEGER PRIMARY KEY);";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ClothesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
