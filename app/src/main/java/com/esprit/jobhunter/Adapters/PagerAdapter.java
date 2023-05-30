package com.esprit.jobhunter.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.esprit.jobhunter.CompanyFragments.ListoffersFragment;
import com.esprit.jobhunter.CompanyFragments.ListinternshipsFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private int numOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ListoffersFragment();
            case 1:
                return new ListinternshipsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Jobs";
            case 1:
                return "Internships";
        }
        return null;
    }
}
