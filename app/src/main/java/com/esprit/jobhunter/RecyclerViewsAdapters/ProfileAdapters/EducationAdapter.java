package com.esprit.jobhunter.RecyclerViewsAdapters.ProfileAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.esprit.jobhunter.Entity.Education;
import com.esprit.jobhunter.R;

import java.util.ArrayList;
import java.util.List;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.ViewHolder> {

    private Context context;
    private List<Education> list ;

    public EducationAdapter(Context context, ArrayList<Education> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.education_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Education education = list.get(position);

        holder.textInstitution.setText(education.getInst_name());
        holder.textDegree.setText(education.getDegree());
        holder.textDomain.setText(education.getDomain());
        holder.textStartDate.setText(education.getStart_date());
        holder.textEndDate.setText(education.getEnd_date());
        holder.textResult.setText(education.getResult());
        //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        //holder.textCompanyName.setText(String.valueOf(job.getCompany().getName()));
        //holder.imgCompany.setImageResource(String.valueOf(job.getYear()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textInstitution, textDegree, textDomain, textStartDate, textEndDate, textResult;

        public ViewHolder(View itemView) {
            super(itemView);

            textInstitution = itemView.findViewById(R.id.inst_name);
            textDegree = itemView.findViewById(R.id.degree);
            textDomain = itemView.findViewById(R.id.domain);
            textStartDate = itemView.findViewById(R.id.start_date);
            textEndDate = itemView.findViewById(R.id.end_date);
            textResult = itemView.findViewById(R.id.result);
        }
    }
}

