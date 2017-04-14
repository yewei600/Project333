package com.ericwei.project333.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ericwei on 2017-04-13.
 */

public class OutfitContract {

    public static final String AUTHORITY = "com.ericwei.project333";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_OUTFIT = "outfit";


    public static final class OutfitEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_OUTFIT).build();

        public static final String TABLE_NAME = "clothes";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_OUTFIT1 = "outfit1";
        public static final String COLUMN_OUTFIT2 = "outfit2";
        public static final String COLUMN_OUTFIT3 = "outfit3";
        public static final String COLUMN_OUTFIT4 = "outfit4";
        public static final String COLUMN_OUTFIT5 = "outfit5";
        public static final String COLUMN_OUTFIT6 = "outfit6";
        public static final String COLUMN_OUTFIT7 = "outfit7";
        public static final String COLUMN_OUTFIT8 = "outfit8";
        public static final String COLUMN_OUTFIT9 = "outfit9";
        public static final String COLUMN_OUTFIT10 = "outfit10";

    }
}
