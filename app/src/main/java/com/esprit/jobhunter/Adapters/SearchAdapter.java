package com.esprit.jobhunter.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.esprit.jobhunter.Entity.Offer;
import com.esprit.jobhunter.MainActivity;
import com.esprit.jobhunter.MyUtils.GlobalParams;
import com.esprit.jobhunter.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends ArrayAdapter<Offer> {

    private List<Offer> OfferList;
    private List<Offer> tempList;
    private List<Offer> suggestionList;

    public SearchAdapter(@NonNull Context context, int resource, @NonNull List<Offer> objects) {
        super(context, resource, objects);
        OfferList = objects;
        tempList = new ArrayList<>(OfferList);
        suggestionList = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_row, parent, false);

        TextView offer_label = convertView.findViewById(R.id.job_label);
        TextView cmp_name = convertView.findViewById(R.id.company_name);
        CircleImageView cmp_pic = convertView.findViewById(R.id.cmp_pic);

        Offer offer = OfferList.get(position);

        offer_label.setText(offer.getLabel());
        cmp_name.setText(offer.getCmp_name());

        //Picasso.with(convertView.getContext()).load(offer.getCmp_pic()).into(cmp_pic);
        if(offer.getCmp_pic().length()>60){
            Picasso.with(convertView.getContext()).load(offer.getCmp_pic()).into(cmp_pic);
        } else {
            Picasso.with(convertView.getContext()).load(GlobalParams.ressourceUrl+"/"+offer.getCmp_pic()).into(cmp_pic);
        }

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return offerFilter;
    }

    Filter offerFilter = new Filter() {

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Offer offer = (Offer) resultValue;
            return offer.getLabel();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                suggestionList.clear();
                constraint = constraint.toString().trim().toLowerCase();

                for (Offer offer : tempList) {

                    if (offer.getLabel().toLowerCase().contains(constraint) || offer.getCmp_name().toLowerCase().contains(constraint)) {
                        suggestionList.add(offer);
                    }
                }

                filterResults.count = suggestionList.size();
                filterResults.values = suggestionList;

            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Offer> uList = (ArrayList<Offer>) results.values;

            if (results != null && results.count > 0) {
                clear();
                for (Offer u : uList) {
                    add(u);
                    notifyDataSetChanged();
                }
            }
        }
    };




}
