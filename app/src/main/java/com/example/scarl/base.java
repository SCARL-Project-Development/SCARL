package com.example.scarl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;


public class base extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LocationListener {
    private LocationManager locationManager;
    String loc;
    String lat;
    String Police_Url;
    String Hospital_Url;
    String Petrol_Url;
    public static String URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    public static String Key = "AIzaSyCyR0jsXvANbneb11u8yjDiu4o8mbNDpDc";

    @Override
    public void onLocationChanged(Location location) {
        //Toast.makeText(getApplicationContext(),location.getLatitude() + " " + location.getLongitude(),Toast.LENGTH_SHORT).show();
        lat  = Double.toString(location.getLatitude());
        loc = Double.toString(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }
    @Override
    public void onProviderEnabled(String s) {
    }
    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(getApplicationContext(),"Please Switch on the provider!!",Toast.LENGTH_LONG).show();
    }

    String temp;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        temp = "";
        try {
            temp = getIntent().getExtras().getString("logindev");
        }
        catch (Exception e)
        {

        }
        //sharedPreferences=getSharedPreferences("MyPrefs", MODE_PRIVATE);
        //String i=sharedPreferences.getString("DeviceNo.","");
        //temp=i;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                    }, 123);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);

        Criteria locationCriteria = new Criteria();
        locationCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        locationCriteria.setAltitudeRequired(false);
        locationCriteria.setBearingRequired(false);
        locationCriteria.setCostAllowed(true);
        locationCriteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        String providername=locationManager.getBestProvider(locationCriteria, true);
        Location l = locationManager.getLastKnownLocation(providername);
        Double lala=l.getLatitude();
        Double lolo=l.getLongitude();
        lat=Double.toString(lala);
        loc=Double.toString(lolo);

        Bundle b1=new Bundle();
        b1.putString("Lat", lat);
        b1.putString("Long", loc);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        MainFragment mainfrag=new MainFragment();
        mainfrag.setArguments(b1);
        fragmentTransaction.add(R.id.container_fragment, mainfrag);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(menuItem.getItemId() == R.id.carlocation){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new FragmentCarLocation());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.carcondition){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new Car_Details());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.passkeychange){
            Bundle bundle = new Bundle();
            bundle.putString("logindev", temp);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            FragmentPasskeyChange fragobj=new FragmentPasskeyChange();
            fragobj.setArguments(bundle);
            fragmentTransaction.replace(R.id.container_fragment, fragobj);
            fragmentTransaction.commit();
        }

        if(menuItem.getItemId() == R.id.cnps) {
            Toast.makeText(this, "Nearest Police station", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("geo:" +lat+ "," +loc+ "?q=Police_station"));
            startActivity(intent);
        }
        if(menuItem.getItemId() == R.id.cnpp)
        {
            Toast.makeText(this, "Nearest Petrol Pump", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("geo:" +lat+ "," +loc+ "?q=Petrol_Pump"));
            startActivity(intent);
        }
        if(menuItem.getItemId() == R.id.cnh)
        {
            Toast.makeText(this, "Nearest Hospitals", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("geo:" +lat+ "," +loc+ "?q=hospitals"));
            startActivity(intent);
        }
        if(menuItem.getItemId() == R.id.cnsc)
            Toast.makeText(this, "Nearest Service Center", Toast.LENGTH_SHORT).show();
        if(menuItem.getItemId() == R.id.travelhistory)
            Toast.makeText(this, "Travel History", Toast.LENGTH_SHORT).show();
        if(menuItem.getItemId() == R.id.logout)
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
        return true;
    }
}
