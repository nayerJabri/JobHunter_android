package com.esprit.jobhunter.RecyclerViewsAdapters.ProfileAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.esprit.jobhunter.Entity.Volunteer;
import com.esprit.jobhunter.R;

import java.util.ArrayList;
import java.util.List;

public class VolunteerAdapter extends RecyclerView.Adapter<VolunteerAdapter.ViewHolder> {

    private Context context;
    private List<Volunteer> list ;

    public VolunteerAdapter(Context context, ArrayList<Volunteer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.volunteer_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Volunteer vol = list.get(position);

        holder.textLabel.setText(vol.getOrganisation());
        holder.textRole.setText(vol.getRole());
        holder.textStartDate.setText(vol.getStart_date());
        if(vol.getStill_going() == 0)
            holder.textEndDate.setText(vol.getEnd_date());
        else
            holder.textEndDate.setText("Today");
        //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        //holder.textCompanyName.setText(String.valueOf(job.getCompany().getName()));
        //holder.imgCompany.setImageResource(String.valueOf(job.getYear()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textLabel, textRole, textStartDate, textEndDate;

        public ViewHolder(View itemView) {
            super(itemView);

            textLabel = itemView.findViewById(R.id.vol_org_name);
            textRole = itemView.findViewById(R.id.vol_role);
            textStartDate = itemView.findViewById(R.id.start_date);
            textEndDate = itemView.findViewById(R.id.end_date);
        }
    }
}
