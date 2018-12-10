package com.zzi.testdemo;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> FragmentlistTitles = new ArrayList<>();

    ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);

    }

    @Override
    public int getCount() {
        return FragmentlistTitles.size();
    }
    

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return FragmentlistTitles.get(position);
    }
    void AddFragment(Fragment fragment, String title){
        fragmentList.add(fragment);
        FragmentlistTitles.add(title);
    }
}
