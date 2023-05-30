package com.esprit.jobhunter.ApplicationsFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.esprit.jobhunter.Entity.User;
import com.esprit.jobhunter.MainActivity;
import com.esprit.jobhunter.MyUtils.GlobalParams;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.jobhunter.Entity.ApplicationJob;
import com.esprit.jobhunter.R;
import com.esprit.jobhunter.RecyclerViewsAdapters.ApplicationsJobsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApplicationsJobsFragment extends Fragment {

    //---
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<ApplicationJob> applicationJobList;
    private RecyclerView.Adapter adapter;
    ProgressBar progressBar1;

    String url = GlobalParams.url + "/getjobappbyuserid/";

    ApplicationJob job;
    RequestQueue queue;

    private User connectedUser;

    public ApplicationsJobsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_applications_jobs, container, false);
        //------------------------
        connectedUser = ((MainActivity)getActivity()).getConnectedUser();

        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        progressBar1 = (ProgressBar) view.findViewById(R.id.progressBar1);
        recyclerView = (RecyclerView) view.findViewById(R.id.application_job_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        getJsonArray();

        setHasOptionsMenu(true);
        return view;
    }

    private void getJsonArray() {
        progressBar1.setVisibility(View.VISIBLE);

        applicationJobList = new ArrayList<>();
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url+connectedUser.getId(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = response.getJSONArray("result");

                            //  System.out.println("RESPONSE: " + response.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    ApplicationJob applicationJob = new ApplicationJob();

                                    applicationJob.setId_applicant(jsonObject.getInt("id_user"));
                                    applicationJob.setId_job(jsonObject.getInt("id_job"));
                                    applicationJob.setCreation_date(jsonObject.getString("creation_date"));
                                    applicationJob.setLabel(jsonObject.getString("label"));
                                    applicationJob.setDescription(jsonObject.getString("description"));
                                    applicationJob.setStart_date(jsonObject.getString("start_date"));
                                    applicationJob.setContract_type(jsonObject.getString("contract_type"));
                                    applicationJob.setCareer_req(jsonObject.getString("career_req"));
                                    applicationJob.setId_company(jsonObject.getInt("user_id"));
                                    applicationJob.setName_company(jsonObject.getString("name"));
                                    applicationJob.setPicture_company(jsonObject.getString("picture"));
                                    //-----------------------------------------
                                    applicationJobList.add(applicationJob);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter = new ApplicationsJobsAdapter(getActivity(), applicationJobList);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            progressBar1.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //System.out.println("MYYYLIST"+jobList);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", error.getMessage());
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(jsonArrayRequest);
    }

}
