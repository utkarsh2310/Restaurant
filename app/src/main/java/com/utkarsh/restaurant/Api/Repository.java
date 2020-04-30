package com.utkarsh.restaurant.Api;

import androidx.lifecycle.MutableLiveData;

import com.utkarsh.restaurant.Model.Geocode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    public static Repository repository;

    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

    private ZomatoApi zomatoApi;
    public Repository() {
        zomatoApi = RetrofitClient.getClient().create(ZomatoApi.class);
    }

    public MutableLiveData<Geocode> getZomatoRestaurants(double lat, double lon) {
        final MutableLiveData<Geocode> resData = new MutableLiveData<>();
        zomatoApi.getRes(lat,lon).enqueue(new Callback<Geocode>() {
            @Override
            public void onResponse(Call<Geocode> call, Response<Geocode> response) {
                if (response.isSuccessful()) {
                    resData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Geocode> call, Throwable t) {

            }
        });
        return resData;
    }

}
