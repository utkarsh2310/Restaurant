//package com.utkarsh.restaurant.QueryUtils;
//
//import android.net.Uri;
//import android.util.Log;
//
//import com.utkarsh.restaurant.Data.NearbyRestaurant;
//import com.utkarsh.restaurant.RestaurantAdapter;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.List;
//
//public class QueryData {
//
//    private static final String QUERY_LAT = "lat";
//    private static final String QUERY_LON = "lon";
//
//    public static URL builtUri(String Base_Geocode_URL,String Lat, String Lon) {
//        Uri uri = Uri.parse(Base_Geocode_URL).buildUpon()
//                .appendQueryParameter(QUERY_LAT , Lat)
//                .appendQueryParameter(QUERY_LON, Lon)
//                .build();
//
//        Log.v("Query data", "builtUri me aa gaye *******"+
//                "************" + Lat);
//        URL url = QueryData.createUrl(uri.toString());
//        return url;
//    }
//
//    public static URL createUrl(String urlString) {
//        URL url = null;
//        try {
//            url = new URL(urlString);
//        }
//        catch (MalformedURLException e) {
//            Log.e("Query", "Url is malformed");
//        }
//        return url;
//    }
//
//    public static String makeHttpRequest(URL url) {
//        HttpURLConnection urlConnection = null;
//        InputStream inputStream = null;
//        String JsonResponse = "";
//        try {
//            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.setRequestProperty("user-key","5f7e18c358a9714e46cee944446d6d13");
//            urlConnection.setRequestProperty("Accept", "application/json");
//            urlConnection.connect();
//            inputStream = urlConnection.getInputStream();
//            Log.v("inpupt stream ", "************" + inputStream);
//            JsonResponse = readFromInputStream(inputStream);
//        }
//        catch (IOException e) {
//            Log.e("Query","connection not established" + e.getMessage());
//            return null;
//        }
//        return JsonResponse;
//    }
//
//    private static String readFromInputStream(InputStream inputStream) throws IOException {
//        StringBuilder builder = new StringBuilder();
//        if (inputStream != null) {
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,  Charset.forName("UTF-8"));
//            BufferedReader reader = new BufferedReader(inputStreamReader);
//            String line = reader.readLine();
//            while (line != null) {
//                builder.append(line);
//                line = reader.readLine();
//            }
//        }
//
//        return builder.toString();
//    }
//
//    public static List<NearbyRestaurant> extractRestaurants(String JsonString) {
//        List<NearbyRestaurant> nearbyRestaurantArrayList = null;
//
//        try {
//            nearbyRestaurantArrayList = new ArrayList<>();
//            JSONObject entry = new JSONObject(JsonString);
//            JSONObject locality = entry.getJSONObject("location");
//            String Entity_Id = locality.getString("entity_id");
//            String Entity_Type = locality.getString("entity_type");
//            JSONArray nearbyRestaurants = entry.getJSONArray("nearby_restaurants");
//
//            for (int i=0; i<nearbyRestaurants.length(); i++) {
//                JSONObject o = nearbyRestaurants.getJSONObject(i);
//                JSONObject res = o.getJSONObject("restaurant");
//                JSONObject r = res.getJSONObject("R");
//                String Res_Id = r.getString("res_id");
//                String ResName = res.getString("name");
//                JSONObject loc = res.getJSONObject("location");
//                String ResLocation = loc.getString("address");
//                String ResLocality = loc.getString("locality");
//                String ResCity = loc.getString("city");
//
//                NearbyRestaurant nearbyRestaurant = new NearbyRestaurant(Res_Id,ResName,ResLocation,ResLocality);
//                nearbyRestaurantArrayList.add(nearbyRestaurant);
//            }
//        }
//        catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return nearbyRestaurantArrayList;
//    }
//}
