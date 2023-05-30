package com.esprit.jobhunter.CompanyFragments;

import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esprit.jobhunter.Adapters.PagerAddAdapter;
import com.esprit.jobhunter.R;

public class AddOffersFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAddAdapter pageAdapter;
    TabItem tabJobs;
    TabItem tabInternships;


    public static AddOffersFragment newInstance() {
        return new AddOffersFragment();
    }


    public AddOffersFragment() {
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
        pageAdapter = new PagerAddAdapter(this.getChildFragmentManager(), 2);
        //viewPager.setOffscreenPageLimit(0);
        //pageAdapter.notifyDataSetChanged();
        viewPager.setAdapter(pageAdapter);

        tabLayout.setupWithViewPager(viewPager);
        
        return view;
        //return inflater.inflate(R.layout.fragment_offers, container, false);
    }

}
