package com.utkarsh.restaurant.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.utkarsh.restaurant.Model.NearbyRestaurant;
import com.utkarsh.restaurant.R;
import com.utkarsh.restaurant.ViewModel.ResViewModel;
import com.utkarsh.restaurant.adapters.RestaurantAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    ArrayList<NearbyRestaurant> nearbyRestaurants = new ArrayList<>();
    public double Latitude;
    public double Longitude;
    private RecyclerView.LayoutManager layoutManager;
    public RecyclerView resTv;
    RestaurantAdapter adapter;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 10 * 1000;
    private long FASTEST_INTERVAL = 2000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG,"onCreate is called *********************************************************");
        resTv = findViewById(R.id.restaurants_rv);
        checkPermissions();
        setupRecyclerView();
    }

    private void setUpviewModel(double latitude, double longitude) {
        ResViewModel model = new ViewModelProvider(this).get(ResViewModel.class);
        model.init(latitude,longitude);
        model.getData().observe(this, Geocode -> {
            List<NearbyRestaurant> restaurants = Geocode.getNearbyRestaurants();
            nearbyRestaurants.addAll(restaurants);
            adapter.notifyDataSetChanged();
        });
    }

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }
        else {
            startLocationUpdates();
            Log.v(TAG, "permission grant hai *************************************************");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();

                } else {
                    Toast.makeText(this,"permission denied",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private void setupRecyclerView() {
        if (adapter == null) {
            adapter = new RestaurantAdapter(nearbyRestaurants);
            resTv.setAdapter(adapter);
            layoutManager = new LinearLayoutManager(this);
            resTv.setLayoutManager(layoutManager);
        }
        else {
            adapter.notifyDataSetChanged();
        }
    }
    public void startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        if (locationResult == null) {
                            return;
                        }
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }

    private void onLocationChanged(android.location.Location location) {
        if (location != null) {
            Log.v("Mainaactivity","***********************************" +
                    "********************************************************" +
                    "********************" + "this is called");
            Latitude = location.getLatitude();
            Longitude = location.getLongitude();
            setUpviewModel(Latitude,Longitude);
        }

    }
}
