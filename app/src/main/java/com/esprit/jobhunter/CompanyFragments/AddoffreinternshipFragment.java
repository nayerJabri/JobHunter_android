package com.esprit.jobhunter.CompanyFragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
public class AddoffreinternshipFragment extends Fragment {

    Button viewmore2,viewmore;
    RequestQueue queue;
    Button submit,add;
    String ur;
    String url;
    TagContainerLayout mTagContainerLayout;
    EditText Title,Description,date_begin,educ_req,duration,skills;
    public User user;
    private Calendar calendar;
    private DatePicker datePicker;
    private int year, month, day;
    int i=1;
    List<String> list = new ArrayList<>();
    public AddoffreinternshipFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Main2Activity myActivity = (Main2Activity) getActivity();
        View view = inflater.inflate(R.layout.fragment_addinternshipoffre, container, false);
        Intent fromLogin = getActivity().getIntent();
        user = (User) fromLogin.getSerializableExtra("connectedUser");
        System.out.println("user"+user.getId());
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        url = GlobalParams.url+ "/addinternship/";
        Title = (EditText) view.findViewById(R.id.Title);
        mTagContainerLayout = (TagContainerLayout) view.findViewById(R.id.tagcontainerLayout);
        Description = (EditText) view.findViewById(R.id.Description);
        date_begin = (EditText) view.findViewById(R.id.date_begin);
        educ_req = (EditText) view.findViewById(R.id.educ_req);
        submit = (Button) view.findViewById(R.id.submit);
        duration = (EditText) view.findViewById(R.id.duration);
        skills = (EditText) view.findViewById(R.id.skills);
        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+2, day);
        Title.setHint("Title of the offre");
        Title.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                skills.setHint("");
                return false;
            }
        });

        Title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Title.setHint("Title of the offre");
                }
            }
        });
        Description.setHint("Description of the offre");
        Description.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                skills.setHint("");
                return false;
            }
        });

        Description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Description.setHint("Description of the offre");
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
        duration.setHint("Duration of the projet");
        duration.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                duration.setHint("");
                return false;
            }
        });

        duration.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    duration.setHint("Duration of the offre");
                }
            }
        });
        date_begin.setHint("Date begin of the offre");
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
                    date_begin.setHint("Date begin of the offre");
                }
            }
        });
        educ_req.setHint("Education required for the offre");
        educ_req.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                educ_req.setHint("");
                return false;
            }
        });

        educ_req.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    educ_req.setHint("Education required for the projet");
                }
            }
        });
        add = (Button) view.findViewById(R.id.add);
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
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ur = date_begin.getText().toString();
                ur = ur.replace("/", "%2F");

                if (Title.getText().toString().isEmpty() || Description.getText().toString().isEmpty() || ur.isEmpty() || educ_req.getText().toString().isEmpty() || duration.getText().toString().isEmpty() || mTagContainerLayout.getTags().size() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "You must add all!", Toast.LENGTH_LONG).show();

                } else {
                    url += Title.getText() + "/" + Description.getText() + "/" + ur + "/" + educ_req.getText() + "/" + mTagContainerLayout.getTags() + "/" + duration.getText() + "/" + user.getId();
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
                }
            }
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
