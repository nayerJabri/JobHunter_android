package com.esprit.jobhunter.RecyclerViewsAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.esprit.jobhunter.Entity.Message;
import com.esprit.jobhunter.R;

import java.util.List;

public class ChatBoxAdapter extends RecyclerView.Adapter<ChatBoxAdapter.MyViewHolder> {

    private List<Message> MessageList;
    private List<Message> MessageList2;


    public ChatBoxAdapter(List<Message> MessagesList, List<Message> MessagesList2) {
        this.MessageList = MessagesList;
        this.MessageList2 = MessagesList2;
    }

    @Override
    public int getItemCount() {
        return MessageList.size();
    }

    @Override
    public ChatBoxAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new ChatBoxAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ChatBoxAdapter.MyViewHolder holder, final int position) {
        //binding the data from our ArrayList of object to the item.xml using the viewholder

        Message m = MessageList.get(position);

        if(m.getTest()==0) {
            holder.nickname.setText(m.getSender_name());
            holder.message.setText(m.getContent());
        } else if (m.getTest()==1){
            holder.nickname.setText(m.getReceiver_name());
            holder.message.setText(m.getContent());
        }

    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView nickname,nickname1;
        public TextView message,message1;


        public MyViewHolder(View view) {
            super(view);
            nickname = (TextView) view.findViewById(R.id.m_name);
            message = (TextView) view.findViewById(R.id.message);
            /*nickname1 = (TextView) view.findViewById(R.id.m_name1);
            message1 = (TextView) view.findViewById(R.id.message1);*/
        }
    }

}
