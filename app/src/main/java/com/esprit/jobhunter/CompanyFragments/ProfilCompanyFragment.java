package com.esprit.jobhunter.CompanyFragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.jobhunter.Entity.User;
import com.esprit.jobhunter.Main2Activity;
import com.esprit.jobhunter.MyUtils.GlobalParams;
import com.esprit.jobhunter.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilCompanyFragment extends Fragment {

    TextView tvName,tvAddress,email,contact,description;
    ImageView civProfilePic;
    User user;
    String test1="",test2="",test3="";
    RequestQueue queue;
    private ProgressBar progressBarProfile;


    public ProfilCompanyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profil_company, container, false);

        progressBarProfile = (ProgressBar) view.findViewById(R.id.progressBarProfil);
        progressBarProfile.setVisibility(View.VISIBLE);

        View photoHeader = view.findViewById(R.id.photoHeader);
        photoHeader.setTranslationZ(6);
        photoHeader.invalidate();


        tvName = (TextView) view.findViewById(R.id.tvName);
        tvAddress = (TextView) view.findViewById(R.id.tvAddress);
        email = (TextView) view.findViewById(R.id.email);
        contact = (TextView) view.findViewById(R.id.contact);
        description = (TextView) view.findViewById(R.id.description);
        civProfilePic = (ImageView) view.findViewById(R.id.civProfilePic);
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        user = new User();
        user = ((Main2Activity)getActivity()).getConnectedUser();

        tvName.setText(user.getName());
        tvAddress.setText(user.getAdress());
        email.setText(user.getEmail());

        if(user.getDescription() != null && !user.getDescription().equals("null")) {
            description.setText(user.getDescription());
        }else{
            description.setText("");
        }


        String cont = "";

        if(user.getTel1() != null && !user.getTel1().equals("null")) {
            test1 = "Tel1: " + user.getTel1();
        }
        if(user.getTel2() != null && !user.getTel2().equals("null")) {
            test2 = "Tel2: " + user.getTel2();
        }
        if(user.getFax() != null && !user.getFax().equals("null")) {
            test3 = "Fax: " + user.getFax();
        }

        if(test1.isEmpty() && test2.isEmpty() && test3.isEmpty())
            cont = cont+"";

        if(!test1.isEmpty() && !test2.isEmpty() && !test3.isEmpty())
            cont = cont+test1+" - "+test2+" - "+test3;

        if(!test1.isEmpty() && test2.isEmpty() && test3.isEmpty())
            cont = cont+test1;

        if(test1.isEmpty() && test2.isEmpty() && !test3.isEmpty())
            cont = cont+test3;

        if(test1.isEmpty() && !test2.isEmpty() && test3.isEmpty())
            cont = cont+test2;

        if(!test1.isEmpty() && !test2.isEmpty() && test3.isEmpty())
            cont = cont+test1+" - "+test2;

        if(test1.isEmpty() && !test2.isEmpty() && !test3.isEmpty())
            cont = cont+test2+" - "+test3;

        if(!test1.isEmpty() && test2.isEmpty() && !test3.isEmpty())
            cont = cont+test1+" - "+test3;

        contact.setText(cont);

        if (user.getPicture() != null) {
            //Picasso.with(this.getActivity().getApplicationContext()).load(user.getPicture()).into(civProfilePic);
            /*ImageRequest imageLoad = new ImageRequest(GlobalParams.ressourceUrl+"/"+user.getPicture(), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    // callback
                    civProfilePic.setImageBitmap(response); }
            }, 100, 100, null, null);
            RequestQueue queueImage = Volley.newRequestQueue(getActivity().getApplicationContext());;
            queueImage.add(imageLoad);*/
            if(user.getPicture().length()>60){
                Picasso.with(getActivity().getApplicationContext()).load(user.getPicture()).into(civProfilePic);
            } else {
                Picasso.with(getActivity().getApplicationContext()).load(GlobalParams.ressourceUrl+"/"+user.getPicture()).into(civProfilePic);
            }
        }

        progressBarProfile.setVisibility(View.GONE);


        return view;
    }

}
