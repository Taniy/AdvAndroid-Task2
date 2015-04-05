package com.tan.advandroid.myapplication;

import android.provider.BaseColumns;

/**
 * Created by asus on 02.04.2015.
 */
public class TechContract {
    public TechContract() {}

    public static abstract  class TechEntry implements BaseColumns {
        public static final String TABLE_NAME = "technologies";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_IMAGE = "picture";
        public static final String COLUMN_NAME_INFO = "info";
    }
}
