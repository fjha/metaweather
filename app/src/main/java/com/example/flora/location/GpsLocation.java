package com.example.flora.location;
import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class GpsLocation extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    String mprovider;
    private WebView webView ;
    double latiInt ;
    double longiInt;
    SharedPreferences.Editor editor;
    SharedPreferences preferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         preferences= PreferenceManager.getDefaultSharedPreferences(this);
         editor= preferences.edit();
        DecimalFormat df = new DecimalFormat("#.#####");
      // setContentView(R.layout.gps_location);
        String query1="https://www.metaweather.com/api/location/search/?lattlong="+preferences.getString("LatitudePref","0")+","+preferences.getString("LongitudePref","0") ;
        System.out.println("Current Location : "+query1);
        String query="https://www.metaweather.com/2459269/" ;
        String query2="https://www.metaweather.com/api/location/search/?lattlong="+df.format(longiInt)+","+df.format(latiInt) ;
        Toast.makeText(getBaseContext(), query1, Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_browser);

        webView = (WebView) findViewById(R.id.webView1);

        webView.loadUrl(query1);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        mprovider = locationManager.getBestProvider(criteria, false);

        if (mprovider != null && !mprovider.equals("")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(mprovider);
            locationManager.requestLocationUpdates(mprovider, 1, 0, this);



            if (location != null)
            { onLocationChanged(location);
             //  Toast.makeText(getBaseContext(), query1, Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getBaseContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        TextView longitude = (TextView) findViewById(R.id.textView);
        TextView latitude = (TextView) findViewById(R.id.textView1);
        latiInt =location.getLatitude();
        longiInt=location.getLongitude();
       // longitude.setText("Current Longitude:" + location.getLongitude());
        //latitude.setText("Current Latitude:" + location.getLatitude());
        DecimalFormat df = new DecimalFormat("#.#####");
        editor.putString("LatitudePref", Double.toString(Double.parseDouble(df.format(latiInt))));
        editor.putString("LongitudePref", Double.toString(Double.parseDouble(df.format(longiInt))));
        editor.commit();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}