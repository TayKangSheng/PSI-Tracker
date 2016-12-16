package com.taykangsheng.www.singaporepowerpsitracker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.taykangsheng.www.singaporepowerpsitracker.fragments.AlertDialogFragment;
import com.taykangsheng.www.singaporepowerpsitracker.fragments.Map;
import com.taykangsheng.www.singaporepowerpsitracker.fragments.PSI_24hr;
import com.taykangsheng.www.singaporepowerpsitracker.fragments.PSI_3hr;
import com.taykangsheng.www.singaporepowerpsitracker.fragments.p_subindex;
import com.taykangsheng.www.singaporepowerpsitracker.helpers.AsyncHttpRequestHelper;
import com.taykangsheng.www.singaporepowerpsitracker.helpers.DataHelper;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        AsyncHttpRequestHelper.AsyncResponseListener {
    private FragmentManager fragmentManager;
    public MenuItem currentNavigationItem;
    public MenuItem selectedNavigationItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNavDrawer();
        RetrievePSIData();
    }

    private void setNavDrawer(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        drawer.addDrawerListener(new DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Log.d("PSITracker", "onDrawerClosed");

                int cur_id = currentNavigationItem.getItemId();
                int id = selectedNavigationItem.getItemId();
                if (cur_id != id){
                    refreshFragment();
                }
                currentNavigationItem = selectedNavigationItem;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener( this );

        /* Select the first item by default which is map */
        currentNavigationItem = navigationView.getMenu().getItem(0);
        selectedNavigationItem = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Map MapFragment = Map.newInstance();
        fragmentTransaction.replace(R.id.fragment_container, MapFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        MenuItem item = menu.findItem(R.id.refresh);
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
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

        int id = item.getItemId();

        if (id == R.id.refresh) {
            RetrieveMapData();
            RetrievePSIData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        selectedNavigationItem = item;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        item.setChecked(true);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void refreshFragment(){
        Log.d("PSITracker", "refreshFragment");
        int id = selectedNavigationItem.getItemId();
        if (id == R.id.nav_map) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Map MapFragment = Map.newInstance();
            fragmentTransaction.replace(R.id.fragment_container, MapFragment);
            fragmentTransaction.commitAllowingStateLoss();
        } else if (id == R.id.nav_3psi) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            PSI_3hr MapFragment = PSI_3hr.newInstance();
            fragmentTransaction.replace(R.id.fragment_container, MapFragment);
            fragmentTransaction.commitAllowingStateLoss();
        } else if (id == R.id.nav_24psi) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            PSI_24hr MapFragment = PSI_24hr.newInstance();
            fragmentTransaction.replace(R.id.fragment_container, MapFragment);
            fragmentTransaction.commitAllowingStateLoss();
        } else if (id == R.id.nav_psubindicies) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            p_subindex MapFragment = p_subindex.newInstance();
            fragmentTransaction.replace(R.id.fragment_container, MapFragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    private void RetrieveMapData(){
        AsyncHttpRequestHelper asyncTaskMapData = new AsyncHttpRequestHelper();
        asyncTaskMapData.delegate = this;

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore"), Locale.US);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Log.d("PSITracker", df.format(calendar.getTime()));

        String URL = "https://api.data.gov.sg/v1/environment/psi";
        String[] headers = {
                "api-key", "5oE2yNgO6mrIfFXG3Y4KNf7KGD4thuO7",
                "Accept", "*/*",
                "User-Agent", "Mozilla/5.0"
        };

        Bundle mapBundle = new Bundle();
        mapBundle.putString("address",URL);
        mapBundle.putStringArray("headers", headers);
        mapBundle.putString("errorMessage","Fail to receive latest updates.");
        mapBundle.putString("tag", "map");

        Log.d("PSITracker", "Execute Async");
        asyncTaskMapData.execute(mapBundle);
    }

    private void RetrievePSIData(){
        AsyncHttpRequestHelper asyncTaskPSIData = new AsyncHttpRequestHelper();
        asyncTaskPSIData.delegate = this;

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore"), Locale.US);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Log.d("PSITracker", df.format(calendar.getTime()));

        String URL = "https://api.data.gov.sg/v1/environment/psi";
        String[] headers = {
                "api-key", "5oE2yNgO6mrIfFXG3Y4KNf7KGD4thuO7",
                "Accept", "*/*",
                "User-Agent", "Mozilla/5.0"
        };
        String[] queries = {
                "date", df.format(calendar.getTime())
        };

        Bundle psiBundle = new Bundle();
        psiBundle.putString("address",URL);
        psiBundle.putStringArray("headers", headers);
        psiBundle.putStringArray("queries", queries);
        psiBundle.putString("errorMessage","Fail to receive chart updates.");
        psiBundle.putString("tag", "psi");

        asyncTaskPSIData.execute(psiBundle);
    }

    @Override
    public void AsyncResponse(JSONObject currentData, String error_message, String tag) {
        if (currentData == null){
            Log.d("PSITracker", error_message);
            Toast.makeText(getApplicationContext(), error_message, Toast.LENGTH_SHORT).show();
        } else {
            Log.d("PSITracker", "Successfully receive Data");
            DataHelper dataHelper = new DataHelper(this);

            if (tag == "map"){
                dataHelper.setMapData(currentData.toString());
            } else if (tag == "psi") {
                dataHelper.setPSIData(currentData.toString());
            }
        }
        refreshFragment();
    }
}
