package com.esprit.jobhunter.PagerConfig;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.esprit.jobhunter.ApplicationsFragments.ApplicationsInternshipsFragment;
import com.esprit.jobhunter.ApplicationsFragments.ApplicationsJobsFragment;

public class PageApplicationsAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public PageApplicationsAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ApplicationsJobsFragment();
            case 1:
                return new ApplicationsInternshipsFragment();
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
                return "Jobs Applications";
            case 1:
                return "Internships Applications";
        }
        return null;
    }

}
