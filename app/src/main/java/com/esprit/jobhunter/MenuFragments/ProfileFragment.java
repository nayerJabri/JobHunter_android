package com.esprit.jobhunter.MenuFragments;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.jobhunter.Entity.Certification;
import com.esprit.jobhunter.Entity.Experience;
import com.esprit.jobhunter.Entity.Language;
import com.esprit.jobhunter.Entity.Skills;
import com.esprit.jobhunter.Entity.Volunteer;
import com.esprit.jobhunter.MainActivity;
import com.esprit.jobhunter.MyUtils.AndroidMultiPartEntity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.jobhunter.Entity.Education;
import com.esprit.jobhunter.Entity.User;
import com.esprit.jobhunter.MyUtils.GlobalParams;
import com.esprit.jobhunter.R;
import com.esprit.jobhunter.RecyclerViewsAdapters.ProfileAdapters.CertificationsAdapter;
import com.esprit.jobhunter.RecyclerViewsAdapters.ProfileAdapters.EducationAdapter;
import com.esprit.jobhunter.RecyclerViewsAdapters.ProfileAdapters.ExperienceAdapter;
import com.esprit.jobhunter.RecyclerViewsAdapters.ProfileAdapters.LanguagesAdapter;
import com.esprit.jobhunter.RecyclerViewsAdapters.ProfileAdapters.VolunteerAdapter;
import com.squareup.picasso.Picasso;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import de.hdodenhof.circleimageview.CircleImageView;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView skills_tv;
    private RecyclerView recyclerViewEduc, recyclerViewExp, recyclerViewCert, recyclerViewLang, recyclerViewVol;
    RecyclerView.LayoutManager layoutManager;
    private NestedScrollView profileNestedScrollView;
    private ProgressBar progressBarProfile;

    private ArrayList<Education> educationList;
    private ArrayList<Experience> experienceList;
    private ArrayList<Certification> certificationList;
    private ArrayList<Volunteer> volunteerList;
    private ArrayList<Language> languageList;
    private ArrayList<Skills> skillsList;

    private RecyclerView.Adapter adapterEduc, adapterExp, adapterCert, adapterLang, adapterVol;
    RequestQueue queue;

    String url = GlobalParams.url + "/education/";
    String url2 = GlobalParams.url + "/experience/";
    String url3 = GlobalParams.url + "/certification/";
    String url4 = GlobalParams.url + "/language/";
    String url5 = GlobalParams.url + "/volunteer/";
    String url6 = GlobalParams.url + "/skills/";

    User connectedUser;

    TextView textName, textDescription, textAdress, textEduc, tvTitle, tvEmail, tvTel, tvNat;
    CircleImageView profilePic;
    ImageView addVolun, addEduc, addExp, addSkill, addCert, addLang;

    String skills = "";
    String test1 = "", test2 = "";

    //-----Uploadimage vars
    //Bitmap to get image from gallery
    private Bitmap bitmap;
    //Uri to store the image uri
    private Uri filePathUri;
    public String UPLOAD_URL = GlobalParams.uploadUrl;
    long totalSize = 0;
    private Uri fileUri;

    final int CODE_GALLERY_REQUEST = 999;

    String url7 = GlobalParams.url + "/addeduc";
    String url8 = GlobalParams.url + "/addexp";
    String url9 = GlobalParams.url + "/addskill";
    String url10 = GlobalParams.url + "/addcert";
    String url11 = GlobalParams.url + "/addlang";
    String url12 = GlobalParams.url + "/addevolun";


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        View photoHeader = view.findViewById(R.id.photoHeader);
        photoHeader.setTranslationZ(6);
        photoHeader.invalidate();
        //----------------Getting connected user
        connectedUser = ((MainActivity) getActivity()).getConnectedUser();
        //----------------
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        //-----------------------------
        profileNestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedProfil);
        progressBarProfile = (ProgressBar) view.findViewById(R.id.progressBarProfil);

        textName = (TextView) view.findViewById(R.id.tvName);
        textDescription = (TextView) view.findViewById(R.id.tvSummary);
        textAdress = (TextView) view.findViewById(R.id.tvAddress);
        textEduc = (TextView) view.findViewById(R.id.tvEducation);
        profilePic = (CircleImageView) view.findViewById(R.id.civProfilePic);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        tvTel = (TextView) view.findViewById(R.id.tvTel);
        tvNat = (TextView) view.findViewById(R.id.tvNat);
        addEduc = (ImageView) view.findViewById(R.id.addEduc);
        addExp = (ImageView) view.findViewById(R.id.addExp);
        addSkill = (ImageView) view.findViewById(R.id.addSkill);
        addCert = (ImageView) view.findViewById(R.id.addCert);
        addLang = (ImageView) view.findViewById(R.id.addLang);
        addVolun = (ImageView) view.findViewById(R.id.addVolun);

        profilePic.setTranslationZ(20);
        profilePic.invalidate();


        if (connectedUser.getName() != null && !connectedUser.getName().equals("null") && connectedUser.getLast_name() != null && !connectedUser.getLast_name().equals("null")) {
            textName.setText(connectedUser.getName() + " " + connectedUser.getLast_name());
        }
        if (connectedUser.getAdress() != null && !connectedUser.getAdress().equals("null")) {
            textAdress.setText(connectedUser.getAdress());
        }
        if (connectedUser.getDescription() != null && !connectedUser.getDescription().equals("null")) {
            textDescription.setText(connectedUser.getDescription());
        }
        if (connectedUser.getTel1() != null && !connectedUser.getTel1().equals("null")) {
            test1 = "Tel1: " + connectedUser.getTel1();
        }

        String cont = "";
        if (connectedUser.getTel2() != null && !connectedUser.getTel2().equals("null")) {
            test2 = "Tel2: " + connectedUser.getTel2();
        }
        if (test1.isEmpty() && test2.isEmpty())
            cont = cont + "";
        if (!test1.isEmpty() && !test2.isEmpty())
            cont = cont + test1 + " - " + test2;
        if (test1.isEmpty() && !test2.isEmpty())
            cont = cont + test1;
        if (!test1.isEmpty() && test2.isEmpty())
            cont = cont + test2;

        tvTel.setText(cont);

        if (connectedUser.getNationality() != null && !connectedUser.getNationality().equals("null")) {
            tvNat.setText(connectedUser.getNationality());
        }

        if (connectedUser.getEmail() != null && !connectedUser.getEmail().equals("null")) {
            tvEmail.setText(connectedUser.getEmail());
        }


        if (connectedUser.getPicture() != null && !connectedUser.getPicture().equals("null")) {
            /*ImageRequest imageLoad = new ImageRequest(GlobalParams.ressourceUrl + "/" + connectedUser.getPicture(), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    // callback
                    profilePic.setImageBitmap(response);
                }
            }, 100, 100, null, null);
            RequestQueue queueImage = Volley.newRequestQueue(getActivity().getApplicationContext());
            queueImage.add(imageLoad);*/
            if(connectedUser.getPicture().length()>60){
                Picasso.with(getActivity().getApplicationContext()).load(connectedUser.getPicture()).into(profilePic);
            } else {
                Picasso.with(getActivity().getApplicationContext()).load(GlobalParams.ressourceUrl+"/"+connectedUser.getPicture()).into(profilePic);
            }
            //Picasso.with(getActivity().getApplicationContext()).load(GlobalParams.ressourceUrl+"/"+connectedUser.getPicture()).into(profilePic);
        }
        //-----------------------------Education

        url += connectedUser.getCv_id();
        getJsonArrayEducation();

        recyclerViewEduc = (RecyclerView) view.findViewById(R.id.recyclerEducation);
        recyclerViewEduc.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewEduc.setLayoutManager(layoutManager);


        //---------------------Experience
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        url2 += connectedUser.getCv_id();
        getJsonArrayExperience();

        recyclerViewExp = (RecyclerView) view.findViewById(R.id.recyclerExperience);
        recyclerViewExp.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewExp.setLayoutManager(layoutManager);

        //---------------------Skills
        skills_tv = view.findViewById(R.id.skills_tv);
        getJsonArraySkills();

        //---------------------Certifications
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        url3 += connectedUser.getCv_id();
        getJsonArrayCertification();

        recyclerViewCert = (RecyclerView) view.findViewById(R.id.recyclerCertification);
        recyclerViewCert.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewCert.setLayoutManager(layoutManager);

        //---------------------Languages
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        url4 += connectedUser.getCv_id();
        getJsonArrayLanguage();

        recyclerViewLang = (RecyclerView) view.findViewById(R.id.recyclerLanguages);
        recyclerViewLang.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewLang.setLayoutManager(layoutManager);

        //---------------------Volunteer
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        url5 += connectedUser.getCv_id();
        getJsonArrayVolunteer();

        recyclerViewVol = (RecyclerView) view.findViewById(R.id.recyclerVolunteer);
        recyclerViewVol.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewVol.setLayoutManager(layoutManager);


        //---------------------Image upload
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CODE_GALLERY_REQUEST);
            }
        });

        //---------------------
        addEduc.setOnClickListener(addEducOnClickListener);
        addExp.setOnClickListener(addExpOnClickListener);
        addSkill.setOnClickListener(addSkillOnClickListener);
        addCert.setOnClickListener(addCertOnClickListener);
        addLang.setOnClickListener(addLangOnClickListener);
        addVolun.setOnClickListener(addVolunOnClickListener);
        //---------------------
        return view;
    }

    //==================================
    private View.OnClickListener addEducOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addEducClicked();
        }
    };

    private void addEducClicked() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(getActivity()).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_educ_dialog, null);

        //final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);
        EditText inst_name = (EditText) dialogView.findViewById(R.id.edt_inst);
        EditText start_date = (EditText) dialogView.findViewById(R.id.edt_from);
        EditText end_date = (EditText) dialogView.findViewById(R.id.edt_to);
        EditText degree = (EditText) dialogView.findViewById(R.id.edt_degree);
        EditText domain = (EditText) dialogView.findViewById(R.id.edt_domain);
        EditText result = (EditText) dialogView.findViewById(R.id.edt_result);
        EditText description = (EditText) dialogView.findViewById(R.id.edt_desc);

        final Calendar today = Calendar.getInstance();
        /*DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                start_date.setText(sdf.format(myCalendar.getTime()));
            }
        };*/
        /*end_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(getActivity(), R.style.DatePickerDialogStyle, date2, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                return true;
            }
        });*/


        start_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //------------------
                    MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getActivity(), new MonthPickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(int selectedMonth, int selectedYear) {
                            //Toast.makeText(getActivity(), "Date set with month" + selectedMonth + " year " + selectedYear, Toast.LENGTH_SHORT).show();
                            start_date.setText("");
                            start_date.setText((selectedMonth + 1) + "/" + selectedYear);
                        }
                    }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

                    builder.setActivatedMonth(Calendar.JANUARY)
                            .setMinYear(1900)
                            .setActivatedYear(2019)
                            .setMaxYear(2050)
                            .setMinMonth(Calendar.JANUARY)
                            .setTitle("Select month")
                            .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                            .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                                @Override
                                public void onMonthChanged(int selectedMonth) {

                                }
                            })
                            .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                                @Override
                                public void onYearChanged(int selectedYear) {

                                }
                            })
                            .build()
                            .show();
                    //-------------------
                }
                return true;
            }
        });

        end_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //------------------
                    MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getActivity(), new MonthPickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(int selectedMonth, int selectedYear) {
                            //Toast.makeText(getActivity(), "Date set with month" + selectedMonth + " year " + selectedYear, Toast.LENGTH_SHORT).show();
                            end_date.setText("");
                            end_date.setText((selectedMonth + 1) + "/" + selectedYear);
                        }
                    }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

                    builder.setActivatedMonth(Calendar.JANUARY)
                            .setMinYear(1900)
                            .setActivatedYear(2019)
                            .setMaxYear(2050)
                            .setMinMonth(Calendar.JANUARY)
                            .setTitle("Select month")
                            .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                            .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                                @Override
                                public void onMonthChanged(int selectedMonth) {

                                }
                            })
                            .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                                @Override
                                public void onYearChanged(int selectedYear) {

                                }
                            })
                            .build()
                            .show();
                    //-------------------
                }
                return true;
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                if (inst_name.getText().toString().equals("") || start_date.getText().toString().equals("") || end_date.getText().toString().equals("") || degree.getText().toString().equals("") || domain.getText().toString().equals("") || result.getText().toString().equals("") || description.getText().toString().equals("")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme).create();
                    alertDialog.setTitle("Alert!");
                    alertDialog.setMessage("Please fill all the fields");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url7,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    getJsonArrayEducation();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    error.printStackTrace();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("inst_name", inst_name.getText().toString());
                            params.put("start_date", start_date.getText().toString());
                            params.put("end_planned_date", end_date.getText().toString());
                            params.put("degree", degree.getText().toString());
                            params.put("domain", domain.getText().toString());
                            params.put("result", result.getText().toString());
                            params.put("description", description.getText().toString());
                            params.put("cv_id", connectedUser.getCv_id());

                            return params;
                        }
                    };
                    RequestQueue queuex = Volley.newRequestQueue(getActivity().getApplicationContext());
                    queuex.add(postRequest);
                }
                //------------------
                dialogBuilder.dismiss();
                getJsonArrayEducation();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    private View.OnClickListener addExpOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addExpClicked();
        }
    };


    private void addExpClicked() {
        final Calendar today = Calendar.getInstance();
        final AlertDialog dialogBuilder = new AlertDialog.Builder(getActivity()).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_exp_dialog, null);

        //final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);

        EditText edt_label = (EditText) dialogView.findViewById(R.id.edt_label);
        EditText edt_establishment = (EditText) dialogView.findViewById(R.id.edt_establishment);
        EditText edt_type = (EditText) dialogView.findViewById(R.id.edt_type);
        EditText edt_place = (EditText) dialogView.findViewById(R.id.edt_place);
        EditText edt_desc = (EditText) dialogView.findViewById(R.id.edt_desc);
        EditText edt_to = (EditText) dialogView.findViewById(R.id.edt_to);
        EditText edt_from = (EditText) dialogView.findViewById(R.id.edt_from);
        CheckBox still_going = (CheckBox) dialogView.findViewById(R.id.still_going);


        //edt_from.setInputType(InputType.TYPE_NULL);
        edt_from.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //------------------
                    MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getActivity(), new MonthPickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(int selectedMonth, int selectedYear) {
                            //Toast.makeText(getActivity(), "Date set with month" + selectedMonth + " year " + selectedYear, Toast.LENGTH_SHORT).show();
                            edt_from.setText("");
                            edt_from.setText((selectedMonth + 1) + "/" + selectedYear);
                        }
                    }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

                    builder.setActivatedMonth(Calendar.JANUARY)
                            .setMinYear(1900)
                            .setActivatedYear(2019)
                            .setMaxYear(2050)
                            .setMinMonth(Calendar.JANUARY)
                            .setTitle("Select month")
                            .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                            .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                                @Override
                                public void onMonthChanged(int selectedMonth) {

                                }
                            })
                            .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                                @Override
                                public void onYearChanged(int selectedYear) {

                                }
                            })
                            .build()
                            .show();
                    //-------------------
                }
                return true;
            }
        });

        edt_to.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //------------------
                    MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getActivity(), new MonthPickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(int selectedMonth, int selectedYear) {
                            //Toast.makeText(getActivity(), "Date set with month" + selectedMonth + " year " + selectedYear, Toast.LENGTH_SHORT).show();
                            edt_to.setText("");
                            edt_to.setText((selectedMonth + 1) + "/" + selectedYear);
                        }
                    }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

                    builder.setActivatedMonth(Calendar.JANUARY)
                            .setMinYear(1900)
                            .setActivatedYear(2019)
                            .setMaxYear(2050)
                            .setMinMonth(Calendar.JANUARY)
                            .setTitle("Select month")
                            .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                            .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                                @Override
                                public void onMonthChanged(int selectedMonth) {

                                }
                            })
                            .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                                @Override
                                public void onYearChanged(int selectedYear) {

                                }
                            })
                            .build()
                            .show();
                    //-------------------
                }
                return true;
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                if (edt_label.getText().toString().equals("") || edt_establishment.getText().toString().equals("") || edt_type.getText().toString().equals("") || edt_place.getText().toString().equals("") || edt_desc.getText().toString().equals("") || edt_from.getText().toString().equals("")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme).create();
                    alertDialog.setTitle("Alert!");
                    alertDialog.setMessage("Please fill all the fields");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url8,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    getJsonArrayExperience();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    error.printStackTrace();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("label", edt_label.getText().toString());
                            params.put("establishment_name", edt_establishment.getText().toString());
                            params.put("type", edt_type.getText().toString());
                            params.put("place", edt_place.getText().toString());
                            params.put("description", edt_desc.getText().toString());
                            params.put("start_date", edt_from.getText().toString());
                            params.put("end_date", edt_to.getText().toString());
                            if (still_going.isChecked()) {
                                params.put("still_going", "1");
                            } else {
                                params.put("still_going", "0");
                            }
                            params.put("cv_id", connectedUser.getCv_id());

                            return params;
                        }
                    };
                    RequestQueue queuex = Volley.newRequestQueue(getActivity().getApplicationContext());
                    queuex.add(postRequest);
                }
                //------------------
                dialogBuilder.dismiss();
                getJsonArrayExperience();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    private View.OnClickListener addSkillOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addSkillClicked();
        }
    };

    private void addSkillClicked() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(getActivity()).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_skill_dialog, null);

        //final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);
        EditText edt_label = (EditText) dialogView.findViewById(R.id.edt_label);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                if (edt_label.getText().toString().equals("")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme).create();
                    alertDialog.setTitle("Alert!");
                    alertDialog.setMessage("Please fill all the fields");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url9,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    getJsonArraySkills();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    error.printStackTrace();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("label", edt_label.getText().toString());
                            params.put("cv_id", connectedUser.getCv_id());

                            return params;
                        }
                    };
                    RequestQueue queuex = Volley.newRequestQueue(getActivity().getApplicationContext());
                    queuex.add(postRequest);
                }
                //------------------
                dialogBuilder.dismiss();
                getJsonArraySkills();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    private View.OnClickListener addCertOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addCertClicked();
        }
    };

    private void addCertClicked() {
        final Calendar today = Calendar.getInstance();
        final AlertDialog dialogBuilder = new AlertDialog.Builder(getActivity()).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_certif_dialog, null);

        //final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);

        EditText edt_label = (EditText) dialogView.findViewById(R.id.edt_label);
        EditText edt_authority = (EditText) dialogView.findViewById(R.id.edt_authority);
        EditText edt_licence = (EditText) dialogView.findViewById(R.id.edt_licence);
        EditText edt_to = (EditText) dialogView.findViewById(R.id.edt_to);
        EditText edt_from = (EditText) dialogView.findViewById(R.id.edt_from);
        CheckBox still_going = (CheckBox) dialogView.findViewById(R.id.still_going);


        //edt_from.setInputType(InputType.TYPE_NULL);
        edt_from.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //------------------
                    MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getActivity(), new MonthPickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(int selectedMonth, int selectedYear) {
                            //Toast.makeText(getActivity(), "Date set with month" + selectedMonth + " year " + selectedYear, Toast.LENGTH_SHORT).show();
                            edt_from.setText("");
                            edt_from.setText((selectedMonth + 1) + "/" + selectedYear);
                        }
                    }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

                    builder.setActivatedMonth(Calendar.JANUARY)
                            .setMinYear(1900)
                            .setActivatedYear(2019)
                            .setMaxYear(2050)
                            .setMinMonth(Calendar.JANUARY)
                            .setTitle("Select month")
                            .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                            .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                                @Override
                                public void onMonthChanged(int selectedMonth) {

                                }
                            })
                            .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                                @Override
                                public void onYearChanged(int selectedYear) {

                                }
                            })
                            .build()
                            .show();
                    //-------------------
                }
                return true;
            }
        });

        edt_to.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //------------------
                    MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getActivity(), new MonthPickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(int selectedMonth, int selectedYear) {
                            //Toast.makeText(getActivity(), "Date set with month" + selectedMonth + " year " + selectedYear, Toast.LENGTH_SHORT).show();
                            edt_to.setText("");
                            edt_to.setText((selectedMonth + 1) + "/" + selectedYear);
                        }
                    }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

                    builder.setActivatedMonth(Calendar.JANUARY)
                            .setMinYear(1900)
                            .setActivatedYear(2019)
                            .setMaxYear(2050)
                            .setMinMonth(Calendar.JANUARY)
                            .setTitle("Select month")
                            .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                            .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                                @Override
                                public void onMonthChanged(int selectedMonth) {

                                }
                            })
                            .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                                @Override
                                public void onYearChanged(int selectedYear) {

                                }
                            })
                            .build()
                            .show();
                    //-------------------
                }
                return true;
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                if (edt_label.getText().toString().equals("") || edt_authority.getText().toString().equals("") || edt_licence.getText().toString().equals("") || edt_from.getText().toString().equals("")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme).create();
                    alertDialog.setTitle("Alert!");
                    alertDialog.setMessage("Please fill all the fields");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url10,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    getJsonArrayCertification();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    error.printStackTrace();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("label", edt_label.getText().toString());
                            params.put("cert_authority", edt_authority.getText().toString());
                            params.put("licence_num", edt_licence.getText().toString());
                            params.put("cert_date", edt_from.getText().toString());
                            params.put("expire_date", edt_to.getText().toString());
                            if (still_going.isChecked()) {
                                params.put("if_expire", "1");
                            } else {
                                params.put("if_expire", "0");
                            }
                            params.put("cv_id", connectedUser.getCv_id());

                            return params;
                        }
                    };
                    RequestQueue queuex = Volley.newRequestQueue(getActivity().getApplicationContext());
                    queuex.add(postRequest);
                }
                //------------------
                dialogBuilder.dismiss();
                getJsonArrayCertification();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    private View.OnClickListener addLangOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addLangClicked();
        }
    };

    private void addLangClicked() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(getActivity()).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_lang_dialog, null);

        //final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);
        EditText edt_label = (EditText) dialogView.findViewById(R.id.edt_label);
        Spinner edt_level = (Spinner) dialogView.findViewById(R.id.edt_level);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                if (edt_label.getText().toString().equals("") || edt_level.getSelectedItem().toString().equals("")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme).create();
                    alertDialog.setTitle("Alert!");
                    alertDialog.setMessage("Please fill all the fields");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url11,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    getJsonArrayLanguage();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    error.printStackTrace();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("label", edt_label.getText().toString());
                            params.put("level", edt_level.getSelectedItem().toString());
                            params.put("cv_id", connectedUser.getCv_id());

                            return params;
                        }
                    };
                    RequestQueue queuex = Volley.newRequestQueue(getActivity().getApplicationContext());
                    queuex.add(postRequest);
                }
                //------------------
                dialogBuilder.dismiss();
                getJsonArrayLanguage();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    private View.OnClickListener addVolunOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addVolunClicked();
        }
    };

    private void addVolunClicked() {
        final Calendar today = Calendar.getInstance();
        final AlertDialog dialogBuilder = new AlertDialog.Builder(getActivity()).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_volunteer_dialog, null);

        //final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);

        EditText edt_organisation = (EditText) dialogView.findViewById(R.id.edt_organisation);
        EditText edt_role = (EditText) dialogView.findViewById(R.id.edt_role);
        EditText edt_domain = (EditText) dialogView.findViewById(R.id.edt_domain);
        EditText edt_desc = (EditText) dialogView.findViewById(R.id.edt_desc);
        EditText edt_to = (EditText) dialogView.findViewById(R.id.edt_to);
        EditText edt_from = (EditText) dialogView.findViewById(R.id.edt_from);
        CheckBox still_going = (CheckBox) dialogView.findViewById(R.id.still_going);


        //edt_from.setInputType(InputType.TYPE_NULL);
        edt_from.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //------------------
                    MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getActivity(), new MonthPickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(int selectedMonth, int selectedYear) {
                            //Toast.makeText(getActivity(), "Date set with month" + selectedMonth + " year " + selectedYear, Toast.LENGTH_SHORT).show();
                            edt_from.setText("");
                            edt_from.setText((selectedMonth + 1) + "/" + selectedYear);
                        }
                    }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

                    builder.setActivatedMonth(Calendar.JANUARY)
                            .setMinYear(1900)
                            .setActivatedYear(2019)
                            .setMaxYear(2050)
                            .setMinMonth(Calendar.JANUARY)
                            .setTitle("Select month")
                            .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                            .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                                @Override
                                public void onMonthChanged(int selectedMonth) {

                                }
                            })
                            .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                                @Override
                                public void onYearChanged(int selectedYear) {

                                }
                            })
                            .build()
                            .show();
                    //-------------------
                }
                return true;
            }
        });

        edt_to.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //------------------
                    MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getActivity(), new MonthPickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(int selectedMonth, int selectedYear) {
                            //Toast.makeText(getActivity(), "Date set with month" + selectedMonth + " year " + selectedYear, Toast.LENGTH_SHORT).show();
                            edt_to.setText("");
                            edt_to.setText((selectedMonth + 1) + "/" + selectedYear);
                        }
                    }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

                    builder.setActivatedMonth(Calendar.JANUARY)
                            .setMinYear(1900)
                            .setActivatedYear(2019)
                            .setMaxYear(2050)
                            .setMinMonth(Calendar.JANUARY)
                            .setTitle("Select month")
                            .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                            .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                                @Override
                                public void onMonthChanged(int selectedMonth) {

                                }
                            })
                            .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                                @Override
                                public void onYearChanged(int selectedYear) {

                                }
                            })
                            .build()
                            .show();
                    //-------------------
                }
                return true;
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                if (edt_organisation.getText().toString().equals("") || edt_role.getText().toString().equals("") || edt_domain.getText().toString().equals("") || edt_desc.getText().toString().equals("") || edt_from.getText().toString().equals("")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme).create();
                    alertDialog.setTitle("Alert!");
                    alertDialog.setMessage("Please fill all the fields");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url12,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    getJsonArrayVolunteer();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    error.printStackTrace();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("organisation", edt_organisation.getText().toString());
                            params.put("role", edt_role.getText().toString());
                            params.put("domain", edt_domain.getText().toString());
                            params.put("start_date", edt_from.getText().toString());
                            params.put("end_date", edt_to.getText().toString());
                            params.put("description", edt_desc.getText().toString());
                            if (still_going.isChecked()) {
                                params.put("still_going", "1");
                            } else {
                                params.put("still_going", "0");
                            }
                            params.put("cv_id", connectedUser.getCv_id());

                            return params;
                        }
                    };
                    RequestQueue queuex = Volley.newRequestQueue(getActivity().getApplicationContext());
                    queuex.add(postRequest);
                }
                //------------------
                dialogBuilder.dismiss();
                getJsonArrayVolunteer();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }
    //==================================

    private void getJsonArraySkills() {
        skillsList = new ArrayList<>();
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url6 + connectedUser.getCv_id(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        try {

                            if (response.getInt("success") == 1) {
                                jsonArray = response.getJSONArray("result");

                                //  System.out.println("RESPONSE: " + response.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        Skills skill = new Skills();

                                        skill.setId(jsonObject.getInt("id"));
                                        skill.setLabel(jsonObject.getString("label"));
                                        skill.setCv_id(jsonObject.getInt("cv_id"));

                                        skillsList.add(skill);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                skills = "";
                                if (skillsList.size() == 0) {
                                    skills_tv.setText("None");
                                } else {
                                    for (int i = 0; i < skillsList.size(); i++) {
                                        if (i != skillsList.size() - 1) {
                                            skills = skills + skillsList.get(i).getLabel() + ", ";
                                        } else {
                                            skills = skills + skillsList.get(i).getLabel();
                                        }
                                    }
                                }
                                skills_tv.setText(skills);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(jsonArrayRequest);
    }
    //------------------------------------------------------

    private void getJsonArrayEducation() {
        educationList = new ArrayList<>();
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        try {

                            if (response.getInt("success") == 1) {
                                jsonArray = response.getJSONArray("result");

                                //  System.out.println("RESPONSE: " + response.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        Education education = new Education();

                                        education.setId(jsonObject.getInt("id"));
                                        education.setInst_name(jsonObject.getString("inst_name"));
                                        education.setStart_date(jsonObject.getString("start_date"));
                                        education.setEnd_date(jsonObject.getString("end_planned_date"));
                                        education.setDegree(jsonObject.getString("degree"));
                                        education.setDomain(jsonObject.getString("domain"));
                                        education.setResult(jsonObject.getString("result"));

                                        educationList.add(education);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (educationList.get(0).getInst_name().length() > 33) {
                                    textEduc.setLines(2);
                                } else if (educationList.get(0).getInst_name().length() > 66) {
                                    textEduc.setLines(3);
                                } else if (educationList.get(0).getInst_name().length() > 132) {
                                    textEduc.setLines(4);
                                }
                                textEduc.setText(educationList.get(0).getInst_name());
                            } else if (response.getInt("success") == 0) {
                                recyclerViewEduc.setVisibility(View.GONE);
                            }
                            if(educationList.size()!= 0)
                                textEduc.setText(educationList.get(0).getInst_name());
                            else
                                textEduc.setText("-");
                            adapterEduc = new EducationAdapter(getActivity().getApplicationContext(), educationList);
                            //adapterEduc.notifyDataSetChanged();
                            recyclerViewEduc.setAdapter(adapterEduc);
                            adapterEduc.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("MYYYLIST" + educationList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", error.getMessage());
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(jsonArrayRequest);
    }

    //------------------------------------------------------
    private void getJsonArrayExperience() {
        experienceList = new ArrayList<>();
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url2,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        try {
                            if (response.getInt("success") == 1) {
                                jsonArray = response.getJSONArray("result");

                                //  System.out.println("RESPONSE: " + response.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        Experience experience = new Experience();

                                        experience.setId(jsonObject.getInt("id"));
                                        experience.setLabel(jsonObject.getString("label"));
                                        experience.setStart_date(jsonObject.getString("start_date"));
                                        experience.setEnd_date(jsonObject.getString("end_date"));
                                        experience.setDescription(jsonObject.getString("description"));
                                        experience.setStill_going(jsonObject.getInt("still_going"));
                                        experience.setEstablishmentName(jsonObject.getString("establishment_name"));

                                        experienceList.add(experience);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (experienceList.get(0).getEstablishmentName().length() > 33) {
                                    tvTitle.setLines(2);
                                } else if (experienceList.get(0).getEstablishmentName().length() > 66) {
                                    tvTitle.setLines(3);
                                } else if (experienceList.get(0).getEstablishmentName().length() > 132) {
                                    tvTitle.setLines(4);
                                }
                                tvTitle.setText(experienceList.get(0).getLabel() + " at " + experienceList.get(0).getEstablishmentName());
                            } else if (response.getInt("success") == 0) {
                                recyclerViewExp.setVisibility(View.GONE);
                            }
                            adapterExp = new ExperienceAdapter(getActivity().getApplicationContext(), experienceList);
                            //adapterExp.notifyDataSetChanged();
                            recyclerViewExp.setAdapter(adapterExp);
                            adapterExp.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("MYYYLIST" + experienceList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(jsonArrayRequest);
    }

    //------------------------------------------------------
    private void getJsonArrayCertification() {
        certificationList = new ArrayList<>();
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url3,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        try {
                            if (response.getInt("success") == 1) {
                                jsonArray = response.getJSONArray("result");

                                //  System.out.println("RESPONSE: " + response.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        Certification certif = new Certification();

                                        certif.setId(jsonObject.getInt("id"));
                                        certif.setLabel(jsonObject.getString("label"));
                                        certif.setCert_authority(jsonObject.getString("cert_authority"));
                                        certif.setLicence_num(jsonObject.getString("licence_num"));
                                        certif.setIf_expire(jsonObject.getInt("if_expire"));
                                        certif.setCert_date(jsonObject.getString("cert_date"));
                                        if (jsonObject.getInt("if_expire") == 1) {
                                            certif.setExpire_date(jsonObject.getString("expire_date"));
                                        }

                                        certificationList.add(certif);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else if (response.getInt("success") == 0) {
                                recyclerViewCert.setVisibility(View.GONE);
                            }
                            adapterCert = new CertificationsAdapter(getActivity().getApplicationContext(), certificationList);
                            //adapterCert.notifyDataSetChanged();
                            recyclerViewCert.setAdapter(adapterCert);
                            adapterCert.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("MYYYLIST" + certificationList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", error.getMessage());
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(jsonArrayRequest);
    }

    //------------------------------------------------------
    private void getJsonArrayLanguage() {
        languageList = new ArrayList<>();
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url4,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        try {
                            if (response.getInt("success") == 1) {
                                jsonArray = response.getJSONArray("result");

                                //  System.out.println("RESPONSE: " + response.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        Language language = new Language();

                                        language.setId(jsonObject.getInt("id"));
                                        language.setLabel(jsonObject.getString("label"));
                                        language.setLevel(jsonObject.getString("level"));

                                        languageList.add(language);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else if (response.getInt("success") == 0) {
                                recyclerViewLang.setVisibility(View.GONE);
                            }

                            adapterLang = new LanguagesAdapter(getActivity().getApplicationContext(), languageList);
                            //adapterLang.notifyDataSetChanged();
                            recyclerViewLang.setAdapter(adapterLang);
                            adapterLang.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("MYYYLIST" + languageList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", error.getMessage());
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(jsonArrayRequest);
    }

    //------------------------------------------------------
    private void getJsonArrayVolunteer() {
        progressBarProfile.setVisibility(View.VISIBLE);

        volunteerList = new ArrayList<>();
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url5,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        try {
                            if (response.getInt("success") == 1) {
                                jsonArray = response.getJSONArray("result");

                                //  System.out.println("RESPONSE: " + response.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        Volunteer vol = new Volunteer();

                                        vol.setId(jsonObject.getInt("id"));
                                        vol.setOrganisation(jsonObject.getString("organisation"));
                                        vol.setRole(jsonObject.getString("role"));
                                        vol.setStart_date(jsonObject.getString("start_date"));
                                        vol.setStill_going(jsonObject.getInt("still_going"));

                                        vol.setEnd_date("Today");

                                        if (jsonObject.getInt("still_going") == 0) {
                                            vol.setEnd_date(jsonObject.getString("end_date"));
                                        }


                                        volunteerList.add(vol);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else if (response.getInt("success") == 0) {
                                recyclerViewVol.setVisibility(View.GONE);
                            }
                            adapterVol = new VolunteerAdapter(getActivity().getApplicationContext(), volunteerList);
                            //adapterVol.notifyDataSetChanged();
                            recyclerViewVol.setAdapter(adapterVol);
                            adapterVol.notifyDataSetChanged();

                            progressBarProfile.setVisibility(View.GONE);
                            profileNestedScrollView.setAlpha(1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("MYYYLIST" + volunteerList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", error.getMessage());
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(jsonArrayRequest);
    }
    //------------------------------------------------------


    //PERSSION TO ACCESS GALERY
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CODE_GALLERY_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), CODE_GALLERY_REQUEST);
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "You don't have permission to access gallery", Toast.LENGTH_LONG).show();
                ;
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public String getPath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    // PUT IMAGE ON IMAGE VIEW AFTER CHOOSING
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            filePathUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePathUri);
                profilePic.setImageBitmap(bitmap);
                new UploadFileToServer().execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //CONVERT IMAGE TO BASE64 STRING
    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    //======================================================
    /*
     * This is the method responsible for image upload
     * We need the full image path and the name for the image in this method
     * */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {


        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            progressBarProfile.bringToFront();
            progressBarProfile.invalidate();
            progressBarProfile.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(UPLOAD_URL);
            HttpClient client = new DefaultHttpClient();
            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, new AndroidMultiPartEntity.ProgressListener() {
                    @Override
                    public void transferred(long num) {
                        publishProgress((int) ((num / (float) totalSize) * 100));
                    }
                });

                File sourceFile = new File(getPath(filePathUri));
                // Adding file data to http body
                entity.addPart("file", new FileBody(sourceFile));
                // Adding user_id
                entity.addPart("user_id", new StringBody(String.valueOf(connectedUser.getId()), Charset.forName("UTF-8")));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: " + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            // showing the server response in an alert dialog
            progressBarProfile.setVisibility(View.GONE);
            showAlert(result);
            super.onPostExecute(result);
        }
    }

    /**
     * Method to show alert dialog
     */
    private void showAlert(String message) {
        if (message.contains("jpg") || message.contains("jpeg") || message.contains("png")) {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme).create();
            alertDialog.setTitle("Image upload");
            alertDialog.setMessage("Your profile image has been Changed successfully !");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    /*@Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            fileUri = savedInstanceState.getParcelable("file_uri");
        }
    }

    public void refreshFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

}


