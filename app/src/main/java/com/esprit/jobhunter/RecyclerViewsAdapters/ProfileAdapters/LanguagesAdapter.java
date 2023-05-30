package com.esprit.jobhunter.RecyclerViewsAdapters.ProfileAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.esprit.jobhunter.Entity.Language;
import com.esprit.jobhunter.R;

import java.util.ArrayList;
import java.util.List;

public class LanguagesAdapter extends RecyclerView.Adapter<LanguagesAdapter.ViewHolder> {

    private Context context;
    private List<Language> list ;

    public LanguagesAdapter(Context context, ArrayList<Language> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.languages_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Language lan = list.get(position);

        holder.textLabel.setText(lan.getLabel());
        holder.textLevel.setText(lan.getLevel());
        //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        //holder.textCompanyName.setText(String.valueOf(job.getCompany().getName()));
        //holder.imgCompany.setImageResource(String.valueOf(job.getYear()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textLabel, textLevel;

        public ViewHolder(View itemView) {
            super(itemView);

            textLabel = itemView.findViewById(R.id.lang_label);
            textLevel = itemView.findViewById(R.id.lang_level);
        }
    }
}
