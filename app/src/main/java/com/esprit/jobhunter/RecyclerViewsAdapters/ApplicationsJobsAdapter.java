package com.esprit.jobhunter.RecyclerViewsAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.esprit.jobhunter.Entity.ApplicationJob;
import com.esprit.jobhunter.MainActivity;
import com.esprit.jobhunter.MyUtils.GlobalParams;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.jobhunter.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ApplicationsJobsAdapter extends RecyclerView.Adapter<ApplicationsJobsAdapter.ViewHolder> {

    private Context context;
    private List<ApplicationJob> list ;

    public ApplicationsJobsAdapter(Context context, ArrayList<ApplicationJob> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ApplicationsJobsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.offer_job_list_item, parent, false);
        return new ApplicationsJobsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ApplicationsJobsAdapter.ViewHolder holder, int position) {
        ApplicationJob job = list.get(position);
        final int x = position;

        holder.textLabel.setText(job.getLabel());
        //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        holder.textCompanyName.setText(String.valueOf(job.getName_company()));
        //Picasso.with((MainActivity)context).load(job.getPicture_company()).into(holder.imgCompany);

        /*ImageRequest imageLoad = new ImageRequest(GlobalParams.ressourceUrl+"/"+job.getPicture_company(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                // callback
                holder.imgCompany.setImageBitmap(response); }
        }, 100, 100, null, null);
        RequestQueue queueImage = Volley.newRequestQueue((MainActivity)context);;
        queueImage.add(imageLoad);*/
        if(job.getPicture_company().length()>60){
            Picasso.with((MainActivity)context).load(job.getPicture_company()).into(holder.imgCompany);
        } else {
            Picasso.with((MainActivity)context).load(GlobalParams.ressourceUrl+"/"+job.getPicture_company()).into(holder.imgCompany);
        }


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Fragment fragment = new JobDetailsFragment();
                ((JobDetailsFragment) fragment).setData(list.get(x));
                //FragmentManager fragmentManager = v.getContext()
                ((MainActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
                */
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textLabel, textCompanyName;
        public ImageView imgCompany;
        public RelativeLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            textLabel = itemView.findViewById(R.id.job_label);
            textCompanyName = itemView.findViewById(R.id.company_name);
            imgCompany = itemView.findViewById(R.id.job_image);
            layout = itemView.findViewById(R.id.ojli_layout);
        }
    }
}
