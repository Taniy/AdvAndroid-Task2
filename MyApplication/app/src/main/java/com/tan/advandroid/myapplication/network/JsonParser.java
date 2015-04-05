package com.tan.advandroid.myapplication.network;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.tan.advandroid.myapplication.MyContentProvider;
import com.tan.advandroid.myapplication.TechContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by asus on 03.04.2015.
 */
public class JsonParser {
    private Context context;
    //проверка на существование
    private static final String[] TECH_PROJECTION = {TechContract.TechEntry.COLUMN_NAME_ID};
    private static final String TECH_SELECTION = String.valueOf(TechContract.TechEntry.COLUMN_NAME_ID)+ " = ?";

    public JsonParser(Context _context) {
        context = _context;
    }

    public void parser(JSONObject json) throws JSONException {
        JSONObject object = json.getJSONObject("technology");
        JSONArray array = object.names();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < array.length(); i++) {
            JSONObject strings = object.getJSONObject(array.get(i).toString());
            contentValues.put(TechContract.TechEntry.COLUMN_NAME_ID, strings.getInt("id"));
            contentValues.put(TechContract.TechEntry.COLUMN_NAME_IMAGE, strings.getString("picture"));
            contentValues.put(TechContract.TechEntry.COLUMN_NAME_TITLE, strings.getString("title"));
            if (strings.has("info"))
                contentValues.put(TechContract.TechEntry.COLUMN_NAME_INFO,strings.getString("info") );
            else
                contentValues.put(TechContract.TechEntry.COLUMN_NAME_INFO, "" );
            Cursor cursor = context.getContentResolver().query(
                    MyContentProvider.TECH_CONTENT_URI,
                TECH_PROJECTION, TECH_SELECTION, new String[]{String.valueOf(strings.getInt("id"))}, null
                );
            if (cursor.getCount() == 0) {
                context.getContentResolver().insert(MyContentProvider.TECH_CONTENT_URI, contentValues);
            }
            cursor.close();
            contentValues.clear();
        }
    }
}
