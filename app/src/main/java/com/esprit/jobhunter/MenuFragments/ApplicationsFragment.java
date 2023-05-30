package com.esprit.jobhunter.MenuFragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esprit.jobhunter.PagerConfig.PageApplicationsAdapter;
import com.esprit.jobhunter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApplicationsFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    PageApplicationsAdapter pageAdapter;

    public static ApplicationsFragment newInstance() {
        return new ApplicationsFragment();
    }


    public ApplicationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_applications, container, false);

        tabLayout = view.findViewById(R.id.tablayout);

        viewPager = view.findViewById(R.id.viewPager);

        pageAdapter = new PageApplicationsAdapter(this.getChildFragmentManager(), 2);

        viewPager.setAdapter(pageAdapter);

        tabLayout.setupWithViewPager(viewPager);

        return view;

    }

}
