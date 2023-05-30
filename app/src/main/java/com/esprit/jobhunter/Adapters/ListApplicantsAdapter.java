package com.esprit.jobhunter.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.jobhunter.Entity.User;
import com.esprit.jobhunter.Main2Activity;
import com.esprit.jobhunter.MainActivity;
import com.esprit.jobhunter.MyUtils.GlobalParams;
import com.esprit.jobhunter.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListApplicantsAdapter extends RecyclerView.Adapter<ListApplicantsAdapter.ViewHolder> {

    private Context context;
    private List<User> list ;
    Activity activity;
    public ListApplicantsAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.offer_applicant_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = list.get(position);
        String name = user.getName() + " " + user.getLast_name();
        holder.applicant_name.setText(name);
        holder.applicant_email.setText(user.getEmail());
        if (user.getPicture() != null) {
            //Picasso.with(context.getApplicationContext()).load("http://i.imgur.com/DvpvklR.png").into(holder.applicant_image);
            /*Picasso.with(this.context.getApplicationContext())
                    .load(user.getPicture())
                    .into(holder.applicant_image);*/
            /*ImageRequest imageLoad = new ImageRequest(GlobalParams.ressourceUrl+"/"+user.getPicture(), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    // callback
                    holder.applicant_image.setImageBitmap(response); }
            }, 100, 100, null, null);
            RequestQueue queueImage = Volley.newRequestQueue((Main2Activity)context);
            queueImage.add(imageLoad);*/
            if(user.getPicture().length()>60){
                Picasso.with((MainActivity)context).load(user.getPicture()).into(holder.applicant_image);
            } else {
                Picasso.with((MainActivity)context).load(GlobalParams.ressourceUrl+"/"+user.getPicture()).into(holder.applicant_image);
            }
        }
        if(user.getNationality() != null) {
            String nat = "https://www.countries-ofthe-world.com/flags-normal/flag-of-" + user.getNationality() + ".png";
            //Picasso.with(context.getApplicationContext()).load("http://i.imgur.com/DvpvklR.png").into(holder.applicant_natio);
            Picasso.with(this.context.getApplicationContext())
                    .load(nat)
                    .into(holder.applicant_natio);
        }


        //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        //holder.textCompanyName.setText(String.valueOf(job.getCompany().getName()));
        //holder.imgCompany.setImageResource(String.valueOf(job.getYear()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView applicant_name,applicant_email,Applicant_name;
        public CircleImageView applicant_natio;
        public CircleImageView applicant_image;
        public Button show;
        public ViewHolder(View itemView) {
            super(itemView);
            show = itemView.findViewById(R.id.show);
            applicant_image = itemView.findViewById(R.id.applicant_image);
            applicant_name = itemView.findViewById(R.id.applicant_name);
            applicant_natio = itemView.findViewById(R.id.applicant_natio);
            applicant_email = itemView.findViewById(R.id.applicant_email);

            //imgCompany = itemView.findViewById(R.id.job_image);
        }
    }

}