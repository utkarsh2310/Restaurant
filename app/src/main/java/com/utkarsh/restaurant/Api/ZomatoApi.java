package com.utkarsh.restaurant.Api;

import com.utkarsh.restaurant.Model.Geocode;
import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ZomatoApi {

    @Headers({
            "Accept: application/json",
            "user-key: 5f7e18c358a9714e46cee944446d6d13",
            "content-type: application/json"
    })
    @GET("geocode")
    Call<Geocode> getRes(@Query("lat") double Lat, @Query("lon") double Lon);
}
