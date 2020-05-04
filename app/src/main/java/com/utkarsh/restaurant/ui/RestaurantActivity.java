package com.utkarsh.restaurant.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.utkarsh.restaurant.Model.Location_;
import com.utkarsh.restaurant.Model.NearbyRestaurant;
import com.utkarsh.restaurant.Model.Restaurant;
import com.utkarsh.restaurant.R;
import com.utkarsh.restaurant.ViewModel.ResViewModel;
import com.utkarsh.restaurant.adapters.RestaurantAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RestaurantsFragment fragment = new RestaurantsFragment();

        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction()
                .add(R.id.restaurant_container,fragment)
                .commit();
    }

}
