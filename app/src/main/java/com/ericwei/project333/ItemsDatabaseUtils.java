package com.ericwei.project333;

import android.content.Context;
import android.database.Cursor;

import com.ericwei.project333.data.ClothesContract;

/**
 * Created by ericwei on 2017-04-15.
 */

public class ItemsDatabaseUtils {

    public static int numItemsInDatabase(Context context) {
        Cursor cursor = context.getContentResolver().query(ClothesContract.ClothesEntry.CONTENT_URI,
                new String[]{ClothesContract.ClothesEntry.COLUMN_ID},
                null,
                null,
                null);
        return cursor.getCount();
    }

    public static int numItemForCategory(Context context, String category) {
        Cursor cursor = context.getContentResolver().query(ClothesContract.ClothesEntry.CONTENT_URI,
                new String[]{ClothesContract.ClothesEntry.COLUMN_ID},
                ClothesContract.ClothesEntry.COLUMN_CATEGORY + "=?",
                new String[]{category},
                null);
        return cursor.getCount();
    }

}
