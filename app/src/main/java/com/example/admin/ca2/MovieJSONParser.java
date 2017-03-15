package com.example.admin.ca2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by admin on 15/03/2017.
 */

public class MovieJSONParser {


    public static ArrayList<Movie> parseFeed(String content) {
        ArrayList<Movie>movieList = new ArrayList<>();

        try {
            JSONObject jsonObj = new JSONObject(content);

            // Getting JSON Array node
            JSONArray movies = jsonObj.getJSONArray("movie");

            // looping through movies
            for (int i = 0; i < movies.length(); i++) {
                JSONObject m = movies.getJSONObject(i);

                String id = m.getString("movieId");
                String name = m.getString("name");
                String category = m.getString("category");
                String director = m.getString("category");
                String actors = m.getString("actors");
                String actorArray[] = actors.split(", ");
                String description = m.getString("description");
                String date = m.getString("releaseDate");
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date inputDate = new Date();
                try {
                    inputDate = dateFormat.parse(date);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

                String photoLink = m.getString("photo");

                Movie movie = new Movie(Integer.parseInt(id), name, category, director, actorArray, description, inputDate, photoLink);
                Log.d("JSON", "parseFeed: ");
                Log.d("MovieTitle ", movie.getTitle());
                Log.d("MovieID ", movie.getId() + "");
                Log.d("Photo link", movie.getPhotoLink());
                Log.d("Release Date", movie.getReleaseDate().toString());
                for (int x =0; x < movie.getActors().length; x++) {
                    Log.d("Actor array", movie.getActors()[x]);
                }
                Log.d("Director", movie.getDirector());
                Log.d("Desc", movie.getDescription());
                Log.d("Photo", movie.getPhotoLink().toString());

                movieList.add(movie);

            }
        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());


        }
        return movieList;
    }

}
