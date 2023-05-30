package com.esprit.jobhunter.OffersFragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.jobhunter.Entity.Job;
import com.esprit.jobhunter.Entity.User;
import com.esprit.jobhunter.MyUtils.GlobalParams;
import com.esprit.jobhunter.R;
import com.esprit.jobhunter.SQLite.DatabaseHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobDetailsFragment extends Fragment {

    Job job = new Job();
    Job x;
    String text="";
    TextView job_label;
    TextView start_date;
    TextView contract_type;
    int i;
    TagContainerLayout mTagContainerLayout;
    TextView career_req;
    TextView description;
    List<String> list = new ArrayList<>();
    ImageView cmp_pic;
    Button apply;
    Button show_profile;
    CheckBox bookmark;

    private NestedScrollView offerJobDetailNestedScrollView;
    private ProgressBar progressBarOfferJobDetail;

    User user;
    String url;
    RequestQueue queue;
    int applyVerify = 0;

    Boolean if_bookmarked;
    private List<Job> storageJobList = new ArrayList<>();
    private DatabaseHelper db;

    public JobDetailsFragment() {
    }

    public void setData(Job job){
        this.job = job;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job_details, container, false);

        i=1;

        offerJobDetailNestedScrollView = view.findViewById(R.id.nestedJobOfferDetails);
        progressBarOfferJobDetail = view.findViewById(R.id.progressBarJobOfferDetails);
        bookmark = (CheckBox) view.findViewById(R.id.bookmark);

        queue = Volley.newRequestQueue(getActivity());


        Intent fromLogin = getActivity().getIntent();
        user = (User) fromLogin.getSerializableExtra("connectedUser");

        job_label = view.findViewById(R.id.job_label);
        start_date = view.findViewById(R.id.start_date);
        contract_type = view.findViewById(R.id.contract_type);
        career_req = view.findViewById(R.id.career_req);
        description = view.findViewById(R.id.description);
        cmp_pic = view.findViewById(R.id.cmp_pic);
        apply = view.findViewById(R.id.apply);
        show_profile = view.findViewById(R.id.show_profile);

        job_label.setText(job.getLabel());
        start_date.setText(job.getStart_date());
        contract_type.setText(job.getContract_type());
        career_req.setText(job.getCareer_req());
        description.setText(job.getDescription());
        //Picasso.with(getActivity().getApplicationContext()).load(job.getCompany_pic()).into(cmp_pic);

        /*ImageRequest imageLoad = new ImageRequest(GlobalParams.ressourceUrl+"/"+job.getCompany_pic(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                // callback
                cmp_pic.setImageBitmap(response); }
        }, 100, 100, null, null);
        RequestQueue queueImage = Volley.newRequestQueue(getActivity().getApplicationContext());;
        queueImage.add(imageLoad);*/
        if(job.getCompany_pic().length()>60){
            Picasso.with(getActivity().getApplicationContext()).load(job.getCompany_pic()).into(cmp_pic);
        } else {
            Picasso.with(getActivity().getApplicationContext()).load(GlobalParams.ressourceUrl+"/"+job.getCompany_pic()).into(cmp_pic);
        }


        System.out.println("HHHHHHHHHHHHHHHHHHH+++: "+job);
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

        System.out.println("NAAAAAAAAAAAAAAAAAAAME: "+job.getCompany_name());
        System.out.println("NAAAAAAAAAAAAAAAAAAAME: "+job.getCompany_pic());

        verifyApplications();


        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(applyVerify == 0){
                    progressBarOfferJobDetail.setVisibility(View.VISIBLE);
                    url = GlobalParams.url + "/addappjob/"+String.valueOf(user.getId())+"/"+job.getId();
                    StringRequest getData = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                System.out.println(obj);
                                if (obj.getInt("success") == 1) {
                                    apply.setEnabled(false);
                                }
                                progressBarOfferJobDetail.setVisibility(View.GONE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                //Log.e("JSONExeption", e.getMessage());
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            //Log.e("ErrorResponse", error.getMessage());
                        }
                    });
                    queue.add(getData);
                    url = "";
                    apply.setEnabled(false);
                }
            }
        });

        //------Bookmark checked
        db = new DatabaseHelper(getActivity());
        if_bookmarked = false;
        bookmark.setChecked(false);
        storageJobList.addAll(db.getAllJobs());
        for (Job jobItem : storageJobList) {
            if(jobItem.getId() == job.getId()){
                if_bookmarked = true;
                bookmark.setChecked(true);
            }
        }

        bookmark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    //db.deleteDB();

                    db.insertJob(job);
                    bookmark.setChecked(true);
                } else if(isChecked == false){
                    db.deleteJob(job);
                    bookmark.setChecked(false);
                }
            }
        });


        return view;
    }

    public void verifyApplications(){
        progressBarOfferJobDetail.setVisibility(View.VISIBLE);

        url = GlobalParams.url + "/getjobappbyuser/"+String.valueOf(user.getId())+"/"+job.getId();
        StringRequest getData = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getInt("success") == 1) {
                        apply.setEnabled(false);
                        applyVerify = 1;
                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme).create();
                        alertDialog.setTitle("Information");
                        alertDialog.setMessage("You have already applied to this job offer");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                    if (obj.getInt("success") == 0) {
                        applyVerify = 0;
                    }
                    progressBarOfferJobDetail.setVisibility(View.GONE);
                    offerJobDetailNestedScrollView.setAlpha(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Log.e("JSONExeption", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //Log.e("ErrorResponse", error.getMessage());
            }
        });
        queue.add(getData);
        url = "";
    }


}
