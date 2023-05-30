package com.esprit.jobhunter.CompanyFragments;


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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.jobhunter.Adapters.ListApplicantsAdapter;
import com.esprit.jobhunter.Entity.User;
import com.esprit.jobhunter.Entity.User_Job;
import com.esprit.jobhunter.MyUtils.GlobalParams;
import com.esprit.jobhunter.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Listoffre_applicants extends Fragment {

    FragmentManager fragmentManager;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<User> users;
    private ArrayList<User_Job> usersjob;
    private RecyclerView.Adapter adapter;
    public int id;
    TextView count;
    String url;
    int size=0;
    RequestQueue queue;
    Button show;


    public Listoffre_applicants() {
        // Required empty public constructor
    }

    //---




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listoffre_applicants, container, false);
        //------------------------
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        //jobList = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.offers_applicants_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);


        getJsonArray();
        //------------------------
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return view;
    }

    private void getJsonArray() {
        if(getArguments().get("id") != null){
            id = (int) getArguments().get("id");
            url = GlobalParams.url + "/job/applicants/" +id;
        }
        else{
            id = (int) getArguments().get("id1");
            url = GlobalParams.url + "/internship/applicants/" +id;
        }
        System.out.println(url);
        users = new ArrayList<>();
        usersjob = new ArrayList<>();
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
                                    User user = new User();
                                    User_Job userjob = new User_Job();


                                    user.setId(jsonObject.getInt("id"));
                                    user.setName(jsonObject.getString("name"));
                                    user.setLast_name(jsonObject.getString("last_name"));
                                    user.setNationality(jsonObject.getString("nationality"));
                                    user.setPicture(jsonObject.getString("picture"));
                                    user.setEmail(jsonObject.getString("email"));
                                    user.setAdress(jsonObject.getString("adress"));
                                    user.setTel1(jsonObject.getString("tel1"));
                                    user.setTel2(jsonObject.getString("tel2"));
                                    user.setDescription(jsonObject.getString("description"));
                                    userjob.setCreation_date("creation_date");
                                    //-----------------------------------------
                                    //-----------------------------------------
                                    users.add(user);
                                    usersjob.add(userjob);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter = new ListApplicantsAdapter(getActivity(), users);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("MYYYLIST"+users);
                        count = (TextView) getView().findViewById(R.id.count);
                        count.setText(String.valueOf(users.size()));

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
