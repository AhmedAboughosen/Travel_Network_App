package com.example.socialnetworkfortravellers.ViewLayer.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProfileTabsAdapter extends FragmentPagerAdapter {


    private final List<Fragment> mFragmentList ;


    public ProfileTabsAdapter(@NonNull FragmentManager fm) {
        super(fm);
        mFragmentList  = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        try {
            return mFragmentList.get(position);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }


    public List<Fragment> getFragments() {
        return mFragmentList;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}