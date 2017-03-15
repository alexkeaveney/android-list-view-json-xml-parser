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
            JSONArray movies = jsonObj.getJSONArray("movies");

            // looping through All Contacts
            for (int i = 0; i < movies.length(); i++) {
                JSONObject m = movies.getJSONObject(i);

                String id = m.getString("id");
                String name = m.getString("name");
                String category = m.getString("category");
                String director = m.getString("category");
                String actors = m.getString("actors");
                String actorArray[] = actors.split(",");
                String description = m.getString("description");
                String date = m.getString("date");
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date inputDate = new Date();
                try {
                    inputDate = dateFormat.parse(date);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

                String photoLink = m.getString("photo");

                Movie movie = new Movie(Integer.parseInt(id), name, category, director, actorArray, description, inputDate, photoLink);
                movieList.add(movie);

            }
        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());


        }
        return movieList;
    }

}
