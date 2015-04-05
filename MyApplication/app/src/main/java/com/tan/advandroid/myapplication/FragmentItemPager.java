package com.tan.advandroid.myapplication;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tan.advandroid.myapplication.network.Network;

public class FragmentItemPager extends Fragment {

    private ViewPager mPager;

    private PageAdapter adapter;
    private ImageView icon;
    private static final int LOADER_ID = 0;
    private MyCursorAdapter mAdapter;

    public static FragmentItemPager getInstance(String title,String info, String imageUrl) {
        FragmentItemPager fragmentItemPager = new FragmentItemPager();
        Bundle arguments = new Bundle();
        arguments.putString(TechContract.TechEntry.COLUMN_NAME_TITLE, title);
        arguments.putString(TechContract.TechEntry.COLUMN_NAME_IMAGE, imageUrl);
        arguments.putString(TechContract.TechEntry.COLUMN_NAME_INFO, info);
        fragmentItemPager.setArguments(arguments);
        return fragmentItemPager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_pager,container, false);
        TextView title = (TextView) v.findViewById(R.id.tech_title);
        TextView info = (TextView) v.findViewById(R.id.tech_info);
        icon = (ImageView) v.findViewById(R.id.tech_icon);
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(getArguments().getString(TechContract.TechEntry.COLUMN_NAME_IMAGE));
        title.setText(getArguments().getString(TechContract.TechEntry.COLUMN_NAME_TITLE));
        info.setText(getArguments().getString(TechContract.TechEntry.COLUMN_NAME_INFO));
        return  v;
    }

    public class MyAsyncTask extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... string) {
            Network network = new Network();
            Bitmap bitmap = network.downloadImage(string[0]);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            icon.setImageBitmap(bitmap);
        }

    }
}
