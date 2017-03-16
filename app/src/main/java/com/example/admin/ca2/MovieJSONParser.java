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

//This class is used to convert a string of JSON content into movie objects

public class MovieJSONParser {

    //This is the function that does the conversion.
    public static ArrayList<Movie> parseFeed(String content) {

        //This arraylist of movies holds the movie objects that are converted
        ArrayList<Movie>movieList = new ArrayList<>();

        try {

            //creates a JSON object to hold the string content
            JSONObject jsonObj = new JSONObject(content);

            // Getting an array of JSON movies from the JSON object
            JSONArray movies = jsonObj.getJSONArray("movie");

            // looping through movies
            for (int i = 0; i < movies.length(); i++) {

                //gets the 'i' movie in the list and sets it equal to a seperate JSON object
                JSONObject m = movies.getJSONObject(i);

                //sets the values to seperate variables and objects
                String id = m.getString("movieId");
                String name = m.getString("name");
                String category = m.getString("category");
                String director = m.getString("category");
                String actors = m.getString("actors");
                String actorArray[] = actors.split(", ");
                String description = m.getString("description");

                //The date is passed in as a string in the format (dd-MM-yyyy), below shows the conversion from a string to a Date object in Java
                String date = m.getString("releaseDate");
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date inputDate = new Date();
                try {
                    inputDate = dateFormat.parse(date);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

                String photoLink = m.getString("photo");

                //A new movie object is created using the above variables/objects passed in to its constructor
                Movie movie = new Movie(Integer.parseInt(id), name, category, director, actorArray, description, inputDate, photoLink);

                //The new movie is then added to the array list
                movieList.add(movie);

            }
        } catch (final JSONException e) {
            //If the function cannot handle the JSON conversion / Perhaps the JSON is in the wrong format print the error
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
        //returns the array list of newly created movies
        return movieList;
    }

}
