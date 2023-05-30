package com.esprit.jobhunter.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.esprit.jobhunter.CompanyFragments.AddoffreFragment;
import com.esprit.jobhunter.CompanyFragments.AddoffreinternshipFragment;

public class PagerAddAdapter extends FragmentPagerAdapter {
    private int numOfTabs;

    public PagerAddAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AddoffreFragment();
            case 1:
                return new AddoffreinternshipFragment();
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
                return "Job";
            case 1:
                return "Internship";
        }
        return null;
    }
}
