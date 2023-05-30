package com.esprit.jobhunter.MessengerFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.esprit.jobhunter.Entity.Message;
import com.esprit.jobhunter.Entity.User;
import com.esprit.jobhunter.MyUtils.GlobalParams;
import com.esprit.jobhunter.RecyclerViewsAdapters.ChatBoxAdapter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.jobhunter.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatBoxFragment extends Fragment {

    String nickname;
    User connectedUser;
    User receiverUser;

    private Socket socket;
    private String Nickname = "" ;

    public RecyclerView myRecylerView ;
    public List<Message> MessageList ;
    public List<Message> MessageList2 ;
    public ChatBoxAdapter chatBoxAdapter;
    public EditText messagetxt ;
    public Button send ;

    Message messageVar;
    Message messageVar2;

    private String url = GlobalParams.url+"/getmessages/";


    public ChatBoxFragment() {
        // Required empty public constructor
    }

    public void setReceiverUser(User receiverUser){
        this.receiverUser = receiverUser;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_box, container, false);

        messagetxt = (EditText) view.findViewById(R.id.message) ;
        send = (Button)view.findViewById(R.id.send);

        Intent fromLogin = getActivity().getIntent();
        connectedUser = (User) fromLogin.getSerializableExtra("connectedUser");

        //------------------
        url+=connectedUser.getId()+"/"+receiverUser.getId();

        getMessages();
        //------------------

        /*if(((MainActivity)getActivity()).getConnectedUser() != null) {
            connectedUser = ((MainActivity) getActivity()).getConnectedUser();
        } else if (((Main2Activity)getActivity()).getConnectedUser() != null){
            connectedUser = ((Main2Activity)getActivity()).getConnectedUser();
        }*/

        //connect you socket client to the server
        try {
            //if you are using a phone device you should connect to same local network as your laptop and disable your pubic firewall as well
            socket = IO.socket(GlobalParams.url);
            //create connection
            socket.connect();
            // emit the event join along side with the nickname
            socket.emit("join",connectedUser.getId());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        MessageList = new ArrayList<>();
        MessageList2 = new ArrayList<>();
        myRecylerView = (RecyclerView) view.findViewById(R.id.messagelist);
        myRecylerView.setAdapter(chatBoxAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        myRecylerView.setLayoutManager(mLayoutManager);
        myRecylerView.setItemAnimator(new DefaultItemAnimator());

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //retrieve the nickname and the message content and fire the event //messagedetection
                if(!messagetxt.getText().toString().isEmpty()){
                    socket.emit("messagedetection", connectedUser.getId(), receiverUser.getId(), messagetxt.getText().toString(), socket.id());
                    messagetxt.setText("");
                }
                getMessages();
            }
        });

        socket.on("message", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                if(isAdded()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject data = (JSONObject) args[0];
                            try {
                                messageVar = new Message();
                                messageVar2 = new Message();
                                //extract data from fired event

                                messageVar.setContent(data.getString("message"));
                                messageVar.setReceiver(data.getInt("id_receiver"));
                                messageVar.setSender(data.getInt("id_sender"));
                                messageVar.setSender_name(connectedUser.getName() + ": ");
                                messageVar.setReceiver_name(receiverUser.getName() + ": ");
                                messageVar.setTest(0);


                                    System.out.println("My socket id: "+socket.id());
                                    System.out.println("Other socket id: "+data.getString("socket_id"));
                                    //add the message to the messageList
                                    if ((messageVar.getSender() == connectedUser.getId() && messageVar.getReceiver() == receiverUser.getId()) || (messageVar.getSender() == receiverUser.getId() && messageVar.getReceiver() == connectedUser.getId())) {
                                        if (messageVar.getSender() == connectedUser.getId() && messageVar.getReceiver() == receiverUser.getId()) {
                                            messageVar.setTest(0);
                                            MessageList.add(messageVar);
                                        } else if (messageVar.getSender() == receiverUser.getId() && messageVar.getReceiver() == connectedUser.getId()) {
                                            messageVar.setTest(1);
                                            MessageList.add(messageVar);
                                        }
                                    }


                                System.out.println("MESSAGE :: " + messageVar);
                                System.out.println("MMMOOOOOPPPPP :: " + MessageList);
                                // add the new updated list to the adapter
                                chatBoxAdapter = new ChatBoxAdapter(MessageList, MessageList2);
                                //set the adapter for the recycler view
                                myRecylerView.setAdapter(chatBoxAdapter);
                                // notify the adapter to update the recycler view
                                chatBoxAdapter.notifyDataSetChanged();
                                myRecylerView.smoothScrollToPosition(MessageList.size() - 1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }


    private void getMessages() {
        //progressBar1.setVisibility(View.VISIBLE);

        MessageList = new ArrayList<>();
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = response.getJSONArray("result");

                            //  System.out.println("RESPONSE: " + response.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    //System.out.println("JSONOBJECT " + jsonObject);
                                    Message msg = new Message();


                                    msg.setId(jsonObject.getInt("id"));
                                    msg.setSender(jsonObject.getInt("sender"));
                                    msg.setReceiver(jsonObject.getInt("receiver"));
                                    msg.setContent(jsonObject.getString("content"));
                                    msg.setSent_at(jsonObject.getString("sent_at"));
                                    msg.setSender_name(jsonObject.getString("sender_name"));
                                    msg.setReceiver_name(jsonObject.getString("receiver_name"));
                                    //-----------------------------------------
                                    MessageList.add(msg);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            // add the new updated list to the adapter
                            chatBoxAdapter = new ChatBoxAdapter(MessageList, MessageList2);
                            //set the adapter for the recycler view
                            myRecylerView.setAdapter(chatBoxAdapter);
                            // notify the adapter to update the recycler view
                            chatBoxAdapter.notifyDataSetChanged();
                            //progressBar1.setVisibility(View.GONE);
                            myRecylerView.smoothScrollToPosition(MessageList.size() - 1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //System.out.println("MYYYLIST"+jobList);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", error.getMessage());
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(jsonArrayRequest);
    }

}
