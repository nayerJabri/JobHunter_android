package com.esprit.jobhunter.RecyclerViewsAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.jobhunter.Entity.User;
import com.esprit.jobhunter.Main2Activity;
import com.esprit.jobhunter.MainActivity;
import com.esprit.jobhunter.MessengerFragments.ChatBoxFragment;
import com.esprit.jobhunter.MyUtils.GlobalParams;
import com.esprit.jobhunter.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ContactsCompanyAdapter extends RecyclerView.Adapter<ContactsCompanyAdapter.ViewHolder> {

    private Context context;
    private List<User> list ;

    public ContactsCompanyAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ContactsCompanyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);
        return new ContactsCompanyAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactsCompanyAdapter.ViewHolder holder, int position) {
        User user = list.get(position);
        final int x = position;

        System.out.println("SSSSSSSSSSSSSSSSS: "+user);
        holder.name_user.setText(user.getName()+" "+user.getLast_name());
        //Picasso.with((Main2Activity)context).load(user.getPicture()).into(holder.img_user);

        /*ImageRequest imageLoad = new ImageRequest(GlobalParams.ressourceUrl+"/"+user.getPicture(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                // callback
                holder.img_user.setImageBitmap(response); }
        }, 100, 100, null, null);
        RequestQueue queueImage = Volley.newRequestQueue((Main2Activity)context);
        queueImage.add(imageLoad);*/
        if(user.getPicture().length()>60){
            Picasso.with((MainActivity)context).load(user.getPicture()).into(holder.img_user);
        } else {
            Picasso.with((MainActivity)context).load(GlobalParams.ressourceUrl+"/"+user.getPicture()).into(holder.img_user);
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ChatBoxFragment();
                ((ChatBoxFragment) fragment).setReceiverUser(list.get(x));
                //FragmentManager fragmentManager = v.getContext()
                ((Main2Activity)context).getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name_user;
        public ImageView img_user;
        public RelativeLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            name_user = itemView.findViewById(R.id.contact_name);
            img_user = itemView.findViewById(R.id.contact_pic);
            layout = itemView.findViewById(R.id.contact_item_layout);
        }
    }
}
