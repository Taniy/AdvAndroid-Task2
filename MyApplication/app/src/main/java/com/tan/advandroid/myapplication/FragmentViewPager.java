package com.tan.advandroid.myapplication;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Admin on 15.03.2015.
 */
public class FragmentViewPager extends Fragment {
    private int count;
    private FragmentActivity fragmentActivity;

    public static FragmentViewPager getInstance(int _count, int position) {
        FragmentViewPager fragmentViewPager = new FragmentViewPager();
        Bundle arguments = new Bundle();
        arguments.putInt("count" , _count);
        arguments.putInt("position" , position);
        fragmentViewPager.setArguments(arguments);
        return fragmentViewPager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.fragment_view_pager, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);

        PageAdapter pageAdapter = new PageAdapter(fragmentActivity.getSupportFragmentManager(), getArguments().getInt("count"), getActivity());
        viewPager.setAdapter(pageAdapter);
        viewPager.setCurrentItem(getArguments().getInt("position"));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAttach(Activity activity) {
        fragmentActivity = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }




}
