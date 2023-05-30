package com.esprit.jobhunter.CompanyFragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.jobhunter.Entity.Job;
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
public class UpdateactionFragment extends Fragment {
    RequestQueue queue;
    Button submit,delete,add;
    String url1,url2;
    int i=1;  User user;
    String text="",type;
    TagContainerLayout mTagContainerLayout;
    EditText Title,Description,date_begin,experience,skills;
    Spinner content_type;
    Fragment fragment = null;
    List<String> list = new ArrayList<>();
    public UpdateactionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Main2Activity myActivity = (Main2Activity) getActivity();
        Job job = (Job) getArguments().get("job");
        System.out.println("job" + job);
        Intent fromLogin = getActivity().getIntent();
        user = (User) fromLogin.getSerializableExtra("connectedUser");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_updateaction, container, false);
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        url1 = GlobalParams.url+"/updatejob/";
        url2 = GlobalParams.url+"/deletejob/";
        Title = (EditText) view.findViewById(R.id.Title);
        Description = (EditText) view.findViewById(R.id.Description);
        date_begin = (EditText) view.findViewById(R.id.date_begin);
        experience = (EditText) view.findViewById(R.id.experience);
        skills = (EditText) view.findViewById(R.id.skills);
        submit = (Button) view.findViewById(R.id.submit);
        content_type = (Spinner) view.findViewById(R.id.content_type);
        Title.setText(job.getLabel());
        experience.setText(job.getCareer_req());
        Description.setText(job.getDescription());

        String tmp_str = job.getStart_date().replace("%", " ");

        date_begin.setText(tmp_str);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.options_arrays, R.layout.spinner_item);
        content_type.setAdapter(adapter);
        Title.setHint("Title of offre");
        Title.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Title.setHint("");
                return false;
            }
        });

        Title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Title.setHint("Title of offre");
                }
            }
        });
        Description.setHint("Description of offre");
        Description.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Description.setHint("");
                return false;
            }
        });

        Description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Description.setHint("Description of offre");
                }
            }
        });
        date_begin.setHint("Date begin of the offer");
        date_begin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                date_begin.setHint("");
                return false;
            }
        });

        date_begin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    date_begin.setHint("Date begin of the offer");
                }
            }
        });
        experience.setHint("Experience for the offre");
        experience.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                experience.setHint("");
                return false;
            }
        });

        experience.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    experience.setHint("Experience for the offre");
                }
            }
        });
        skills.setHint("Skills required");
        skills.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                skills.setHint("");
                return false;
            }
        });

        skills.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    skills.setHint("Skills required");
                }
            }
        });
        add = (Button) view.findViewById(R.id.add);
        while (i<job.getSkills().length()){
            if((!(String.valueOf((job.getSkills()).charAt(i)).equals(",")))&&(!(String.valueOf((job.getSkills()).charAt(i)).equals("]")))){
                text = text + (job.getSkills()).charAt(i);
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
                url2 += job.getId();
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
                if(content_type.getSelectedItem().toString().equals("Permenant"))
                {
                    type = job.getContract_type();
                }
                else {type = content_type.getSelectedItem().toString();}
                String tmp_str_2 = date_begin.getText().toString().replace(" ", "%20");
                String dd = mTagContainerLayout.getTags().toString();
                url1 +=  job.getId() + "/" + Title.getText() + "/" + Description.getText() + "/" + tmp_str_2.replace("/","%2F") + "/" + type + "/" + experience.getText() + "/" + dd;
                StringRequest getData = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getInt("success") == 1) {
                                Toast.makeText(getActivity().getApplicationContext(), "Ligne inserré !", Toast.LENGTH_LONG).show();
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


