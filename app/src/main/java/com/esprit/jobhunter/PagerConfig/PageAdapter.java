package com.esprit.jobhunter.PagerConfig;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.esprit.jobhunter.OffersFragments.OffersInternshipsFragment;
import com.esprit.jobhunter.OffersFragments.OffersJobsFragment;


public class PageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public PageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new OffersJobsFragment();
            case 1:
                return new OffersInternshipsFragment();
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