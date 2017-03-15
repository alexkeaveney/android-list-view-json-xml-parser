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


public class MovieListFragment extends ListFragment {

    private static final String TAG = "MovieListFragment";
    private List<Movie> mMovies;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Movie List");


        MovieModel model = MovieModel.get(getActivity());
        mMovies = model.getMovies();

        for (int i =0; i < mMovies.size(); i++) {
            Log.d("Movie " + i, mMovies.get(i).getTitle());
        }
//        mMovies = new ArrayList<Movie>();
//        MyTask task = new MyTask();
//        task.execute("Getting Data", "Getting Images");
//        MovieAdapter adapter = new MovieAdapter(mMovies);
//        setListAdapter(adapter);

    }





    private class MovieAdapter extends ArrayAdapter<Movie> {

        public MovieAdapter(List<Movie> movies) {
            super(getActivity(), 0 , movies);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.movie_list_item, null);
            }

            Movie m = getItem(position);
            TextView titleTextView = (TextView)convertView.findViewById(R.id.titleTextView);
            titleTextView.setText(m.getTitle());
            ImageView movieImageView = (ImageView)convertView.findViewById(R.id.movieImageView);
            movieImageView.setImageBitmap(m.getPhoto());

            return convertView;
        }

    }

    public void onListItemClick(ListView l, View v,int position, long id) {
        Movie m = (Movie)(getListAdapter().getItem(position));

        //Send the movie from MovieListFragment to MovieActivity to MovieFragment for display
        Intent intent = new Intent(getActivity(), MoviesActivity.class);
        intent.putExtra(MovieFragment.EXTRA_MOVIE_ID, m.getId());
        startActivity(intent);

    }


    @Override
    public void onResume() {
        super.onResume();
        //gets the updated list
        updateUI();

    }

    private void updateUI() {

        MyTask task = new MyTask();
        task.execute("Getting Data", "Getting Images");
        MovieAdapter adapter = new MovieAdapter(mMovies);
        setListAdapter(adapter);

    }

    protected void finished(String message) {

        MovieAdapter adapter = new MovieAdapter(mMovies);
        setListAdapter(adapter);

    }

    private class MyTask extends AsyncTask<String, String, String> {

        HTTPManager manager = new HTTPManager();
        String movies;
        @Override
        protected void onPreExecute() {
            //updateDisplay("Starting task");
            //toggleVisibility();

        }

        @Override
        protected String doInBackground(String... params) {

            movies = manager.getData("http://10.0.2.2:8888/httpconn/movies.xml");
            MovieXMLParser myParser = new MovieXMLParser();
            mMovies = myParser.parseFeed(movies);

            for (int i =0; i < mMovies.size(); i++) {
                Bitmap image;
                try {
                    URL url = new URL("http://10.0.2.2:8888/httpconn/" + mMovies.get(i).getPhotoLink());
                    Log.d("URL", url.toString());
                    InputStream inputStream = url.openStream();
                    image = BitmapFactory.decodeStream(inputStream);
                    mMovies.get(i).setImage(image);
                }
                catch (Exception e) {
                    Log.e("Aysnc Exception", "Cant decoded image for movie " + i);
                }
            }

            return "Task complete";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            for (int i =0; i < values.length; i++) {
                Log.d("Progress update", values[i]);
            }
        }


        @Override
        protected void onPostExecute(String result) {
            Log.d("Async", "onPostExecute: Finished");
            finished(movies);
            //toggleVisibility()
        }


    }


}

