package com.esprit.jobhunter.OffersFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.esprit.jobhunter.Entity.Internship;
import com.esprit.jobhunter.Entity.User;
import com.esprit.jobhunter.MyUtils.GlobalParams;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.jobhunter.R;
import com.esprit.jobhunter.RecyclerViewsAdapters.OffersInternshipsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OffersInternshipsFragment extends Fragment {

    //---
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<Internship> internshipList;
    private RecyclerView.Adapter adapter;
    ProgressBar progressBar1;

    String url = GlobalParams.url + "/internships";

    Internship internship;
    RequestQueue queue;
    String url2 = GlobalParams.url + "/getuser";
    User companyUser;


    public OffersInternshipsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_offers_internships, container, false);
        //------------------------
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        //jobList = new ArrayList<>();

        progressBar1 = (ProgressBar) view.findViewById(R.id.progressBar1);
        recyclerView = (RecyclerView) view.findViewById(R.id.offers_internship_list);
        recyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        getJsonArray();

        System.out.println("JOBLIST" + internshipList);

        //------------------------
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return view;
    }

    private void getJsonArray() {
        progressBar1.setVisibility(View.VISIBLE);

        internshipList = new ArrayList<>();
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
                                    Internship internship = new Internship();


                                    internship.setId(jsonObject.getInt("id"));
                                    internship.setLabel(jsonObject.getString("label"));
                                    internship.setDescription(jsonObject.getString("description"));
                                    internship.setStart_date(jsonObject.getString("start_date"));
                                    internship.setDuration(jsonObject.getString("duration"));
                                    internship.setEduc_req(jsonObject.getString("educ_req"));
                                    internship.setSkills(jsonObject.getString("skills"));
                                    internship.setUser_id(jsonObject.getString("user_id"));
                                    internship.setCompany_name(jsonObject.getString("name"));
                                    internship.setCompany_pic(jsonObject.getString("picture"));

                                    //-----------------------------------------
                                    //-----------------------------------------
                                    internshipList.add(internship);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter = new OffersInternshipsAdapter(getActivity(), internshipList);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            progressBar1.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //System.out.println("MYYYLIST"+internshipList);
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
