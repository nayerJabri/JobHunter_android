package com.esprit.jobhunter.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.esprit.jobhunter.CompanyFragments.Listoffre_applicants;
import com.esprit.jobhunter.CompanyFragments.UpdateactionFragment;
import com.esprit.jobhunter.Entity.Job;
import com.esprit.jobhunter.Main2Activity;
import com.esprit.jobhunter.R;

import java.util.ArrayList;
import java.util.List;

public class ListoffersAdapter extends RecyclerView.Adapter<ListoffersAdapter.ViewHolder> {
    private Context context;
    private List<Job> list ;
    Fragment fragment = null;
    int lastPosition=-1;

    public ListoffersAdapter(Context context, ArrayList<Job> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.offer_jobprofile_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Job job = list.get(position);
        fragment = new Listoffre_applicants();
        holder.textLabel.setText(job.getLabel());
        holder.type.setText(job.getContract_type());
        holder.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main2Activity myActivity = (Main2Activity) context;
                Bundle bundle = new Bundle();
                bundle.putSerializable("id",job.getId());
                Fragment myFragment = new Listoffre_applicants();
                myFragment.setArguments(bundle);
                myActivity.getSupportFragmentManager().beginTransaction().replace(R.id.flContent, myFragment).addToBackStack(null).commit();
            }
        });
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main2Activity myActivity = (Main2Activity) context;
                Bundle bundle = new Bundle();
                bundle.putSerializable("job", job);
                Fragment myFragment = new UpdateactionFragment();
                myFragment.setArguments(bundle);
                myActivity.getSupportFragmentManager().beginTransaction().replace(R.id.flContent,myFragment).addToBackStack(null).commit();
            }
        });

        setAnimation(holder.itemView, position);
        //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        //holder.textCompanyName.setText(String.valueOf(job.getCompany().getName()));
        //holder.imgCompany.setImageResource(String.valueOf(job.getYear()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textLabel, textCompanyName,type;
        public ImageView imgCompany,update;
        public Button show;
        public ViewHolder(View itemView) {
            super(itemView);
            show = itemView.findViewById(R.id.show);
            textLabel = itemView.findViewById(R.id.job_label);
            type=itemView.findViewById(R.id.type);
            textCompanyName = itemView.findViewById(R.id.company_name);
            update = itemView.findViewById(R.id.update);

            //imgCompany = itemView.findViewById(R.id.job_image);
        }
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}