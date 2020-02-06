package com.example.yingli.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //private GoogleMap mMap;

    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("latitude", Double.toString(location.getLatitude()));

                Log.i("longitude", Double.toString(location.getLongitude()));

                Log.i("accuracy", Double.toString(location.getAccuracy()));

                displayInformation(location);







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
        };

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastKnownLocation != null){
                updateLocationInfo(lastKnownLocation);
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            startListening();
        }
    }

    public void startListening(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

    }

    public void updateLocationInfo(Location location){
        Log.i("location", location.toString());
    }

    public void displayInformation(Location location){
        TextView latTextView = findViewById(R.id.latitudeTextView);

        TextView longTextView = findViewById(R.id.longitudeTextView);

        TextView accTextView = findViewById(R.id.accuracyTextView);

        TextView addTextView = findViewById(R.id.addressTextView);


        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        String address = "";
        try {
            List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
            if(listAddresses != null && listAddresses.size() > 0){
                Log.i("PlaceInfo", listAddresses.get(0).toString());






                if(listAddresses.get(0).getAddressLine(0)!= null){
                    address+= listAddresses.get(0).getAddressLine(0) + " ";
                }else {

                    if (listAddresses.get(0).getThoroughfare() != null) {
                        address += listAddresses.get(0).getThoroughfare() + " ";
                    }
                    if (listAddresses.get(0).getLocality() != null) {
                        address += listAddresses.get(0).getLocality() + " ";
                    }
                    if (listAddresses.get(0).getPostalCode() != null) {
                        address += listAddresses.get(0).getPostalCode() + " ";
                    }


                    if (listAddresses.get(0).getAdminArea() != null) {
                        address += listAddresses.get(0).getAdminArea();
                    }
                }

//                Toast.makeText(MapsActivity.this, address, Toast.LENGTH_SHORT).show();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        latTextView.setText("Latitude: " + Double.toString(location.getLatitude()));
        longTextView.setText("Longtitude: " + Double.toString(location.getLongitude()));
        accTextView.setText("Accuracy: " + Double.toString(location.getAccuracy()));
        addTextView.setText("Address: " + address);

    }
}

