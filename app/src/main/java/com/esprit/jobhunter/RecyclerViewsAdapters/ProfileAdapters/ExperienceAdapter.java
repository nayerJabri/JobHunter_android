package com.esprit.jobhunter.RecyclerViewsAdapters.ProfileAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.esprit.jobhunter.Entity.Experience;
import com.esprit.jobhunter.R;

import java.util.ArrayList;
import java.util.List;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ViewHolder> {

    private Context context;
    private List<Experience> list ;

    public ExperienceAdapter(Context context, ArrayList<Experience> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.experience_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Experience experience = list.get(position);

        holder.textLabel.setText(experience.getLabel());
        holder.textEstab.setText(experience.getEstablishmentName());
        holder.textStartDate.setText(experience.getStart_date());
        holder.textDesc.setText(experience.getDescription());
        if(experience.getStill_going() == 0)
            holder.textEndDate.setText(experience.getEnd_date());
        else
            holder.textDesc.setText("Today");

        //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        //holder.textCompanyName.setText(String.valueOf(job.getCompany().getName()));
        //holder.imgCompany.setImageResource(String.valueOf(job.getYear()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textLabel, textEstab, textStartDate, textEndDate, textDesc;

        public ViewHolder(View itemView) {
            super(itemView);

            textLabel = itemView.findViewById(R.id.ex_label);
            textEstab = itemView.findViewById(R.id.ex_company);
            textStartDate = itemView.findViewById(R.id.ex_start_date);
            textEndDate = itemView.findViewById(R.id.ex_end_date);
            textDesc = itemView.findViewById(R.id.ex_desc);
        }
    }
}
