package com.esprit.jobhunter.CompanyFragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.jobhunter.Entity.User;
import com.esprit.jobhunter.Main2Activity;
import com.esprit.jobhunter.MyUtils.GlobalParams;
import com.esprit.jobhunter.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddoffreFragment extends DialogFragment{

    Button viewmore2,viewmore;
    RequestQueue queue;
    Button submit,add;
    String url;
    String ur;
    private Calendar calendar;
    private DatePicker datePicker;
    private int year, month, day;
    List<String> list = new ArrayList<>();
    EditText Title,Description,date_begin,experience,skills;
    Spinner content_type;
    Button button1;
    TextView sp;
    TagContainerLayout mTagContainerLayout;
    public User user;
    public AddoffreFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Main2Activity myActivity = (Main2Activity) getActivity();
        View view = inflater.inflate(R.layout.fragment_addoffre, container, false);
        Intent fromLogin = getActivity().getIntent();
        user = (User) fromLogin.getSerializableExtra("connectedUser");
        System.out.println("user"+user.getId());
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        url = GlobalParams.url + "/addjob/";
        Title = (EditText) view.findViewById(R.id.Title);


        Description = (EditText) view.findViewById(R.id.Description);
        date_begin = (EditText) view.findViewById(R.id.date_begin);
        add = (Button) view.findViewById(R.id.add);
        mTagContainerLayout = (TagContainerLayout) view.findViewById(R.id.tagcontainerLayout);
        experience = (EditText) view.findViewById(R.id.experience);
        skills = (EditText) view.findViewById(R.id.skills);

        Title.setHint("Title of offre");
        Title.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Title.setHint("");
                return false;
            }
        });

        Title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Title.setHint("Title of offre");
                }
            }
        });
        Description.setHint("Description of offre");
        Description.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Description.setHint("");
                return false;
            }
        });

        Description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Description.setHint("Description of offre");
                }
            }
        });
        date_begin.setHint("Date begin of the offer");
        date_begin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                date_begin.setHint("");
                return false;
            }
        });

        date_begin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    date_begin.setHint("Date begin of the offer");
                }
            }
        });
        experience.setHint("Experience for the offre");
        experience.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                experience.setHint("");
                return false;
            }
        });

        experience.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    experience.setHint("Experience for the offre");
                }
            }
        });
        skills.setHint("Skills required");
        skills.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                skills.setHint("");
                return false;
            }
        });

        skills.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    skills.setHint("Skills required");
                }
            }
        });
        add.setOnClickListener(v->{
            System.out.println(skills.getText());
            list.add((skills.getText()).toString());
            mTagContainerLayout.setTags(list);
            skills.setText(null);
        });
        mTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {

            @Override
            public void onTagClick(int position, String text) {
                AlertDialog.Builder a_builder =  new AlertDialog.Builder(getActivity(),R.style.MyDialogTheme);
                a_builder.setMessage("Are you want delete this?");
                a_builder.setCancelable(false);
                a_builder.setPositiveButton("Yes", ((DialogInterface dialog, int which) -> {
                    mTagContainerLayout.removeTag(position);
                    list.remove(position);
                    dialog.cancel();
                }));
                a_builder.setNegativeButton("cancel", (DialogInterface dialog, int which) -> {
                    dialog.cancel();
                });
                AlertDialog alert = a_builder.create();
                alert.setTitle("Alert!");
                alert.show();

                System.out.println("zzz");
            }

            @Override
            public void onTagLongClick(final int position, String text) {
                // ...
            }

            public void onSelectedTagDrag(int position, String text){
                // ...
            }

            @Override
            public void onTagCrossClick(int position) {
                // ...
            }
        });
/***********************************************/
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+2, day);



        /****************************************/
        submit = (Button) view.findViewById(R.id.submit);
        content_type = (Spinner) view.findViewById(R.id.content_type);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.options_arrays, R.layout.spinner_item);
        content_type.setAdapter(adapter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ur = date_begin.getText().toString();
                ur = ur.replace("/", "%2F");
                if(Title.getText().toString().isEmpty() || Description.getText().toString().isEmpty() || ur.isEmpty() || experience.getText().toString().isEmpty() || mTagContainerLayout.getTags().size()==0 )
                {
                    Toast.makeText(getActivity().getApplicationContext(), "You must add all!",Toast.LENGTH_LONG).show();
                }

                else {

                    System.out.println(mTagContainerLayout.getTags());

                    url += Title.getText() + "/" + Description.getText() + "/" + ur + "/" + content_type.getSelectedItem() + "/" + experience.getText() + "/" + mTagContainerLayout.getTags() + "/" + user.getId();
                    System.out.println(url);
                    StringRequest getData = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (obj.getInt("success") == 1) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Ligne inserré !", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                Log.e("JSONExeption", e.getMessage());
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("ErrorResponse", error.getMessage());
                        }
                    });
                    queue.add(getData);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", user);
                    Fragment myFragment = new MyOffersFragment();
                    myFragment.setArguments(bundle);
                    myActivity.getSupportFragmentManager().beginTransaction().replace(R.id.flContent, myFragment).addToBackStack(null).commit();
                }}
        });
        return view;
    }





    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(getActivity(),
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        date_begin.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }


}
