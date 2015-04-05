package com.tan.advandroid.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.tan.advandroid.myapplication.network.Network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class FragmentList extends Fragment implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    private ListView groupList;
    private Button addBtn;
    private Fragment self;
    private MainActivity mCallback;

    private static final int LOADER_ID = 1;
    private MyCursorAdapter mAdapter;
    public static FragmentList getInstance() {
        FragmentList fragmentGroups = new FragmentList();
        return fragmentGroups;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (MainActivity)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getLoaderManager().initLoader(LOADER_ID, null, this);
        return inflater.inflate(R.layout.fragment_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        self=this;
        groupList = (ListView) view.findViewById(R.id.list_technologies);
        groupList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                FragmentViewPager fragmentViewPager = FragmentViewPager.getInstance(mAdapter.getCursor().getCount(), position);
                mCallback.addFragment(fragmentViewPager);
            }
        });
    }

    private static final String[] PROJECTION = new String[] {
            TechContract.TechEntry._ID,
            MyContentProvider.TECH_ID,
            MyContentProvider.TECH_TITLE,
            MyContentProvider.TECH_IMAGE,
            MyContentProvider.TECH_INFO,
    };

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), MyContentProvider.TECH_CONTENT_URI, PROJECTION, null, null, null);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case LOADER_ID:
                mAdapter = new MyCursorAdapter(getActivity(), cursor, 0);
                groupList.setAdapter(mAdapter);
                break;
        }

    }
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

   @Override
    public void onClick(View v) {
    }
}
