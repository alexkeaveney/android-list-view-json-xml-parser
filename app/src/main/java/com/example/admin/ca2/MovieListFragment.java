package com.example.admin.ca2;

/**
 * Created by admin on 13/03/2017.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by admin on 29/11/2016.
 */

//This class is used to create a list of movies that can be clicked on to bring up more information about the movies
public class MovieListFragment extends ListFragment {

    //tag for page for debugging
    private static final String TAG = "MovieListFragment";

    //holds the movies in an list
    private List<Movie> mMovies;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sets the title on the title bar
        getActivity().setTitle("Movie List");

        //creates a model if there isnt one already to get the data from the external file
        MovieModel model = MovieModel.get(getActivity());

        //gets the movies retrieved from the model
        mMovies = model.getMovies();

        //used for debugging
//        for (int i =0; i < mMovies.size(); i++) {
//            Log.d("Movie " + i, mMovies.get(i).getTitle());
//        }

    }

    private class MovieAdapter extends ArrayAdapter<Movie> {

        //needs to be passed in a list of movies
        public MovieAdapter(List<Movie> movies) {
            super(getActivity(), 0 , movies);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //gets the movie_list_item layout file which describes how each list item needs to be layed out
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.movie_list_item, null);
            }
            //gets each movie iteratively
            Movie m = getItem(position);

            //links the text view for movie title
            TextView titleTextView = (TextView)convertView.findViewById(R.id.titleTextView);

            //sets the text view for title to the movie title
            titleTextView.setText(m.getTitle());

            //links the image view for movie poster
            ImageView movieImageView = (ImageView)convertView.findViewById(R.id.movieImageView);

            //sets the the image view image to a Bitmap photo
            movieImageView.setImageBitmap(m.getPhoto());

            //returns list item
            return convertView;
        }

    }

    //called if one of the items in the list is clicked
    public void onListItemClick(ListView l, View v,int position, long id) {

        //get the movie object of the item that was clicked
        Movie m = (Movie)(getListAdapter().getItem(position));

        //Moves from MovieListFragment to MovieActivity to MovieFragment for display
        Intent intent = new Intent(getActivity(), MoviesActivity.class);

        //puts the movie ID in an intent so that the other view knows which movie to dispaly
        intent.putExtra(MovieFragment.EXTRA_MOVIE_ID, m.getId());

        //starts new new activity
        startActivity(intent);

    }


    @Override
    public void onResume() {
        super.onResume();

        //gets the updated list
        updateUI();

    }

    //on reload
    private void updateUI() {

        //starts a new task
        MyTask task = new MyTask();

        //gets the data and images
        task.execute("Getting Data", "Getting Images");

        //puts them in a movie adapter
        MovieAdapter adapter = new MovieAdapter(mMovies);

        ///starts the list adapter
        setListAdapter(adapter);

    }


    //when the task is finished
    protected void finished(String message) {

        //creates a new adapter with the retrieved movies
        MovieAdapter adapter = new MovieAdapter(mMovies);

        //starts the adapter to show the movie list
        setListAdapter(adapter);

    }

    //used to get the external information
    private class MyTask extends AsyncTask<String, String, String> {

        //creates a new manager to make the HTTP request
        HTTPManager manager = new HTTPManager();

        //string to hold the retrieved content
        String movies;

        //executed at the beginning to update UI and show the user that it is loading content
        @Override
        protected void onPreExecute() {
            //updateDisplay("Starting task");
            //toggleVisibility();

        }

        //This is where the task takes place
        @Override
        protected String doInBackground(String... params) {

            //gets the xml data in string format
            movies = manager.getData("http://10.0.2.2:8888/httpconn/movies.xml");

            //creates a new XML parser to convert the XML to movie objects
            MovieXMLParser myParser = new MovieXMLParser();

            //sets an array list of movies to the newly converted movie list
            mMovies = myParser.parseFeed(movies);

            //loops through the movies
            for (int i =0; i < mMovies.size(); i++) {

                //to hold images retrieved
                Bitmap image;

                try {
                    //using the photo link retrieved from XML a url path is created
                    URL url = new URL("http://10.0.2.2:8888/httpconn/images/" + mMovies.get(i).getPhotoLink());

                    //for debugging
//                    Log.d("URL", url.toString());

                    //opens an input stream to that URL
                    InputStream inputStream = url.openStream();

                    //Using Java's BitmapFactories decode Stream method it converts the image into bitmap format and sets it to the image obejct
                    image = BitmapFactory.decodeStream(inputStream);

                    //sets the movie object image to the retrieved Bitmap
                    mMovies.get(i).setImage(image);
                }
                catch (Exception e) {
                    //prints custom error message that shows which image failed to decode
                    Log.e("Aysnc Exception", "Cant decoded image for movie " + i);
                }
            }

            return "Task complete";
        }

//        @Override
//        protected String doInBackground(String... params) {
//
//            movies = manager.getData("http://10.0.2.2:8888/httpconn/movies.json");
//            MovieJSONParser myParser = new MovieJSONParser();
//            mMovies = myParser.parseFeed(movies);
//
//            for (int i =0; i < mMovies.size(); i++) {
//                Bitmap image;
//                try {
//                    URL url = new URL("http://10.0.2.2:8888/httpconn/images/" + mMovies.get(i).getPhotoLink());
//                    Log.d("URL", url.toString());
//                    InputStream inputStream = url.openStream();
//                    image = BitmapFactory.decodeStream(inputStream);
//                    mMovies.get(i).setImage(image);
//                }
//                catch (Exception e) {
//                    Log.e("Aysnc Exception", "Cant decoded image for movie " + i);
//                }
//            }
//
//            return "Task complete";
//        }


        //This is used to update the user with the progress of the task
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            //logs the progress
            for (int i =0; i < values.length; i++) {
                Log.d("Progress update", values[i]);
            }
        }

        //This is called when the task is complete
        @Override
        protected void onPostExecute(String result) {
            Log.d("Async MovieListFragment", "on: Finished");
            //calls the finished function with the movies arraylist as a parameter
            finished(movies);
            //toggleVisibility()
        }


    }


}

