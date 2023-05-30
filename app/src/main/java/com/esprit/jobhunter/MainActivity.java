package com.esprit.jobhunter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.jobhunter.Adapters.SearchAdapter;
import com.esprit.jobhunter.Entity.Offer;
import com.esprit.jobhunter.MenuFragments.OffersFragment;
import com.esprit.jobhunter.OffersFragments.JobDetailsFragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.jobhunter.Entity.Job;
import com.esprit.jobhunter.Entity.User;
import com.esprit.jobhunter.MenuFragments.ApplicationsFragment;
import com.esprit.jobhunter.MenuFragments.ContactsFragment;
import com.esprit.jobhunter.MenuFragments.FavoritesFragment;
import com.esprit.jobhunter.MenuFragments.ProfileFragment;
import com.esprit.jobhunter.MyUtils.GlobalParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    SearchView searchView;
    User user;

    private Fragment fragment = null;
    private FragmentManager fragmentManager;

    //------Linkedin vars
    TextView NameLastName;
    Boolean f = false;
    ImageView imgProfil;
    //------
    //SearchView Vars
    //SearchView.SearchAutoComplete searchAutoComplete;
    private String JsonString = "";
    private  ArrayList<Offer> offersList;
    ArrayList<Offer> list;
    Job jobToSend;
    Job x;
    //-----------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.esprit.jobhunter.R.layout.activity_main);
        //-----------SearchView inits
        offersList = new ArrayList<>();

        //---------------Getting connected user
        Intent fromLogin = getIntent();
        user = (User) fromLogin.getSerializableExtra("connectedUser");
        //----------------
        Toolbar toolbar = (Toolbar) findViewById(com.esprit.jobhunter.R.id.toolbar);
        setSupportActionBar(toolbar);
        // toolbar fancy stuff
        getSupportActionBar().setTitle("Search");

        // Get ActionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //-------

        //-------

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        drawer = (DrawerLayout) findViewById(com.esprit.jobhunter.R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, com.esprit.jobhunter.R.string.navigation_drawer_open, com.esprit.jobhunter.R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(com.esprit.jobhunter.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //-------------set Header name/image

        if(getIntent().getExtras().containsKey("connectedUser"))
        {
            View headerView = navigationView.getHeaderView(0);
            TextView navUsername = (TextView) headerView.findViewById(com.esprit.jobhunter.R.id.header_name);
            navUsername.setText(user.getName()+" "+user.getLast_name());
            user.setPicture(user.getPicture().replace("%2F","/"));
            imgProfil = (ImageView) headerView.findViewById(com.esprit.jobhunter.R.id.header_imageView);

            /*ImageRequest imageLoad = new ImageRequest(GlobalParams.ressourceUrl+"/"+user.getPicture(), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    // callback
                    imgProfil.setImageBitmap(response); }
            }, 100, 100, null, null);
            RequestQueue queueImage = Volley.newRequestQueue(getApplicationContext());;
            queueImage.add(imageLoad);*/
            if(user.getPicture().length()>60){
                Picasso.with(this.getApplicationContext()).load(user.getPicture()).into(imgProfil);
            } else {
                Picasso.with(this.getApplicationContext()).load(GlobalParams.ressourceUrl+"/"+user.getPicture()).into(imgProfil);
            }

        }

        //----------------------
        fragment = new OffersFragment();
        navigationView.setCheckedItem(com.esprit.jobhunter.R.id.nav_offers);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(com.esprit.jobhunter.R.id.flContent, fragment);
        tx.commit();

    }

    //----------------------
    public User getConnectedUser(){
        return this.user;
    }

    public void setConnectedUser(User user){
        this.user = user;
    }

    //-------------------------------------
    private void getJobById(int id, final BiConsumer<Job,Nullable> consumer){
        x = new Job();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest getData = new StringRequest(Request.Method.GET, GlobalParams.url+"/getjobbyid/"+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getInt("success") == 1) {
                        JSONArray row = new JSONArray(obj.getString("result"));
                        JSONObject use = row.getJSONObject(0);
                        Job job = new Job();

                        job.setId(use.getInt("id"));
                        job.setLabel(use.getString("label"));
                        job.setDescription(use.getString("description"));
                        job.setStart_date(use.getString("start_date"));
                        job.setContract_type(use.getString("contract_type"));
                        job.setCareer_req(use.getString("career_req"));
                        job.setUser_id(use.getString("user_id"));
                        job.setCompany_name(use.getString("name"));
                        job.setCompany_pic(use.getString("picture"));
                        job.setSkills(use.getString("skills"));
                        consumer.accept(job,null);
                        x = job;

                    }
                } catch (JSONException e) {
                    Log.e("JSONExeption", e.getMessage());
                }
                jobToSend = x;
                System.out.println("UUUUUUUUUUUUUUUUUU: "+jobToSend);
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

    //-------------------------------------

    private void fillOffers(final BiConsumer<List<Offer>,Nullable> consumer){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonArrayRequest2 = new JsonObjectRequest(
                Request.Method.GET,
                GlobalParams.url+"/internships",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        List<Offer> list = new ArrayList<>();
                        try {
                            jsonArray = response.getJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    //System.out.println("JSONOBJECT " + jsonObject);
                                    Offer internship = new Offer();

                                    internship.setId(jsonObject.getInt("id"));
                                    internship.setLabel(jsonObject.getString("label"));
                                    internship.setCmp_name(jsonObject.getString("name"));
                                    internship.setCmp_pic(jsonObject.getString("picture"));
                                    internship.setType("i");

                                    list.add(internship);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            consumer.accept(list,null);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("LLLLLLLLLLL:  "+offersList);
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
        queue.add(jsonArrayRequest2);
        //----
    }

    private void fillOffers2(final BiConsumer<List<Offer>,Nullable> consumer)
    {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                GlobalParams.url+"/jobs",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        List<Offer> list = new ArrayList<>();
                        try {
                            jsonArray = response.getJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    //System.out.println("JSONOBJECT " + jsonObject);
                                    Offer job = new Offer();

                                    job.setId(jsonObject.getInt("id"));
                                    job.setLabel(jsonObject.getString("label"));
                                    job.setCmp_name(jsonObject.getString("name"));
                                    job.setCmp_pic(jsonObject.getString("picture"));
                                    job.setType("j");

                                    list.add(job);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            consumer.accept(list,null);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
        queue.add(jsonArrayRequest);
    }
    //-------------------------------------


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Search...");

        //-------------
        offersList = new ArrayList<>();
        list = new ArrayList<>();
        offersList = fillOffers();
        SearchAdapter searchAdapter = new SearchAdapter(this, R.layout.search_row, offersList);
        //----------

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                //mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                //mAdapter.getFilter().filter(query);
                System.out.println(query);
                return false;
            }
        });
        return true;
    }*/
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the search menu action bar.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(com.esprit.jobhunter.R.menu.action_bar_search_example_menu, menu);

        // Get the search menu.
        MenuItem searchMenu = menu.findItem(com.esprit.jobhunter.R.id.app_bar_menu_search);

        // Get SearchView object.
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenu);

        // Get SearchView autocomplete object.
        final SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        //searchAutoComplete.setBackgroundColor(Color.BLUE);
        searchAutoComplete.setTextColor(Color.BLACK);
        //searchAutoComplete.setDropDownBackgroundResource(android.R.color.holo_blue_light);

        // Create a new ArrayAdapter and add data to search auto complete object.
        //String dataArr[] = {"Apple" , "Amazon" , "Amd", "Microsoft", "Microwave", "MicroNews", "Intel", "Intelligence"};
        //ArrayAdapter<String> newsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, dataArr);

        //offersList = new ArrayList<>();

        //------------------------------------------
        //fillOffers();
        //------------------------------------------

        System.out.println("MMMMMMMMMMMMMMMMMMMMMM:  "+offersList);

        fillOffers((data,n)-> fillOffers2((data2, n2)->{
            data.addAll(data2);
            final SearchAdapter searchAdapter = new SearchAdapter(this, com.esprit.jobhunter.R.layout.search_row, data);
            searchAutoComplete.setAdapter(searchAdapter);
            searchAutoComplete.setThreshold(1);
        }));

        // Listen to search view item on click event.
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                /*String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText("" + queryString);
                Toast.makeText(MainActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();*/
                Offer offer = (Offer) adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText(offer.getLabel());
                //--------
                if(offer.getType().equals("j")){
                    getJobById(offer.getId(),(data,n)->{
                        Fragment fragment = new JobDetailsFragment();
                        ((JobDetailsFragment) fragment).setData(data);
                        getSupportFragmentManager().beginTransaction().replace(com.esprit.jobhunter.R.id.flContent, fragment).addToBackStack(null).commit();
                    });

                } else if (offer.getType().equals("i")){

                }

                //--------
            }
        });

        // Below event is triggered when submit search query.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /*AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setMessage("Search keyword is " + query);
                alertDialog.show();*/
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        // Get the share menu item.
        //MenuItem shareMenuItem = menu.findItem(R.id.app_bar_menu_share);
        // Because it's actionProviderClass is ShareActionProvider, so after below settings
        // when click this menu item A sharable applications list will popup.
        // User can choose one application to share.
        //ShareActionProvider shareActionProvider = (ShareActionProvider)MenuItemCompat.getActionProvider(shareMenuItem);
        //Intent shareIntent = new Intent(Intent.ACTION_SEND);
        //shareIntent.setType("image/*");
        //shareActionProvider.setShareIntent(shareIntent);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.esprit.jobhunter.R.id.action_search) {
            return true;
        }
        if (id == com.esprit.jobhunter.R.id.nav_offers) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(com.esprit.jobhunter.R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();
    }


    /*@Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //-------------------------
        Fragment fragment = null;
        //Class fragmentClass;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == com.esprit.jobhunter.R.id.nav_offers) {
            fragment = new OffersFragment();
        } else if (id == com.esprit.jobhunter.R.id.nav_favorites) {
            fragment = new FavoritesFragment();
        } else if (id == com.esprit.jobhunter.R.id.nav_applications) {
            fragment = new ApplicationsFragment();
        } else if (id == com.esprit.jobhunter.R.id.nav_contacts) {
            fragment = new ContactsFragment();
        } else if (id == com.esprit.jobhunter.R.id.nav_profile) {
            fragment = new ProfileFragment();
        } else if (id == com.esprit.jobhunter.R.id.nav_signout) {
            Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginIntent);
            finish();
            return true;
        }

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(com.esprit.jobhunter.R.id.flContent, fragment);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(com.esprit.jobhunter.R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    
    //----Fragments Display

}
