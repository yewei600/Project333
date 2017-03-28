package com.ericwei.project333.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ericwei on 2017-03-26.
 */

public class ClothesContract {

    public static final String AUTHORITY = "com.ericwei.project333";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_CLOTHES = "clothes";

    public static final class ClothesEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_CLOTHES).build();

        public static final String TABLE_NAME = "clothes";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_SUBCATEGORY = "subcategory";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_ID = "id";
    }

}
