package com.esprit.jobhunter;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.jobhunter.CompanyFragments.AddOffersFragment;
import com.esprit.jobhunter.CompanyFragments.CompanyContactFragment;
import com.esprit.jobhunter.CompanyFragments.MyOffersFragment;
import com.esprit.jobhunter.CompanyFragments.ProfilCompanyFragment;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.jobhunter.Entity.User;

import com.esprit.jobhunter.MyUtils.GlobalParams;
import com.squareup.picasso.Picasso;


public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.esprit.jobhunter.R.layout.activity_main2);
        //----------------Getting connected user
        Intent fromLogin = getIntent();
        user = (User) fromLogin.getSerializableExtra("connectedUser");
        //----------------
        Toolbar toolbar = (Toolbar) findViewById(com.esprit.jobhunter.R.id.toolbar2);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search");
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
            navUsername.setText(user.getName());
            ImageView navUserimage = (ImageView) headerView.findViewById(com.esprit.jobhunter.R.id.header_imageView);
            /*ImageRequest imageLoad = new ImageRequest(GlobalParams.ressourceUrl+"/"+user.getPicture(), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    // callback
                    navUserimage.setImageBitmap(response); }
            }, 100, 100, null, null);
            RequestQueue queueImage = Volley.newRequestQueue(getApplicationContext());;
            queueImage.add(imageLoad);*/
            if(user.getPicture().length()>60){
                Picasso.with(this.getApplicationContext()).load(user.getPicture()).into(navUserimage);
            } else {
                Picasso.with(this.getApplicationContext()).load(GlobalParams.ressourceUrl+"/"+user.getPicture()).into(navUserimage);
            }
            //Picasso.with(this.getApplicationContext()).load(user.getPicture()).into(navUserimage);
        }
        //----------------------
        fragment = new MyOffersFragment();
        navigationView.setCheckedItem(com.esprit.jobhunter.R.id.nav_gallery);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(com.esprit.jobhunter.R.id.flContent, fragment);
        tx.commit();
    }

    public User getConnectedUser(){
        return this.user;
    }

    public void setConnectedUser(User user){
        this.user = user;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.esprit.jobhunter.R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(com.esprit.jobhunter.R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Search...");




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
                return false;
            }
        });
        return true;
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
            // close search view on back button pressed
            if (!searchView.isIconified()) {
                searchView.setIconified(true);
                return;
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
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == com.esprit.jobhunter.R.id.nav_camera) {
            fragment = new AddOffersFragment();

        } else if (id == com.esprit.jobhunter.R.id.nav_gallery) {
            fragment = new MyOffersFragment();

        } else if (id == com.esprit.jobhunter.R.id.nav_manage) {
            fragment = new CompanyContactFragment();

        } else if (id == com.esprit.jobhunter.R.id.nav_share) {
            fragment = new ProfilCompanyFragment();
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
