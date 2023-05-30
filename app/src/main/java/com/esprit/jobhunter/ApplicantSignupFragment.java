package com.esprit.jobhunter;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esprit.jobhunter.MyUtils.GlobalParams;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class ApplicantSignupFragment extends Fragment {

    EditText name, last_name, email, password;
    Button register;
    String url;
    RequestQueue queue;

    public ApplicantSignupFragment() {
        // Required empty public constructor
    }

    public static ApplicantSignupFragment newInstance() {
        return new ApplicantSignupFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(com.esprit.jobhunter.R.layout.fragment_applicant_signup, container, false);
        name = v.findViewById(com.esprit.jobhunter.R.id.nameEditText);
        last_name = v.findViewById(com.esprit.jobhunter.R.id.lastNameEditText);
        email = v.findViewById(com.esprit.jobhunter.R.id.emailEditText);
        password = v.findViewById(com.esprit.jobhunter.R.id.passwordEditText);

        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        register = v.findViewById(com.esprit.jobhunter.R.id.applicationSigninButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equalsIgnoreCase("") || last_name.getText().toString().equalsIgnoreCase("")
                        || email.getText().toString().equalsIgnoreCase("") || password.getText().toString().equalsIgnoreCase("")) {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    url = GlobalParams.url + "/signupapplicant/";
                    url += email.getText() + "/" + password.getText() +"/"+ name.getText()+"/"+last_name.getText() ;
                    StringRequest getData = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //url += email.getText() + "/" + password.getText() +"/"+ name.getText()+"/"+last_name.getText() ;
                                JSONObject obj = new JSONObject(response);
                                if (obj.getInt("success") == 1) {
                                    Intent login = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                                    startActivity(login);
                                    getActivity().finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                //Log.e("JSONExeption", e.getMessage());
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            //Log.e("ErrorResponse", error.getMessage());
                        }
                    });

                    /*getData.setRetryPolicy(new DefaultRetryPolicy(
                            5000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/

                    queue.add(getData);
                }
            }
        });

        // Inflate the layout for this fragment
        return v;
        //return inflater.inflate(R.layout.fragment_applicant_signup, container, false);
    }

}
