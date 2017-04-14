package com.ericwei.project333.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ericwei.project333.data.OutfitContract.OutfitEntry;

/**
 * Created by ericwei on 2017-04-13.
 */

public class OutfitDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "outfit.db";
    private static final int DATABASE_VERSION = 1;

    public OutfitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "CREATE TABLE " + OutfitEntry.TABLE_NAME + " (" +
                OutfitEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                OutfitEntry.COLUMN_OUTFIT1 + " INTEGER, " +
                OutfitEntry.COLUMN_OUTFIT2 + " INTEGER, " +
                OutfitEntry.COLUMN_OUTFIT3 + " INTEGER, " +
                OutfitEntry.COLUMN_OUTFIT4 + " INTEGER, " +
                OutfitEntry.COLUMN_OUTFIT5 + " INTEGER, " +
                OutfitEntry.COLUMN_OUTFIT6 + " INTEGER, " +
                OutfitEntry.COLUMN_OUTFIT7 + " INTEGER, " +
                OutfitEntry.COLUMN_OUTFIT8 + " INTEGER, " +
                OutfitEntry.COLUMN_OUTFIT9 + " INTEGER, " +
                OutfitEntry.COLUMN_OUTFIT10 + " INTEGER);";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OutfitEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
