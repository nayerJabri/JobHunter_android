package com.esprit.jobhunter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.esprit.jobhunter.Entity.User;
import com.esprit.jobhunter.MyUtils.GlobalParams;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.function.Consumer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {

    String url;
    RequestQueue queue ;
    public User user;
    public User user1;
    int count;
    public int cd_id_linkedin;

    //UI Declarations
    Button signupBtn;
    Button signinBtn;
    //TextView login;
    //TextView password;
    private CheckBox remember_me;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    LinearLayout login_layout;
    ProgressBar loginProgress;
    Button viewmore,viewmore2;
    SignInButton imgLogin;
    LinearLayout login_layout2;
    ImageView imgProfil;
    EditText password,login;
    String login_text,password_text;
    String Name;
    /***** added new *****/
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE=9001;
    /*********/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.esprit.jobhunter.R.layout.activity_login);
        computePakageHash();
        loginProgress = (ProgressBar) findViewById(com.esprit.jobhunter.R.id.progressBarLogin);
        loginProgress.setVisibility(View.GONE);

        signupBtn = (Button) findViewById(com.esprit.jobhunter.R.id.signupButton);
        signinBtn = (Button) findViewById(com.esprit.jobhunter.R.id.signinButton);
        login_layout = (LinearLayout) findViewById(com.esprit.jobhunter.R.id.login_layout);
        login_layout2 = (LinearLayout) findViewById(com.esprit.jobhunter.R.id.login_layout2);
        signupBtn.setOnClickListener(signupBtnOnClickListener);
        signinBtn.setOnClickListener(signinBtnOnClickListener);
        /***** added new **********/
        handler.postDelayed(runnable,2000);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();

        /****************************/
        login = findViewById(com.esprit.jobhunter.R.id.emailInput);
        password = findViewById(com.esprit.jobhunter.R.id.passwordInput);
        user = new User();

        //Remember_me
        remember_me = (CheckBox)findViewById(com.esprit.jobhunter.R.id.remember_me);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            login.setText(loginPreferences.getString("username", ""));
            password.setText(loginPreferences.getString("password", ""));
            remember_me.setChecked(true);
        }
        initializeControls();
    }

    private View.OnClickListener signupBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            signupBtnClicked();
        }
    };

    private void signupBtnClicked() {
        Intent signupIntent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(signupIntent);
    }
    //--------------------------
    private View.OnClickListener signinBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            signinBtnClicked();
        }
    };

    //------------------------- LINKEDIN SDK
    private void initializeControls(){
        imgLogin = (SignInButton) findViewById(com.esprit.jobhunter.R.id.imgLogin);
        imgLogin.setOnClickListener(this);
    }
    private void computePakageHash() {
        try{
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.esprit.jobhunter",
                    PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (Exception e){
            Log.e("TAG", e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  com.esprit.jobhunter.R.id.imgLogin:
                handleLogin();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("amir1","aaaaa");
        if (requestCode == REQ_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            fetchPersonalInfo((result),(v)->{
                checkuser((v),(t)->{
                    if(count==0)
                        adduser(v);
                    else {
                        getFullUser(user1);
                    }
                });
            });
            login_layout.setAlpha(0);
        }
    }
    private  void handleLogin(){
        Intent intent =    Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);
    }
    private void fetchPersonalInfo(GoogleSignInResult result,Consumer<User> consumer){
        Log.d("amir","aaaaa");
        if(result.isSuccess()) {
            user1 = new User();
            GoogleSignInAccount account = result.getSignInAccount();

            String fname = account.getDisplayName();
            int space = fname.indexOf(" ");
            user1.setName(fname.substring(0,space));
            user1.setLast_name(account.getFamilyName());
            user1.setEmail(account.getEmail());
            if (account.getPhotoUrl() == null)
            {
               user1.setPicture("profile-default.png");
            }
            else {
                String ur = account.getPhotoUrl().toString();
                ur = ur.replace("/", "%2F");
                user1.setPicture(ur);
            }

            //user1.setPicture(account.getPhotoUrl().toString());
            System.out.println("user1" + user1);
            consumer.accept(user1);
        }
        else
        {
            Log.d("amir3","nope");
        }
    }

    private void checkuser(User user1,Consumer consumer){
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        queue = Volley.newRequestQueue(this);
        url = GlobalParams.url + "/getuserbyemail/";
        url += user1.getEmail();
        System.out.println(url);
        StringRequest getData = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    count = 0;
                    JSONObject obj = new JSONObject(response);
                    JSONArray row = new JSONArray(obj.getString("result"));

                    JSONObject use = row.getJSONObject(0);
                    count = use.getInt("count(email)");
                    consumer.accept(count);
                } catch (JSONException e) {
                    Log.e("JSONExeption", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                // Log.e("ErrorResponse", error.getMessage());
            }
        });
        queue.add(getData);

    }

    private void adduser(User user1){
        String url1 = GlobalParams.url+"/signupapplicant_linkedin/";
        System.out.println("piccccccccccccccccccccccc"+user1.getPicture());
        url1 += user1.getEmail() + "/" + user1.getPassword() +"/"+ user1.getName() +"/"+ user1.getLast_name() +"/"+ user1.getPicture()  ;
        System.out.println(url1);
        StringRequest getData = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getInt("success") == 1) {
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
        //--------------
        StringRequest getFullUser = new StringRequest(Request.Method.GET, GlobalParams.url+"/getuserlikedin/"+user1.getEmail(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getInt("success") == 1) {
                        JSONArray row = new JSONArray(obj.getString("result"));

                        JSONObject use = row.getJSONObject(0);

                        user1.setId(use.getInt("id"));
                        user1.setName(use.getString("name"));
                        user1.setLast_name(use.getString("last_name"));
                        user1.setBirth_date(use.getString("birth_date"));
                        user1.setGender(use.getString("gender"));
                        user1.setAdress(use.getString("adress"));
                        user1.setTel1(use.getString("tel1"));
                        user1.setTel2(use.getString("tel2"));
                        user1.setFax(use.getString("fax"));
                        user1.setNationality(use.getString("nationality"));
                        user1.setDescription(use.getString("description"));
                        user1.setPicture(use.getString("picture"));
                        user1.setType(use.getString("type"));
                        user1.setCv_id(use.getString("cv_id"));

                    }
                    //System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE:  "+user1);
                    Intent home = new Intent(getApplicationContext(), MainActivity.class);
                    home.putExtra("connectedUser", user1);
                    startActivity(home);
                    finish();
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
        //--------------
        queue.add(getData);
        queue.add(getFullUser);

    }
    //-------------------------

    private void signinBtnClicked() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(password.getWindowToken(), 0);

        login_text = login.getText().toString();
        password_text = password.getText().toString();

        if (remember_me.isChecked()) {
            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.putString("username", login_text);
            loginPrefsEditor.putString("password", password_text);
            loginPrefsEditor.commit();
        } else {
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();
        }
        //----------------------------
        loginProgress.setVisibility(View.VISIBLE);
        queue = Volley.newRequestQueue(this);

        url = GlobalParams.url + "/login/";

        if (login.getText().length() != 0 && password.getText().length() != 0){
            url += login.getText() + "/" + password.getText();
            //System.out.println(url);
            StringRequest getData = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.getInt("success") == 1) {
                            JSONArray row = new JSONArray(obj.getString("result"));

                            JSONObject use = row.getJSONObject(0);

                            user.setId(use.getInt("id"));
                            user.setName(use.getString("name"));
                            user.setLast_name(use.getString("last_name"));
                            user.setBirth_date(use.getString("birth_date"));
                            user.setGender(use.getString("gender"));
                            user.setEmail(use.getString("email"));
                            user.setAdress(use.getString("adress"));
                            user.setTel1(use.getString("tel1"));
                            user.setTel2(use.getString("tel2"));
                            user.setFax(use.getString("fax"));
                            user.setNationality(use.getString("nationality"));
                            user.setDescription(use.getString("description"));
                            user.setPicture(use.getString("picture"));
                            user.setType(use.getString("type"));
                            user.setCv_id(use.getString("cv_id"));

                            //System.out.println("UUUUUUUUUUUUUUUUUUUUUUU:  "+user);

                            if(user.getType().equals("a")){
                                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                                home.putExtra("connectedUser", user);
                                startActivity(home);
                                finish();
                            } else if(user.getType().equals("c")){
                                Intent home = new Intent(getApplicationContext(), Main2Activity.class);
                                home.putExtra("connectedUser", user);
                                startActivity(home);
                                finish();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid password or login", Toast.LENGTH_LONG).show();
                            login_layout.setAlpha(1);
                        }
                        loginProgress.setVisibility(View.GONE);
                        login_layout.setAlpha(1);
                    } catch (JSONException e) {
                        Log.e("JSONExeption", e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this, com.esprit.jobhunter.R.style.MyDialogTheme).create();
                    alertDialog.setTitle("Error !");
                    //alertDialog.setMessage("You have already applied to this job offer");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    if (error instanceof NetworkError) {
                        alertDialog.setMessage("Cannot connect to Internet...");
                        alertDialog.show();
                        loginProgress.setVisibility(View.GONE);
                    } else if (error instanceof ServerError) {
                        alertDialog.setMessage("The server could not be found...");
                        alertDialog.show();
                        loginProgress.setVisibility(View.GONE);
                    } else if (error instanceof AuthFailureError) {
                        alertDialog.setMessage("Cannot connect to Internet...");
                        alertDialog.show();
                        loginProgress.setVisibility(View.GONE);
                    } else if (error instanceof ParseError) {
                        alertDialog.setMessage("Parsing error...");
                        alertDialog.show();
                        loginProgress.setVisibility(View.GONE);
                    } else if (error instanceof NoConnectionError) {
                        alertDialog.setMessage("Cannot connect to Internet...");
                        alertDialog.show();
                        loginProgress.setVisibility(View.GONE);
                    } else if (error instanceof TimeoutError) {
                        alertDialog.setMessage("Connection TimeOut...");
                        alertDialog.show();
                        loginProgress.setVisibility(View.GONE);
                    }
                }
            });
            queue.add(getData);
        }

        //Intent signinIntent = new Intent(getApplicationContext(), MainActivity.class);
        //startActivity(signinIntent);
    }

    public void getFullUser(User user1){
        queue = Volley.newRequestQueue(this);
        //user1 = new User();
        StringRequest getFullUser = new StringRequest(Request.Method.GET, GlobalParams.url+"/getuserlikedin/"+user1.getEmail(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getInt("success") == 1) {
                        JSONArray row = new JSONArray(obj.getString("result"));

                        JSONObject use = row.getJSONObject(0);

                        user1.setId(use.getInt("id"));
                        user1.setName(use.getString("name"));
                        user1.setLast_name(use.getString("last_name"));
                        user1.setBirth_date(use.getString("birth_date"));
                        user1.setGender(use.getString("gender"));
                        user1.setAdress(use.getString("adress"));
                        user1.setTel1(use.getString("tel1"));
                        user1.setTel2(use.getString("tel2"));
                        user1.setFax(use.getString("fax"));
                        user1.setNationality(use.getString("nationality"));
                        user1.setDescription(use.getString("description"));
                        user1.setPicture(use.getString("picture"));
                        user1.setType(use.getString("type"));
                        user1.setCv_id(use.getString("cv_id"));

                    }
                    //System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE:  "+user1);
                    Intent home = new Intent(getApplicationContext(), MainActivity.class);
                    home.putExtra("connectedUser", user1);
                    startActivity(home);
                    finish();
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
        queue.add(getFullUser);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            login_layout.setVisibility(View.VISIBLE);
            login_layout2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
