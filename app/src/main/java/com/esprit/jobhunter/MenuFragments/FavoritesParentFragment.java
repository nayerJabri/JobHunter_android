package com.esprit.jobhunter.MenuFragments;


import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esprit.jobhunter.PagerConfig.PageFavAdapter;
import com.esprit.jobhunter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesParentFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    PageFavAdapter pageAdapter;
    TabItem tabJobs;
    TabItem tabInternships;


    public FavoritesParentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites_parent, container, false);

        tabLayout = view.findViewById(R.id.tablayout);
        //tabJobs = view.findViewById(R.id.tabJobs);
        //tabInternships = view.findViewById(R.id.tabInternships);

        viewPager = view.findViewById(R.id.viewPager);

        //pageAdapter = new PageAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        pageAdapter = new PageFavAdapter(this.getChildFragmentManager(), 2);
        //viewPager.setOffscreenPageLimit(0);
        //pageAdapter.notifyDataSetChanged();
        viewPager.setAdapter(pageAdapter);

        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
