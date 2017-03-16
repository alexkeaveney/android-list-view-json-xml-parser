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


        if (sMovieModel == null) {
            sMovieModel = new MovieModel(c.getApplicationContext());
        }
        return sMovieModel;
    }


    public Movie getMovie(int id) {
        for (int i =0; i < movieList.size(); i++) {
            if (id == movieList.get(i).getId()) {
                Log.d("Id=Movie", "It happens");
                return movieList.get(i);
            }
        }
        return null;
    }

    public MyTask returnTask() {
        MyTask task = new MyTask();
        task.execute("Param1", "Param2", "Param3");
        return task;
    }

    public ArrayList<Movie> getMovies () {

        return movieList;
    }

    protected void finished(ArrayList<Movie> movieList) {

        this.movieList = movieList;

    }

    private class MyTask extends AsyncTask<String, String, String> {

        HTTPManager manager = new HTTPManager();
        String movies;
        ArrayList<Movie> movieList;
        @Override
        protected void onPreExecute() {
            //updateDisplay("Starting task");
            //toggleVisibility();

        }

        @Override
        protected String doInBackground(String... params) {

            movies = manager.getData("http://10.0.2.2:8888/httpconn/movies.xml");
            MovieXMLParser myParser = new MovieXMLParser();
            this.movieList = myParser.parseFeed(movies);
            //ArrayList<Movie>movies

            for (int i =0; i < movieList.size(); i++) {
                Bitmap image;
                try {
                    URL url = new URL("http://10.0.2.2:8888/httpconn/images/" + this.movieList.get(i).getPhotoLink());
                    Log.d("URL", url.toString());
                    InputStream inputStream = url.openStream();
                    image = BitmapFactory.decodeStream(inputStream);
                    this.movieList.get(i).setImage(image);
                }
                catch (Exception e) {
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

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            for (int i =0; i < values.length; i++) {
                Log.d("Progress update", values[i]);
            }

            //updateDisplay(flowers);

        }


        @Override
        protected void onPostExecute(String result) {
            Log.d("Async Model", "onPostExecute: Finished");
            finished(this.movieList);
            //toggleVisibility()
        }


    }

}


