package com.example.admin.ca2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by admin on 29/11/2016.
 */

public class MovieModel {

    //holds arraylist of movies
    private ArrayList<Movie> movieList;

    //holds the only instance of the Movie Model class
    private static MovieModel sMovieModel;

    //Holds the context
    private Context sAppContext;

    //movie model needs to be passed a context
    public MovieModel(Context appContext) {

        //sets the global context
        this.sAppContext = appContext;

        //creates an arraylist of movies
        this.movieList = new ArrayList<>();

        //creates a task for getting the information
        MyTask task = new MyTask();

        //starts the task
        task.execute("Param1", "Param2", "Param3");
    }

    //This function is used to ensure only one instance of the class is created.
    public static MovieModel get(Context c) {

        //if there isnt already a Movie model create one
        if (sMovieModel == null) {
            sMovieModel = new MovieModel(c.getApplicationContext());
        }
        //returns the only instance of Movie Model
        return sMovieModel;
    }

    //Gets a movie by its ID
    public Movie getMovie(int id) {

        //loops through all of the movies
        for (int i =0; i < movieList.size(); i++) {

            //If the passed in ID is equal to the movie id of that item in the list return the movie
            if (id == movieList.get(i).getId()) {

                return movieList.get(i);
            }
        }
        //otherwise return null (movie not found)
        return null;
    }


    //gets all of the movies
    public ArrayList<Movie> getMovies () {

        return movieList;
    }

    //called when the task is complete
    protected void finished(ArrayList<Movie> movieList) {

        //sets global movie list to retrieved list from task
        this.movieList = movieList;

    }


    //class to create async tasks
    private class MyTask extends AsyncTask<String, String, String> {

        //creates new HTTPManager object to do HTTP requests of external files
        HTTPManager manager = new HTTPManager();

        //string to holds retrieved XML content in string format
        String movies;

        //holds retreived movie list
        ArrayList<Movie> movieList;

        //called at the beginning to show the user that the task has started
        @Override
        protected void onPreExecute() {
            //updateDisplay("Starting task");
            //toggleVisibility();

        }

        //The task takes place in here
        @Override
        protected String doInBackground(String... params) {

            //reads in the XML as a string and sets it equal to movies (String)
            movies = manager.getData("http://10.0.2.2:8888/httpconn/movies.xml");

            //create a new convert ("parser") to change the string XML content to Java Movie objects
            MovieXMLParser myParser = new MovieXMLParser();

            //sets the movie list to these retrieved object list
            this.movieList = myParser.parseFeed(movies);

            //loops through the movies
            for (int i =0; i < movieList.size(); i++) {

                //creates Bitmap object to hold retrieved image
                Bitmap image;

                try {

                    //creates url to image using movie objects photo link (from XML)
                    URL url = new URL("http://10.0.2.2:8888/httpconn/images/" + this.movieList.get(i).getPhotoLink());

                    //Debugging URL
                    //Log.d("URL", url.toString());

                    //creates a stream to that URL to get the image content
                    InputStream inputStream = url.openStream();

                    //uses Java Bitmap Factory's decode Stream method to convert the retreived data to a bitmap object
                    image = BitmapFactory.decodeStream(inputStream);

                    //sets the movie in the list Bitmap image to the retrieved one
                    this.movieList.get(i).setImage(image);
                }
                catch (Exception e) {

                    //logs which image didnt decode
                    Log.e("Aysnc Exception", "Cant decoded image for movie " + i);
                }
            }

            return "Task complete";
        }
//            @Override
//            protected String doInBackground(String... params) {
//
//                movies = manager.getData("http://10.0.2.2:8888/httpconn/movies.json");
//                MovieJSONParser myParser = new MovieJSONParser();
//                this.movieList = myParser.parseFeed(movies);
//
//                for (int i =0; i < this.movieList.size(); i++) {
//                    Bitmap image;
//                    try {
//                        URL url = new URL("http://10.0.2.2:8888/httpconn/images/" + this.movieList.get(i).getPhotoLink());
//                        Log.d("URL", url.toString());
//                        InputStream inputStream = url.openStream();
//                        image = BitmapFactory.decodeStream(inputStream);
//                        this.movieList.get(i).setImage(image);
//                    }
//                    catch (Exception e) {
//                        Log.e("Aysnc Exception", "Cant decoded image for movie " + i);
//                    }
//                }
//
//                return "Task complete";
//            }

        //To show the user where they are in the task
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            for (int i =0; i < values.length; i++) {
                Log.d("Progress update", values[i]);
            }

            //updateDisplay(flowers);

        }

        //Executed after the task has been completed
        @Override
        protected void onPostExecute(String result) {
            Log.d("Async Model", "onPostExecute: Finished");
            finished(this.movieList);
            //toggleVisibility()
        }


    }

}


