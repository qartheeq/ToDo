package com.maya.todo.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new AllTaskFragment();
    }

    @Override
    public int getCount() {
        return TaskSaving.getCategories().size();
    }

    @Override
    public int getItemPosition(Object item) {
        return POSITION_NONE;

    }

}
