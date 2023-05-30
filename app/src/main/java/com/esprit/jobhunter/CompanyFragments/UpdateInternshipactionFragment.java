package com.esprit.jobhunter.CompanyFragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.jobhunter.Entity.Internship;
import com.esprit.jobhunter.Entity.User;
import com.esprit.jobhunter.Main2Activity;
import com.esprit.jobhunter.MyUtils.GlobalParams;
import com.esprit.jobhunter.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateInternshipactionFragment extends Fragment {
    RequestQueue queue;
    Button submit,delete,add;
    String url1,url2;
    TagContainerLayout mTagContainerLayout;
    String text="";
    int i=1;
    List<String> list = new ArrayList<>();
    User user;
    EditText Title,Description,date_begin,educ_req,duration,skills;

    public UpdateInternshipactionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Main2Activity myActivity = (Main2Activity) getActivity();
        Internship internship = (Internship) getArguments().get("internship");
        System.out.println("internship" + internship);
        Intent fromLogin = getActivity().getIntent();
        user = (User) fromLogin.getSerializableExtra("connectedUser");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_updateinternshipaction, container, false);
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        url1 = GlobalParams.url+"/updateinternship/";
        url2 = GlobalParams.url+"/deleteinternship/";
        Title = (EditText) view.findViewById(R.id.Title);
        Description = (EditText) view.findViewById(R.id.Description);
        date_begin = (EditText) view.findViewById(R.id.date_begin);
        educ_req = (EditText) view.findViewById(R.id.educ_req);
        submit = (Button) view.findViewById(R.id.submit);
        mTagContainerLayout = (TagContainerLayout) view.findViewById(R.id.tagcontainerLayout);
        skills = (EditText) view.findViewById(R.id.skills);
        add = (Button) view.findViewById(R.id.add);
        duration = (EditText) view.findViewById(R.id.duration);
        Title.setText(internship.getLabel());
        Description.setText(internship.getDescription());

        String tmp_str = internship.getStart_date().replace("%", " ");
        date_begin.setText(tmp_str);

        educ_req.setText(internship.getEduc_req());
        duration.setText(internship.getDuration());
        while (i<internship.getSkills().length()){
            if((!(String.valueOf((internship.getSkills()).charAt(i)).equals(",")))&&(!(String.valueOf((internship.getSkills()).charAt(i)).equals("]")))){
                text = text + (internship.getSkills()).charAt(i);
                System.out.println("textz  :" +text);
            }
            else{
                list.add(text);
                System.out.println("list1"+list);
                text="";
            }
            i++;
        }

        System.out.println("list2"+list);

        mTagContainerLayout = (TagContainerLayout) view.findViewById(R.id.tagcontainerLayout);
        mTagContainerLayout.setTags(list);
        add.setOnClickListener(v->{
            System.out.println(skills.getText());
            list.add((skills.getText()).toString());
            mTagContainerLayout.setTags(list);
            skills.setText(null);
        });
        mTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {

            @Override
            public void onTagClick(int position, String text) {
                AlertDialog.Builder a_builder =  new AlertDialog.Builder(getActivity(),R.style.MyDialogTheme);
                a_builder.setMessage("Are you want delete this?");
                a_builder.setCancelable(false);
                a_builder.setPositiveButton("Yes", ((DialogInterface dialog, int which) -> {
                    mTagContainerLayout.removeTag(position);
                    list.remove(position);
                    dialog.cancel();
                }));
                a_builder.setNegativeButton("cancel", (DialogInterface dialog, int which) -> {
                    dialog.cancel();
                });
                AlertDialog alert = a_builder.create();
                alert.setTitle("Alert!");
                alert.show();

                System.out.println("zzz");
            }

            @Override
            public void onTagLongClick(final int position, String text) {
                // ...
            }

            public void onSelectedTagDrag(int position, String text){
                // ...
            }

            @Override
            public void onTagCrossClick(int position) {
                // ...
            }
        });
        delete = (Button) view.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url2 += internship.getId();
                StringRequest getData = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getInt("success") == 1) {
                                Toast.makeText(getActivity().getApplicationContext(), "update succeeded !",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Log.e("JSONExeption", e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ErrorResponse", error.getMessage());
                    }
                });
                queue.add(getData);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                Fragment myFragment = new MyOffersFragment();
                myFragment.setArguments(bundle);
                myActivity.getSupportFragmentManager().beginTransaction().replace(R.id.flContent,myFragment).addToBackStack(null).commit();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp_str_2 = date_begin.getText().toString().replace(" ", "%20");

                url1 +=  internship.getId() + "/" + Title.getText() + "/" + Description.getText() +"/"+ tmp_str_2.replace("/","%2F")+"/"+ educ_req.getText() +"/"+ mTagContainerLayout.getTags() +"/"+ duration.getText()  ;
                //System.out.println(url1);
                StringRequest getData = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getInt("success") == 1) {
                                Toast.makeText(getActivity().getApplicationContext(), "update succeeded !",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Log.e("JSONExeption", e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                });
                queue.add(getData);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                Fragment myFragment = new MyOffersFragment();
                myFragment.setArguments(bundle);
                myActivity.getSupportFragmentManager().beginTransaction().replace(R.id.flContent,myFragment).addToBackStack(null).commit();
            }
        });
        return view;
    }
}


