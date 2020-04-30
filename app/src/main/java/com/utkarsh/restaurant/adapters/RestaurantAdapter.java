package com.utkarsh.restaurant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.utkarsh.restaurant.Model.NearbyRestaurant;
import com.utkarsh.restaurant.Model.Restaurant;
import com.utkarsh.restaurant.R;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {
    private List<NearbyRestaurant> nearbyRestaurants;

    public RestaurantAdapter(List<NearbyRestaurant> restaurants) {
        super();
        nearbyRestaurants = restaurants;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView resName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            resName = itemView.findViewById(R.id.res_titleTv);
        }
    }

    @NonNull
    @Override
    public RestaurantAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.res_view,parent,false);
        MyViewHolder vw = new MyViewHolder(itemView);
        return vw;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.MyViewHolder holder, int position) {
        TextView nameTextView = holder.resName;
        NearbyRestaurant nearbyRestaurant = nearbyRestaurants.get(position);
        Restaurant restaurant = nearbyRestaurant.getRestaurant();
        String name = restaurant.getName();
        nameTextView.setText(name);
    }

    @Override
    public int getItemCount() {
        return nearbyRestaurants.size();
    }

}
