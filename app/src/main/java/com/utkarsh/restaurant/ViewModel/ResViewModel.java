package com.utkarsh.restaurant.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.utkarsh.restaurant.Api.Repository;
import com.utkarsh.restaurant.Model.Geocode;

public class ResViewModel extends ViewModel {

    private MutableLiveData<Geocode> data;
    private Repository repository;

    public void init(double Latitude,double Longitude) {
        if (data != null) {
            return;
        }
        repository = Repository.getInstance();
        Log.v("resviewModel" , "latitude and longitude values are" +
                Latitude+ "***********************************" + Longitude);
        data = repository.getZomatoRestaurants(Latitude,Longitude);
    }

    public LiveData<Geocode> getData() {
        return data;
    }
}
