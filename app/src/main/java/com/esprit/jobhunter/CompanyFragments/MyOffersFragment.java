package com.esprit.jobhunter.CompanyFragments;

import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esprit.jobhunter.Adapters.PagerAdapter;
import com.esprit.jobhunter.R;

public class MyOffersFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter pageAdapter;
    TabItem tabJobs;
    TabItem tabInternships;


    public static MyOffersFragment newInstance() {
        return new MyOffersFragment();
    }


    public MyOffersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_offers, container, false);

        tabLayout = view.findViewById(R.id.tablayout);
        //tabJobs = view.findViewById(R.id.tabJobs);
        //tabInternships = view.findViewById(R.id.tabInternships);

        viewPager = view.findViewById(R.id.viewPager);

        //pageAdapter = new PageAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        pageAdapter = new PagerAdapter(this.getChildFragmentManager(), 2);
        //viewPager.setOffscreenPageLimit(0);
        //pageAdapter.notifyDataSetChanged();
        viewPager.setAdapter(pageAdapter);

        tabLayout.setupWithViewPager(viewPager);

        /*tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Inflate the layout for this fragment
        return view;
        //return inflater.inflate(R.layout.fragment_offers, container, false);
    }

}
