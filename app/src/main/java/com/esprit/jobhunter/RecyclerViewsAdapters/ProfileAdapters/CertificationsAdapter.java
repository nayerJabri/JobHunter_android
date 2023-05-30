package com.esprit.jobhunter.RecyclerViewsAdapters.ProfileAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.esprit.jobhunter.Entity.Certification;
import com.esprit.jobhunter.R;

import java.util.ArrayList;
import java.util.List;

public class CertificationsAdapter extends RecyclerView.Adapter<CertificationsAdapter.ViewHolder> {

    private Context context;
    private List<Certification> list ;

    public CertificationsAdapter(Context context, ArrayList<Certification> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.certifications_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Certification cert = list.get(position);

        holder.textLabel.setText(cert.getLabel());
        holder.textAuth.setText(cert.getCert_authority());
        holder.textStartDate.setText(cert.getCert_date());
        if(cert.getIf_expire()==0){
            holder.textEndDate.setText(cert.getExpire_date());
        }else{
            holder.textEndDate.setText("-");
        }
        holder.textLicence.setText(cert.getLicence_num());
        //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        //holder.textCompanyName.setText(String.valueOf(job.getCompany().getName()));
        //holder.imgCompany.setImageResource(String.valueOf(job.getYear()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textLabel, textAuth, textStartDate, textEndDate, textLicence;

        public ViewHolder(View itemView) {
            super(itemView);

            textLabel = itemView.findViewById(R.id.cert_label);
            textAuth = itemView.findViewById(R.id.cert_auth);
            textStartDate = itemView.findViewById(R.id.cert_obtained_date);
            textEndDate = itemView.findViewById(R.id.cert_expire_date);
            textLicence = itemView.findViewById(R.id.cert_licence);
        }
    }
}
