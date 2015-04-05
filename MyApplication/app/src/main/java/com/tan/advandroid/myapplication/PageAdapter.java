package com.tan.advandroid.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.CursorLoader;

/**
 * Created by Admin on 10.03.2015.
 */
public class PageAdapter extends FragmentStatePagerAdapter {
    private Context context;
     int count;
    int id;
    private static final String[] TECH_PROJECTION = {TechContract.TechEntry.COLUMN_NAME_TITLE, TechContract.TechEntry.COLUMN_NAME_IMAGE,TechContract.TechEntry.COLUMN_NAME_INFO};
    private static final String TECH_SELECTION = String.valueOf(TechContract.TechEntry.COLUMN_NAME_ID)+ " = ?";

    public PageAdapter(FragmentManager fragmentManager, int _count,  Context _context) {
        super(fragmentManager);
        count = _count;
        context = _context;
    }

    @Override
    public Fragment getItem(int position) {
        String title="",info="",icon="";
        Cursor cursor = context.getContentResolver().query(
                MyContentProvider.TECH_CONTENT_URI,
                 TECH_PROJECTION, TechContract.TechEntry._ID +"=" + (position+1), null, null
                );
        if ( cursor != null) {
            cursor.moveToNext();
            title = cursor.getString(cursor.getColumnIndexOrThrow(TechContract.TechEntry.COLUMN_NAME_TITLE));
            icon = cursor.getString(cursor.getColumnIndexOrThrow(TechContract.TechEntry.COLUMN_NAME_IMAGE));
            info = cursor.getString(cursor.getColumnIndexOrThrow(TechContract.TechEntry.COLUMN_NAME_INFO));
        }
            return FragmentItemPager.getInstance(title,info,icon);
    }

    @Override
    public int getCount() {
        return count;
    }

}
