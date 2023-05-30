package com.esprit.jobhunter.CompanyFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.esprit.jobhunter.RecyclerViewsAdapters.ContactsCompanyAdapter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.jobhunter.Entity.User;
import com.esprit.jobhunter.Main2Activity;
import com.esprit.jobhunter.MyUtils.GlobalParams;
import com.esprit.jobhunter.MyUtils.SimpleDividerItemDecoration;
import com.esprit.jobhunter.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyContactFragment extends Fragment {

    private Button btn;
    private EditText nickname;
    public static final String NICKNAME = "usernickname";

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<User> userList;
    private RecyclerView.Adapter adapter;
    ProgressBar progressBar1;
    String url = GlobalParams.url + "/getapprouvedjobappbycompany/";
    RequestQueue queue;



    public CompanyContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        url+=((Main2Activity)getActivity()).getConnectedUser().getId();

        //---------------
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        //jobList = new ArrayList<>();

        progressBar1 = (ProgressBar) view.findViewById(R.id.progressBar1);
        recyclerView = (RecyclerView) view.findViewById(R.id.contacts_list);
        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(
                getActivity().getApplicationContext()
        ));

        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        getJsonArray();
        //---------------

        //call UI components  by id
        /*btn = (Button) view.findViewById(R.id.enterchat) ;
        nickname = (EditText) view.findViewById(R.id.nickname);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the nickname is not empty go to chatbox activity and add the nickname to the intent extra

                if(!nickname.getText().toString().isEmpty()){
                    Fragment fragment = new ChatBoxFragment();
                    ((ChatBoxFragment) fragment).setData(nickname.getText().toString());
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
                }
            }
        });*/

        setHasOptionsMenu(true);
        return view;
    }

    private void getJsonArray() {
        progressBar1.setVisibility(View.VISIBLE);

        userList = new ArrayList<>();
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

                                    user.setId(jsonObject.getInt("id_user"));
                                    user.setPicture(jsonObject.getString("picture"));
                                    user.setType(jsonObject.getString("type"));
                                    user.setName(jsonObject.getString("name"));
                                    user.setLast_name(jsonObject.getString("last_name"));
                                    //-----------------------------------------
                                    userList.add(user);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter = new ContactsCompanyAdapter(getActivity(), userList);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            progressBar1.setVisibility(View.GONE);
                            url = GlobalParams.url + "/getapprouvedjobappbycompany/";
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
