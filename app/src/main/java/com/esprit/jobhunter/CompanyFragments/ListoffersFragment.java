package com.esprit.jobhunter.CompanyFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.jobhunter.Adapters.ListoffersAdapter;
import com.esprit.jobhunter.Entity.Job;
import com.esprit.jobhunter.Entity.User;
import com.esprit.jobhunter.MyUtils.GlobalParams;
import com.esprit.jobhunter.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListoffersFragment extends Fragment {
    FragmentManager fragmentManager;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<Job> jobList;
    private RecyclerView.Adapter adapter;
    public User user;
    RequestQueue queue;
    Button show;


    public ListoffersFragment() {
        // Required empty public constructor
    }

    //---




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listoffers, container, false);
        //------------------------
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        //jobList = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.offers_job_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);


        getJsonArray();
        System.out.println("JOBLIST" + jobList);

        //------------------------
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return view;
    }

    private void getJsonArray() {
        Intent fromLogin = getActivity().getIntent();
        user = (User) fromLogin.getSerializableExtra("connectedUser");
        String url = GlobalParams.url + "/job/" +user.getId();
        jobList = new ArrayList<>();
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
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
                                    //System.out.println("JSONOBJECT " + jsonObject);
                                    Job job = new Job();


                                    job.setId(jsonObject.getInt("id"));
                                    job.setLabel(jsonObject.getString("label"));
                                    job.setDescription(jsonObject.getString("description"));
                                    job.setStart_date(jsonObject.getString("start_date"));
                                    job.setContract_type(jsonObject.getString("contract_type"));
                                    job.setCareer_req(jsonObject.getString("career_req"));
                                    job.setUser_id(jsonObject.getString("user_id"));
                                    job.setSkills(jsonObject.getString("skills"));

                                    //-----------------------------------------
                                    //-----------------------------------------
                                    jobList.add(job);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter = new ListoffersAdapter(getActivity(), jobList);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("MYYYLIST"+jobList);
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
