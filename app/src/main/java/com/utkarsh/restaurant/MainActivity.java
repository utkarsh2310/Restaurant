package com.utkarsh.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.utkarsh.restaurant.Api.RetrofitClient;
import com.utkarsh.restaurant.Api.ZomatoApi;
import com.utkarsh.restaurant.Model.Geocode;
import com.utkarsh.restaurant.Model.NearbyRestaurant;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private FusedLocationProviderClient fusedLocationProviderClient;
    private RecyclerView.LayoutManager layoutManager;
    private Double Latitude;
    private Double Longitude;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    public static RecyclerView resTv;
    RestaurantAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Latitude = 0.0;
        Longitude = 0.0;

        Log.v(TAG,"onCreate is called *********************************************************");
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        locationCallback =  new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Log.v("mainactivity" , "location result pr aa gaye*******");
                onLocationChanged(locationResult.getLastLocation());
                retrofitCall();
            }
        };

        Log.v(TAG,"************************");
        resTv = findViewById(R.id.restaurants_rv);
        layoutManager = new LinearLayoutManager(this);
        resTv.setLayoutManager(layoutManager);
        checkPermissions();

    }

    public void retrofitCall() {

        Retrofit client = RetrofitClient.getClient();
        ZomatoApi zomatoApi = client.create(ZomatoApi.class);
        Log.v("retrofit is called" , "ho gaya retrofit call");
        final Call<Geocode> geocode = zomatoApi.getRes(Latitude,Longitude);
        geocode.enqueue(new Callback<Geocode>() {
            @Override
            public void onResponse(Call<Geocode> call, Response<Geocode> response) {
                if (response.code() == 200) {
                    List<NearbyRestaurant> nearbyRestaurants = response.body().getNearbyRestaurants();
                    adapter = new RestaurantAdapter(nearbyRestaurants);
                    resTv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Geocode> call, Throwable t) {
                Log.v("something happens","on failure is called" + t.getMessage());
            }
        });
    }

    private void onLocationChanged(Location location) {
        Latitude = location.getLatitude();
        Longitude = location.getLongitude();
        Log.v("values:*****","value of " + Double.toString(Longitude) +
                "value of latitide ********************" + Double.toString(Latitude));
    }

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }
        else {
            Log.v(TAG, "permission grant hai *************************************************");
            startLocationUpdates();
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

    private void startLocationUpdates() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Log.v(TAG,"fused location provider pr aa gaye ****************************************");
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, MainActivity.this.getMainLooper());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
}
