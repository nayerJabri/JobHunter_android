package com.esprit.jobhunter.MenuFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.esprit.jobhunter.Entity.Job;
import com.esprit.jobhunter.R;
import com.esprit.jobhunter.RecyclerViewsAdapters.FavoritesAdapter;
import com.esprit.jobhunter.SQLite.DatabaseHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<Job> jobList;
    private RecyclerView.Adapter adapter;
    ProgressBar progressBar1;
    private DatabaseHelper db;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }


    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        //------------------------
        jobList = new ArrayList<>();
        progressBar1 = (ProgressBar) view.findViewById(R.id.progressBar1);
        recyclerView = (RecyclerView) view.findViewById(R.id.favorites_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        //------------------------
        progressBar1.setVisibility(View.VISIBLE);
        db = new DatabaseHelper(getActivity());

        if(db.getJobsCount() !=0 ) {
            jobList = db.getAllJobs();
            adapter = new FavoritesAdapter(getActivity(), jobList);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            progressBar1.setVisibility(View.GONE);
        } else if((db.getJobsCount() == 0)){
            progressBar1.setVisibility(View.GONE);
            Toast.makeText(getActivity().getApplicationContext(),"No Favorites",Toast.LENGTH_LONG);
        }
        //------------------------
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return view;
    }

}
